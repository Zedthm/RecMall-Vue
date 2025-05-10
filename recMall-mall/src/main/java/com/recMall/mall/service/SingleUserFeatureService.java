package com.recMall.mall.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.recMall.mall.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zedthm
 * @version 1.0
 * @date 2025/5/6 3:43
 * @description:
 */
@Service
public class SingleUserFeatureService {
    private final Logger log = LoggerFactory.getLogger(SingleUserFeatureService.class);

    private static final Map<String, Integer> BEHAVIOR_WEIGHTS = new HashMap<>();

    static {
        BEHAVIOR_WEIGHTS.put("purchase", 41);
        BEHAVIOR_WEIGHTS.put("collect", 29);
        BEHAVIOR_WEIGHTS.put("cart", 21);
        BEHAVIOR_WEIGHTS.put("comment", 9);
    }
    public MallRecBooksUserDto buildSingleUserDto(
            MallUserProfiles user,
            List<MallCart> userCarts,
            List<MallCollect> userCollects,
            List<MallComment> userComments,
            List<MallOrders> userOrders,
            List<MallRecBooksBookDto.BookDto> allBooks) { // 新增全量书籍参数

        MallRecBooksUserDto dto = new MallRecBooksUserDto();

        // 构建用户行为索引（保持原有逻辑）
        Map<String, Set<String>> cartIndex = buildCartIndex(userCarts);
        Map<String, Set<String>> collectIndex = buildCollectIndex(userCollects);
        Map<String, Set<String>> commentIndex = buildCommentIndex(userComments);
        Map<String, Set<String>> orderIndex = buildOrderIndex(userOrders);

        // 处理用户特征时关联所有书籍
        List<MallRecBooksUserDto.UserDto> userDtos = allBooks.stream()
                .map(book -> createUserBookFeature(
                        user,
                        book.getBookId(),
                        cartIndex,
                        collectIndex,
                        commentIndex,
                        orderIndex))
                .collect(Collectors.toList());

        dto.setUserList(userDtos);
        return dto;
    }

