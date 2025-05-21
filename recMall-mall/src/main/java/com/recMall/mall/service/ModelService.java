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
import java.time.format.DateTimeFormatter;
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
    private static final Logger log = LoggerFactory.getLogger(ModelService.class);
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
        int totalBatches = (int) Math.ceil((double) instances.size() / batchSize);

        log.info("开始批处理预测. 总数据量={}, 批次大小={}, 总批次数={}",
                instances.size(), batchSize, totalBatches);

        for (int batchNum = 0; batchNum < totalBatches; batchNum++) {
            int start = batchNum * batchSize;
            int end = Math.min(start + batchSize, instances.size());

            JSONArray batch = new JSONArray(instances.subList(start, end));
            JSONObject batchRequest = new JSONObject();
            batchRequest.put("signature_name", signature);
            batchRequest.put("instances", batch);

            List<Double> batchResult = predict(modelName, batchRequest.toJSONString());
            results.addAll(batchResult);

            // 每完成10%进度或最后一批时输出日志
            if ((batchNum+1) % Math.max(1, totalBatches/10) == 0 || (batchNum+1) == totalBatches) {
                String time = LocalDateTime.now().format(DateTimeFormatter.ISO_TIME);
                log.info("模型：{}, 批次进度: {}/{} ({}%), 累计结果数={}, 时间={}",
                        modelName,
                        batchNum+1, totalBatches,
                        (batchNum+1)*100/totalBatches,
                        results.size(), time);
            }
        }

        log.info("批处理完成. 总返回结果数={}", results.size());
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
        List<FeatureService.FeatureSet> featureSets = featureService.getFeatureSets(userDto, bookDto);
        // === 新增输入校验 ===
        if (predictions.size() != featureSets.size()) {
            log.error("预测结果与特征数据数量不匹配. predictions={}, features={}",
                    predictions.size(), featureSets.size());
            throw new IllegalArgumentException("预测数据与特征数量不一致");
        }

        // === 原书籍映射逻辑 ===

        final LocalDateTime now = LocalDateTime.now();
        Map<String, List<Recommendation.BookRec>> userRecMap = new HashMap<>();

        // === 新增计数器 ===
        int validCount = 0;
        int invalidCount = 0;

        for (int i = 0; i < predictions.size(); i++) {
            FeatureService.FeatureSet feature = featureSets.get(i);
            String userId = feature.getString("user_id");
            String bookId = feature.getString("book_id");
            if (userId == null || bookId == null) {
                log.warn("无效特征: 用户[{}] 书籍[{}] ]",
                        userId, bookId);
                invalidCount++;
                continue;
            }
            Recommendation.BookRec bookRec = new Recommendation.BookRec(
                    bookId,
                    predictions.get(i),
                    now
            );
            userRecMap.computeIfAbsent(userId, k -> new ArrayList<>())
                    .add(bookRec);
            validCount++;
        }

        // === 关键日志 ===
        log.info("特征处理完成. 总数={}, 有效={}, 无效={}",
                featureSets.size(), validCount, invalidCount);


        List<Recommendation> result = userRecMap.entrySet().stream()
                .map(entry -> {
                    List<Recommendation.BookRec> sortedBooks = entry.getValue().stream()
                            .sorted(Comparator.comparingDouble(Recommendation.BookRec::getScore).reversed())
                            .limit(10)
                            .collect(Collectors.toList());

                    // === 新增推荐数量检查 ===
                    if (sortedBooks.isEmpty()) {
                        log.warn("用户 {} 无有效推荐", entry.getKey());
                    } else if (sortedBooks.size() < 5) {
                        log.info("用户 {} 推荐数较少: {}", entry.getKey(), sortedBooks.size());
                    }

                    return new Recommendation(entry.getKey(), sortedBooks);
                })
                .collect(Collectors.toList());

        // === 最终统计 ===
        log.info("生成推荐结果. 总用户数={}, 平均推荐数={}",
                result.size(),
                result.stream()
                        .mapToInt(r -> r.getBooks().size())
                        .average().orElse(0));

        return result;
    }

}
