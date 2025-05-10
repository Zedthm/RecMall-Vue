package com.recMall.web.controller.mall;

import com.recMall.common.core.controller.BaseController;
import com.recMall.mall.domain.*;
import com.recMall.mall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author zedthm
 * @version 1.0
 * @date 2025/5/4 15:33
 * @description:
 */
@RestController
@RequestMapping("/mall/recBooks")
public class MallRecBooksController extends BaseController {
    @Autowired
    private ModelService modelService;
    @Autowired
    private FeatureService featureService;
    @Autowired
    private IMallUserProfilesService mallUserProfilesService;
    @Autowired
    private IMallCartService mallCartService;
    @Autowired
    private IMallCollectService mallCollectService;
    @Autowired
    private IMallCommentService mallCommentService;
    @Autowired
    private IMallOrdersService mallOrdersService;
    @Autowired
    private IMallBooksService mallBooksService;
    @Autowired
    private IMallBookTagsService mallBookTagsService;
    @Autowired
    private IMallTagsService mallTagsService;
    @Autowired
    private SingleUserFeatureService singleUserFeatureService;
    private static final String MODEL_TYPE_DEEP_FM = "deep-fm";
    private static final String MODEL_TYPE_NEURAL_CF = "neural-cf";

    @RequestMapping("/deep-fm")
    public ResponseEntity<String> deepFM() {
        return handleRecommendation(MODEL_TYPE_DEEP_FM);
    }

    @RequestMapping("/neural-cf")
    public ResponseEntity<String> neuralCF() {
        return handleRecommendation(MODEL_TYPE_NEURAL_CF);
    }



    private ResponseEntity<String> handleRecommendation(String modelType) {
        try {
            List<MallUserProfiles> userProfiles = mallUserProfilesService.selectMallUserProfilesList(null);
            List<MallCart> carts = mallCartService.selectMallCartList(null);
            List<MallCollect> collects = mallCollectService.selectMallCollectList(null);
            List<MallComment> comments = mallCommentService.selectMallCommentList(null);
            List<MallOrders> orders = mallOrdersService.selectMallOrdersList(null);

            MallRecBooksUserDto userDto = featureService.buildUserDto(userProfiles, carts, collects, comments, orders);

            List<MallBooks> books = mallBooksService.selectMallBooksList(null);
            List<MallBookTags> bookTags = mallBookTagsService.selectMallBookTagsList(null);
            List<MallTags> tags = mallTagsService.selectMallTagsList(null);

            MallRecBooksBookDto bookDto = featureService.buildBooksDto(books, bookTags, tags);

            String featuresJson = featureService.buildFeature(modelType, userDto, bookDto);
            logger.info("FeaturesJson 前100字符: {}", featuresJson.substring(0, Math.min(featuresJson.length(), 100)));

            System.out.println("开始预测");
            if (MODEL_TYPE_DEEP_FM.equals(modelType)) {
                List<Double> predictByDeepFM = modelService.batchPredictByDeepFM(featuresJson);
                List<Recommendation> recommendations = modelService.processPredictions(predictByDeepFM, userDto, bookDto);
                System.out.println(recommendations);
            } else if (MODEL_TYPE_NEURAL_CF.equals(modelType)) {
                List<Double> predictByNeuralCF = modelService.batchPredictByNeuralCF(featuresJson);
                List<Recommendation> recommendations = modelService.processPredictions(predictByNeuralCF, userDto, bookDto);
                System.out.println(recommendations);
            }

            return ResponseEntity.ok("预测完成");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("预测失败：" + e.getMessage());
        }
    }


    @RequestMapping("/single")
    public ResponseEntity<String> single() {
        MallUserProfiles userProfiles = mallUserProfilesService.selectMallUserProfilesByUserId("997");
        List<MallCart> carts = mallCartService.selectMallCartList(null);
        List<MallCollect> collects = mallCollectService.selectMallCollectList(null);
        List<MallComment> comments = mallCommentService.selectMallCommentList(null);
        List<MallOrders> orders = mallOrdersService.selectMallOrdersList(null);
        Map<String, Object> behaviorMaps = singleUserFeatureService.prepareBehaviorMaps("997", carts, collects, orders, comments);

        List<MallBooks> books = mallBooksService.selectMallBooksList(null);
        List<MallBookTags> bookTags = mallBookTagsService.selectMallBookTagsList(null);
        List<MallTags> tags = mallTagsService.selectMallTagsList(null);
        MallRecBooksBookDto bookDto = featureService.buildBooksDto(books, bookTags, tags);

        MallRecBooksUserDto booksUserDto = singleUserFeatureService.buildSingleUserDto(userProfiles, carts, collects, comments, orders, bookDto.getBookList());
        String featuresJson = singleUserFeatureService.buildUserAllBooksFeatures(MODEL_TYPE_NEURAL_CF, booksUserDto.getUserList().get(0), bookDto.getBookList(), behaviorMaps);
        System.out.println(featuresJson);
        List<Double> predictByNeuralCF = modelService.batchPredictByNeuralCF(featuresJson);

        System.out.println(predictByNeuralCF);
        return ResponseEntity.ok("单机预测");
    }
}