    private MallRecBooksUserDto.UserDto createUserBookFeature(
            MallUserProfiles user,
            String bookId,
            Map<String, Set<String>> cartIndex,
            Map<String, Set<String>> collectIndex,
            Map<String, Set<String>> commentIndex,
            Map<String, Set<String>> orderIndex) {

        MallRecBooksUserDto.UserDto dto = new MallRecBooksUserDto.UserDto();

        // 复制用户基础属性
        copyUserProperties(user, dto);

        // 设置当前书籍ID
        dto.setBookId(bookId);

        // 检查各行为索引
        String userId = user.getUserId();
        dto.setIsCart(flagToInt(cartIndex.getOrDefault(userId, Collections.emptySet()), bookId));
        dto.setIsCollect(flagToInt(collectIndex.getOrDefault(userId, Collections.emptySet()), bookId));
        dto.setIsComments(flagToInt(commentIndex.getOrDefault(userId, Collections.emptySet()), bookId));
        dto.setIsPurchased(flagToInt(orderIndex.getOrDefault(userId, Collections.emptySet()), bookId));

        return dto;
    }
    private int flagToInt(Set<String> bookSet, String bookId) {
        return (bookSet != null && bookSet.contains(bookId)) ? 1 : 0;
    }
    private void copyUserProperties(MallUserProfiles source, MallRecBooksUserDto.UserDto target) {
        target.setUserId(source.getUserId());
        target.setUserAvgRating(source.getUserAvgRating());
        target.setUserRatingStd(source.getUserRatingStd());
        target.setUserRatingCount(source.getUserRatingCount());
        target.setUserTag1(source.getUserTag1());
        target.setUserTag2(source.getUserTag2());
        target.setUserTag3(source.getUserTag3());
        target.setUserTag4(source.getUserTag4());
        target.setUserTag5(source.getUserTag5());
    }
    public String buildUserAllBooksFeatures(
            String modelType,
            MallRecBooksUserDto.UserDto singleUser,
            List<MallRecBooksBookDto.BookDto> allBooks,
            Map<String, Object> behaviorMaps) { // 包含用户行为数据的Map

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode instances = mapper.createArrayNode();

        // 并行处理所有书籍
        allBooks.parallelStream().forEach(book -> {
            ObjectNode instance = mapper.createObjectNode();

            // 用户特征
            instance.put("user_id", singleUser.getUserId());
            instance.put("user_avg_rating", singleUser.getUserAvgRating());
            instance.put("user_rating_std", singleUser.getUserRatingStd());
            instance.put("user_rating_count", singleUser.getUserRatingCount());

            // 书籍特征
            instance.put("book_id", book.getBookId());
            instance.put("book_avg_rating", book.getBookAvgRating());
            instance.put("book_rating_std", book.getBookRatingStd());
            instance.put("book_rating_count", book.getBookRatingCount());

            if ("neural-cf".equals(modelType)) {
                // 行为特征（从behaviorMaps获取）
                instance.put("is_purchased", checkBehavior(behaviorMaps.get("purchased"), book.getBookId()));
                instance.put("is_cart", checkBehavior(behaviorMaps.get("cart"), book.getBookId()));
                instance.put("is_collect", checkBehavior(behaviorMaps.get("collect"), book.getBookId()));
                instance.put("is_comments", checkBehavior(behaviorMaps.get("comments"), book.getBookId()));

                instance.put("page_count", book.getPageCount());
                instance.put("prefer_scores", calculatePreferScore(instance));
                instance.put("price", book.getPrice());
                instance.put("pub_year", book.getPubYear());
                instance.put("avg_positive_pubyear", "2021.69");
            }

            instance.put("user_tag1", singleUser.getUserTag1() != null ? singleUser.getUserTag1() : "");
            instance.put("user_tag2", singleUser.getUserTag2() != null ? singleUser.getUserTag2() : "");
            instance.put("user_tag3", singleUser.getUserTag3() != null ? singleUser.getUserTag3() : "");
            instance.put("user_tag4", singleUser.getUserTag4() != null ? singleUser.getUserTag4() : "");
            instance.put("user_tag5", singleUser.getUserTag5() != null ? singleUser.getUserTag5() : "");

            instance.put("book_zh_tag1", book.getBookTag1() != null ? book.getBookTag1() : "");
            instance.put("book_zh_tag2", book.getBookTag2() != null ? book.getBookTag2() : "");
            instance.put("book_zh_tag3", book.getBookTag3() != null ? book.getBookTag3() : "");
            instance.put("book_zh_tag4", book.getBookTag4() != null ? book.getBookTag4() : "");
            instance.put("book_zh_tag5", book.getBookTag5() != null ? book.getBookTag5() : "");
            // 线程安全添加
            synchronized (instances) {
                instances.add(instance);
            }
        });

        return constructFinalJson(modelType, instances);
    }

    private int calculatePreferScore(ObjectNode instance) {
        // 从特征节点获取行为标记值
        int purchased = instance.has("is_purchased") ? instance.get("is_purchased").asInt(0) : 0;
        int collect = instance.has("is_collect") ? instance.get("is_collect").asInt(0) : 0;
        int cart = instance.has("is_cart") ? instance.get("is_cart").asInt(0) : 0;
        int comments = instance.has("is_comments") ? instance.get("is_comments").asInt(0) : 0;

        // 应用权重计算
        return BEHAVIOR_WEIGHTS.getOrDefault("purchase", 41) * purchased +
                BEHAVIOR_WEIGHTS.getOrDefault("collect", 29) * collect +
                BEHAVIOR_WEIGHTS.getOrDefault("cart", 19) * cart +
                BEHAVIOR_WEIGHTS.getOrDefault("comment", 11) * comments;
    }

    private String constructFinalJson(String modelType, ArrayNode instances) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.put("signature_name", "serving_default");

        // 公共特征结构
        ArrayNode featuresArray = mapper.createArrayNode();

