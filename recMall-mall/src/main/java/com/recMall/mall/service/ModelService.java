package com.recMall.mall.service;

import com.alibaba.fastjson2.JSONArray;

import static java.util.AbstractMap.SimpleEntry;


import com.alibaba.fastjson2.JSONObject;
import com.recMall.mall.domain.MallRecBooksBookDto;
import com.recMall.mall.domain.MallRecBooksUserDto;
import com.recMall.mall.domain.Recommendation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zedthm
 * @version 1.0
 * @date 2025/5/4 17:44
 * @description:
 */
@Service
public class ModelService {
    private static final Logger log =  LoggerFactory.getLogger(ModelService.class);
    private final FeatureService featureService;
    private final RestTemplate tfRestTemplate;

    public ModelService(FeatureService featureService, RestTemplate tfRestTemplate) {
        this.featureService = featureService;
        this.tfRestTemplate = tfRestTemplate;
    }

    @Value("${tensorflow.serving.endpoint}")
    private String tfServingEndpoint;

    @Value("${tensorflow.serving.model-name1}")
    private String deepFmModelName;

    @Value("${tensorflow.serving.model-name2}")
    private String neuralCfModelName;

    @Value("${tensorflow.serving.batch-size}")
    private int batchSize;

    private List<Double> predict(String modelName, String featuresJson) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = tfRestTemplate.exchange(
                tfServingEndpoint + "/v1/models/" + modelName + ":predict",
                HttpMethod.POST,
                new HttpEntity<>(featuresJson, headers),
                String.class
        );


        JSONObject responseBody = JSONObject.parseObject(response.getBody());
        JSONArray predictions = responseBody.getJSONArray("predictions");

        return predictions.stream()
                .map(p -> {
                    // 处理嵌套数组结构 [[0.85], [0.92]]
                    if (p instanceof JSONArray) {
                        JSONArray arr = (JSONArray) p;
                        return arr.getDouble(0); // 取第一个元素
                    }
                    // 处理直接数值类型 [0.85, 0.92]
                    else if (p instanceof Number) {
                        return ((Number) p).doubleValue();
                    }
                    // 处理对象结构 {"score": 0.85}
                    else if (p instanceof JSONObject) {
                        return ((JSONObject) p).getDouble("score");
                    }
                    throw new IllegalStateException("未知预测项格式: " + p.getClass().getName());
                })
                .collect(Collectors.toList());

    }

    private List<Double> batchPredict(String modelName, String featuresJson) {
        JSONObject requestJson = JSONObject.parseObject(featuresJson);
        JSONArray instances = requestJson.getJSONArray("instances");
        String signature = requestJson.getString("signature_name");

        List<Double> results = new ArrayList<>(instances.size());

        // 分批处理逻辑
        for (int i = 0; i < instances.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, instances.size());
            JSONArray batch = new JSONArray(instances.subList(i, endIndex));

            JSONObject batchRequest = new JSONObject();
            batchRequest.put("signature_name", signature);
            batchRequest.put("instances", batch);

            List<Double> batchResult = predict(modelName, batchRequest.toJSONString());
            results.addAll(batchResult);
            if (i % 100 == 0 && i > 0) {
                String time = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_DATE_TIME);
                log.info("已经完成批次：{}, 时间:{}, 总量:{}", i, time, results.size());
            }
        }

        return results;
    }

    public List<Double> batchPredictByDeepFM(String featuresJson) {
        return batchPredict(deepFmModelName, featuresJson);
    }

    public List<Double> batchPredictByNeuralCF(String featuresJson) {
        return batchPredict(neuralCfModelName, featuresJson);
    }

    public List<Recommendation> processPredictions(List<Double> predictions,
                                                   MallRecBooksUserDto userDto,
                                                   MallRecBooksBookDto bookDto) {
        // 构建书籍查找表
        Map<String, MallRecBooksBookDto.BookDto> bookMap = bookDto.getBookList().stream()
                .collect(Collectors.toMap(MallRecBooksBookDto.BookDto::getBookId, b -> b));

        // 使用同一时间戳用于所有推荐
        final LocalDateTime now = LocalDateTime.now();

        // 创建中间结构暂存数据
        Map<String, List<Recommendation.BookRec>> userRecMap = new HashMap<>();

        for (int i = 0; i < predictions.size(); i++) {
            MallRecBooksUserDto.UserDto user = userDto.getUserList().get(i);
            MallRecBooksBookDto.BookDto book = bookMap.get(user.getBookId());

            // 构建书籍推荐对象
            Recommendation.BookRec bookRec = new Recommendation.BookRec(
                    book.getBookId(),
                    predictions.get(i),
                    now
            );

            // 按用户ID聚合
            userRecMap.computeIfAbsent(user.getUserId(), k -> new ArrayList<>())
                    .add(bookRec);
        }

        // 构建最终推荐列表（按分数排序）
        return userRecMap.entrySet().stream()
                .map(entry -> {
                    List<Recommendation.BookRec> sortedBooks = entry.getValue().stream()
                            .sorted(Comparator.comparingDouble(Recommendation.BookRec::getScore).reversed())
                            .limit(30)
                            .collect(Collectors.toList());

                    return new Recommendation(entry.getKey(), sortedBooks);
                })
                .collect(Collectors.toList());
    }

}