        instances.forEach(instance -> {
            ObjectNode featureNode = mapper.createObjectNode();

            // 公共特征
            featureNode.put("user_id", instance.get("user_id").asLong());
            featureNode.put("book_id", instance.get("book_id").asLong());
            featureNode.put("user_tag1", instance.get("user_tag1") != null ? instance.get("user_tag1").asText() : "");
            featureNode.put("user_tag2", instance.get("user_tag2") != null ? instance.get("user_tag2").asText() : "");
            featureNode.put("user_tag3", instance.get("user_tag3") != null ? instance.get("user_tag3").asText() : "");
            featureNode.put("user_tag4", instance.get("user_tag4") != null ? instance.get("user_tag4").asText() : "");
            featureNode.put("user_tag5", instance.get("user_tag5") != null ? instance.get("user_tag5").asText() : "");

            featureNode.put("book_zh_tag1", instance.get("book_zh_tag1") != null ? instance.get("book_zh_tag1").asText() : "");
            featureNode.put("book_zh_tag2", instance.get("book_zh_tag2") != null ? instance.get("book_zh_tag2").asText() : "");
            featureNode.put("book_zh_tag3", instance.get("book_zh_tag3") != null ? instance.get("book_zh_tag3").asText() : "");
            featureNode.put("book_zh_tag4", instance.get("book_zh_tag4") != null ? instance.get("book_zh_tag4").asText() : "");
            featureNode.put("book_zh_tag5", instance.get("book_zh_tag5") != null ? instance.get("book_zh_tag5").asText() : "");

            featureNode.put("book_rating_count",
                    safeToInt(instance.get("book_rating_count"), "book_rating_count", 0));

            featureNode.put("user_rating_count",
                    safeToInt(instance.get("user_rating_count"), "user_rating_count", 0));

            // 数值型特征处理
            addNumericFeature(featureNode, (ObjectNode) instance, "user_avg_rating", 3.5f);
            addNumericFeature(featureNode, (ObjectNode) instance, "user_rating_std", 0.5f);
            addNumericFeature(featureNode, (ObjectNode) instance, "book_avg_rating", 3.5f);
            addNumericFeature(featureNode, (ObjectNode) instance, "book_rating_std", 0.5f);




            // 根据模型类型添加特定特征
            if ("neural-cf".equals(modelType)) {
                buildNeuralCFFeatures(featureNode, (ObjectNode) instance);
            }

            featuresArray.add(featureNode);
        });

        // 构建最终JSON结构
        ObjectNode result = mapper.createObjectNode();
        result.put("model_type", modelType);
        result.set("instances", featuresArray);

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            log.error("JSON序列化失败", e);
            return "{\"error\":\"特征生成失败\"}";
        }
    }

    private int safeToInt(JsonNode node, String fieldName, int defaultValue) {
        if (node == null || node.isNull()) {
            log.warn("字段 {} 为空，使用默认值: {}", fieldName, defaultValue);
            return defaultValue;
        }

        try {
            if (node.isTextual()) {
                // 尝试解析字符串内容
                return Integer.parseInt(node.asText().trim());
            } else if (node.isFloatingPointNumber()) {
                // 浮点数四舍五入
                return (int) Math.round(node.asDouble());
            } else if (node.isNumber()) {
                // 直接获取整型值
                return node.asInt();
            } else if (node.isBoolean()) {
                // 布尔转0/1（根据业务需求）
                return node.asBoolean() ? 1 : 0;
            } else {
                log.warn("无法识别的 {} 字段类型: {}，使用默认值", fieldName, node.getNodeType());
                return defaultValue;
            }
        } catch (NumberFormatException e) {
            log.error("字段 {} 转换失败，值: {}，异常: {}", fieldName, node.asText(), e.getMessage());
            return defaultValue;
        }
    }

    private void addNumericFeature(ObjectNode target, ObjectNode source, String field, float defaultValue) {
        if (source.has(field)) {
            JsonNode node = source.get(field);
            if (node.isNumber()) {
                target.put(field, node.floatValue());
            } else if (node.isTextual()) {
                try {
                    target.put(field, Float.parseFloat(node.asText()));
                } catch (NumberFormatException e) {
                    target.put(field, defaultValue);
                }
            } else {
                target.put(field, defaultValue);
            }
        } else {
            target.put(field, defaultValue);
        }
    }

    private int checkBehavior(Object behaviorSet, String bookId) {
        if (behaviorSet instanceof Set) {
            return ((Set<?>) behaviorSet).contains(bookId) ? 1 : 0;
        }
        return 0;
    }

    public Map<String, Object> prepareBehaviorMaps(
            String userId,
            List<MallCart> carts,
            List<MallCollect> collects,
            List<MallOrders> orders,
            List<MallComment> comments) {

        Map<String, Object> behaviorMaps = new HashMap<>();

        behaviorMaps.put("cart", carts.stream()
                .filter(c -> c.getUserId().equals(userId))
                .map(MallCart::getGoodsId)
                .collect(Collectors.toSet()));

        behaviorMaps.put("purchased", orders.stream()
                .filter(o -> o.getUserId().equals(userId))
                .map(MallOrders::getGoodsId)
                .collect(Collectors.toSet()));

        behaviorMaps.put("collect", collects.stream()
                .filter(c -> c.getUserId().equals(userId))
                .map(MallCollect::getGoodsId)
                .collect(Collectors.toSet()));

        behaviorMaps.put("comments", comments.stream()
                .filter(c -> c.getUserId().equals(userId))
                .map(MallComment::getGoodsId)
                .collect(Collectors.toSet()));

        return behaviorMaps;
    }

    // NeuralCF模型特征构建
    private void buildNeuralCFFeatures(ObjectNode featureNode, ObjectNode instance) {
        int purchased = instance.has("is_purchased") ? instance.get("is_purchased").asInt(0) : 0;
        int collect = instance.has("is_collect") ? instance.get("is_collect").asInt(0) : 0;
        int cart = instance.has("is_cart") ? instance.get("is_cart").asInt(0) : 0;
        int comments = instance.has("is_comments") ? instance.get("is_comments").asInt(0) : 0;
        featureNode.put("isPurchased", purchased);
        featureNode.put("isCollect", collect);
        featureNode.put("isCart", cart);
        featureNode.put("isComments", comments);

        featureNode.put("prefer_scores", instance.get("prefer_scores").asInt(0));

        // 页面数量安全转换
        JsonNode pageCountNode = instance.get("page_count");
        if (pageCountNode != null && pageCountNode.isIntegralNumber()) {
            featureNode.put("page_count", pageCountNode.asInt());
        } else {
            featureNode.put("page_count", 200); // 默认值
        }

        // 价格特征处理
        JsonNode priceNode = instance.get("price");
        if (priceNode != null && priceNode.isNumber()) {
            featureNode.put("price", priceNode.decimalValue());
        } else {
            featureNode.put("price", BigDecimal.valueOf(50.0));
        }

        // 出版年份安全处理
        JsonNode pubYearNode = instance.get("pub_year");
        if (pubYearNode != null && pubYearNode.isIntegralNumber()) {
            featureNode.put("pub_year", pubYearNode.asInt());
        } else {
            featureNode.put("pub_year", 2020); // 默认年份
        }

        JsonNode avgPubYearNode = instance.get("avg_positive_pubyear");
        if (avgPubYearNode != null && avgPubYearNode.isNumber()) {
            featureNode.put("avg_positive_pubyear", avgPubYearNode.decimalValue());
        } else {
            featureNode.put("avg_positive_pubyear", BigDecimal.valueOf(2021.69));
        }


    }


    // 购物车索引（用户 -> 书籍集合）
    private Map<String, Set<String>> buildCartIndex(List<MallCart> carts) {
        return Optional.ofNullable(carts)
                .orElse(Collections.emptyList())
                .stream()
                .filter(c -> isValidUserId(c.getUserId(), c.getGoodsId()))
                .collect(Collectors.groupingBy(
                        MallCart::getUserId,
                        Collectors.mapping(
                                MallCart::getGoodsId,
                                Collectors.toSet()
                        )
                ));
    }

    // 收藏索引
    private Map<String, Set<String>> buildCollectIndex(List<MallCollect> collects) {
        return Optional.ofNullable(collects)
                .orElse(Collections.emptyList())
                .stream()
                .filter(c -> isValidUserId(c.getUserId(), c.getGoodsId()))
                .collect(Collectors.groupingBy(
                        MallCollect::getUserId,
                        Collectors.mapping(
                                MallCollect::getGoodsId,
                                Collectors.toSet()
                        )
                ));
    }

    // 评论索引
    private Map<String, Set<String>> buildCommentIndex(List<MallComment> comments) {
        return Optional.ofNullable(comments)
                .orElse(Collections.emptyList())
                .stream()
                .filter(c -> isValidUserId(c.getUserId(), c.getGoodsId()))
                .collect(Collectors.groupingBy(
                        MallComment::getUserId,
                        Collectors.mapping(
                                MallComment::getGoodsId,
                                Collectors.toSet()
                        )
                ));
    }

    // 订单索引
    private Map<String, Set<String>> buildOrderIndex(List<MallOrders> orders) {
        return Optional.ofNullable(orders)
                .orElse(Collections.emptyList())
                .stream()
                .filter(o -> isValidUserId(o.getUserId(), o.getGoodsId()))
                .collect(Collectors.groupingBy(
                        MallOrders::getUserId,
                        Collectors.mapping(
                                MallOrders::getGoodsId,
                                Collectors.toSet()
                        )
                ));
    }

    private boolean isValidUserId(String userId, String bookId) {
        return userId != null && !userId.isEmpty()
                && bookId != null && !bookId.isEmpty();
    }

}
