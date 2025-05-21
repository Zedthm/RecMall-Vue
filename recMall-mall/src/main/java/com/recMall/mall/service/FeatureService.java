package com.recMall.mall.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.recMall.mall.domain.*;
import net.sf.jsqlparser.parser.feature.FeatureSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zedthm
 * @version 1.0
 * @date 2025/5/4 17:25
 * @description:
 */
@Service
public class FeatureService {
    private static final Logger log = LoggerFactory.getLogger(FeatureService.class);

    public MallRecBooksUserDto buildUserDto(List<MallUserProfiles> userProfiles,
                                            List<MallCart> carts,
                                            List<MallCollect> collects,
                                            List<MallComment> comments,
                                            List<MallOrders> orders) {
        MallRecBooksUserDto dto = new MallRecBooksUserDto();

        // 构建各行为表索引（直接使用String类型ID）
        Map<String, Set<String>> cartIndex = buildCartIndex(carts);
        Map<String, Set<String>> collectIndex = buildCollectIndex(collects);
        Map<String, Set<String>> commentIndex = buildCommentIndex(comments);
        Map<String, Set<String>> orderIndex = buildOrderIndex(orders);

        // 处理用户数据
        List<MallRecBooksUserDto.UserDto> userDtos = Optional.ofNullable(userProfiles)
                .orElse(Collections.emptyList())
                .stream()
                .flatMap(user -> processUserActiveInfo(user, cartIndex, collectIndex, commentIndex, orderIndex))
                // 添加显式类型声明
                .sorted(Comparator
                        .comparingLong((MallRecBooksUserDto.UserDto adto) ->
                                Long.parseLong(adto.getUserId()))
                        .thenComparingLong(adto ->
                                Long.parseLong(adto.getBookId()))
                )
                .collect(Collectors.toList());

        dto.setUserList(userDtos);
        return dto;
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

    // 用户数据处理
    private Stream<MallRecBooksUserDto.UserDto> processUserActiveInfo(
            MallUserProfiles user,
            Map<String, Set<String>> cartIndex,
            Map<String, Set<String>> collectIndex,
            Map<String, Set<String>> commentIndex,
            Map<String, Set<String>> orderIndex
    ) {
        String userId = user.getUserId();

        // 合并所有关联bookId
        Set<String> allBookIds = new TreeSet<>(Comparator
                .comparingLong(Long::parseLong)
        );
        addBookIds(allBookIds, cartIndex.get(userId));
        addBookIds(allBookIds, collectIndex.get(userId));
        addBookIds(allBookIds, commentIndex.get(userId));
        addBookIds(allBookIds, orderIndex.get(userId));

        return allBookIds.stream()
                .map(bookId -> createUserDto(user, bookId,
                        cartIndex, collectIndex, commentIndex, orderIndex));
    }

    // 分离创建逻辑
    private MallRecBooksUserDto.UserDto createUserDto(
            MallUserProfiles user,
            String bookId,
            Map<String, Set<String>> cartIndex,
            Map<String, Set<String>> collectIndex,
            Map<String, Set<String>> commentIndex,
            Map<String, Set<String>> orderIndex) {

        MallRecBooksUserDto.UserDto dto = new MallRecBooksUserDto.UserDto();
        copyUserProperties(user, dto);

        // 设置排序关键字段
        dto.setUserId(user.getUserId());
        dto.setBookId(bookId);

        // 行为标记设置
        dto.setIsCart(flagToInt(cartIndex.get(user.getUserId()), bookId));
        dto.setIsCollect(flagToInt(collectIndex.get(user.getUserId()), bookId));
        dto.setIsComments(flagToInt(commentIndex.get(user.getUserId()), bookId));
        dto.setIsPurchased(flagToInt(orderIndex.get(user.getUserId()), bookId));

        return dto;
    }

    // 辅助方法
    private boolean isValidUserId(String userId, String bookId) {
        return userId != null && !userId.isEmpty()
                && bookId != null && !bookId.isEmpty();
    }

    private void addBookIds(Set<String> container, Set<String> bookIds) {
        if (bookIds != null) {
            container.addAll(bookIds);
        }
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

    public MallRecBooksBookDto buildBooksDto(List<MallBooks> books, List<MallBookTags> bookTags, List<MallTags> tags) {
        MallRecBooksBookDto booksDto = new MallRecBooksBookDto();
        List<MallBooks> safeBooks = Optional.ofNullable(books).orElseGet(ArrayList::new);
        List<MallBookTags> safeBookTags = Optional.ofNullable(bookTags).orElseGet(ArrayList::new);
        List<MallTags> safeTags = Optional.ofNullable(tags).orElseGet(ArrayList::new);

        Map<String, String> tagIdToNameMap = safeTags.stream()
                // 过滤无效ID
                .filter(t -> t.getTagId() != null && !t.getTagId().isEmpty())
                .collect(Collectors.toMap(
                        MallTags::getTagId,
                        MallTags::getTagName,
                        // 处理重复tagId
                        (existing, replacement) -> existing
                ));
        Map<String, List<String>> bookTagRelationMap = safeBookTags.stream()
                // 过滤无效bookId
                .filter(bt -> bt.getBookId() != null && !bt.getBookId().isEmpty())
                .collect(Collectors.groupingBy(
                        MallBookTags::getBookId,
                        Collectors.mapping(
                                // 处理空tagId
                                bt -> Optional.ofNullable(bt.getTagId()).orElse(""),
                                Collectors.toList()
                        )
                ));
        List<MallRecBooksBookDto.BookDto> dtoList = safeBooks.stream()
                .map(book -> convertToBookDto(book, bookTagRelationMap, tagIdToNameMap))
                .collect(Collectors.toList());

        booksDto.setBookList(dtoList);
        return booksDto;
    }

    private MallRecBooksBookDto.BookDto convertToBookDto(MallBooks book, Map<String, List<String>> bookTagRelationMap, Map<String, String> tagIdToNameMap) {
        MallRecBooksBookDto.BookDto dto = new MallRecBooksBookDto.BookDto();
        dto.setBookId(book.getBookId());
        dto.setTitle(book.getTitle());
        dto.setPrice(book.getPrice());
        dto.setPageCount(book.getPageCount());
        dto.setPubYear(book.getPubYear());
        dto.setBookRatingStd(book.getBookRatingStd());
        dto.setBookAvgRating(book.getBookAvgRating());
        dto.setBookRatingCount(book.getBookRatingCount());

        List<String> tagIds = bookTagRelationMap.getOrDefault(
                book.getBookId(),
                Collections.emptyList()
        );

        List<String> tagNames = tagIds.stream()
                .limit(5) // 控制最大数量
                .map(tagId -> tagIdToNameMap.getOrDefault(tagId, "null")) // 处理无效tagId
                .collect(Collectors.toList());
        // 填充标签字段
        String[] tags = new String[5];
        for (int i = 0; i < tags.length; i++) {
            tags[i] = (i < tagNames.size()) ? tagNames.get(i) : null;
        }
        dto.setBookTag1(tags[0]);
        dto.setBookTag2(tags[1]);
        dto.setBookTag3(tags[2]);
        dto.setBookTag4(tags[3]);
        dto.setBookTag5(tags[4]);
        return dto;
    }

    private static final Map<String, Integer> BEHAVIOR_WEIGHTS = new HashMap<>();

    static {
        BEHAVIOR_WEIGHTS.put("purchase", 41);
        BEHAVIOR_WEIGHTS.put("collect", 29);
        BEHAVIOR_WEIGHTS.put("cart", 21);
        BEHAVIOR_WEIGHTS.put("comment", 9);
    }

    public String buildFeature(String modelName, MallRecBooksUserDto userDto, MallRecBooksBookDto bookDto) {
        List<FeatureSet> features = getFeatureSets(userDto, bookDto);
        // 转换为TF Serving格式
        if ("deep-fm".equals(modelName)) {
            return convertToDeepFMJson(features);
        }
        if ("neural-cf".equals(modelName)) {
            return convertToNeuralCFJson(features);
        }
        return null;
    }

    List<FeatureSet> getFeatureSets(MallRecBooksUserDto userDto, MallRecBooksBookDto bookDto) {
        log.info("构建特征, 用户数据量={},  数据数据量={}", userDto.getUserList().size(), bookDto.getBookList().size());
        // 构建书籍查找表
        Map<String, MallRecBooksBookDto.BookDto> bookMap = bookDto.getBookList().stream()
                .collect(Collectors.toMap(MallRecBooksBookDto.BookDto::getBookId, b -> b));

        // 生成特征列表
        List<FeatureSet> features = userDto.getUserList().stream() // 改为顺序流
                .sorted(Comparator
                        .comparing(MallRecBooksUserDto.UserDto::getUserId)
                        .thenComparing(MallRecBooksUserDto.UserDto::getBookId))
                .flatMap(user -> processUserFeatures(user, bookMap))
                .collect(Collectors.toList());
        log.info("特征列表大小量={}", features.size());
        return features;
    }

    private Stream<FeatureSet> processUserFeatures(
            MallRecBooksUserDto.UserDto user,
            Map<String, MallRecBooksBookDto.BookDto> bookMap
    ) {
        MallRecBooksBookDto.BookDto book = bookMap.get(user.getBookId());
        if (book == null) return Stream.empty();

        // 计算偏好分数
        int preferScore = calculatePreferScore(user);

        // 生成标签（强制分类）
        Integer label = calculateLabel(preferScore);
        if (label == null) return Stream.empty();

        // 构建特征集合
        return Stream.of(createFeatureSet(user, book, preferScore, label));
    }

    private int calculatePreferScore(MallRecBooksUserDto.UserDto user) {
        return BEHAVIOR_WEIGHTS.get("purchase") * user.getIsPurchased() +
                BEHAVIOR_WEIGHTS.get("collect") * user.getIsCollect() +
                BEHAVIOR_WEIGHTS.get("cart") * user.getIsCart() +
                BEHAVIOR_WEIGHTS.get("comment") * user.getIsComments();
    }

    private Integer calculateLabel(int preferScore) {
        if (preferScore > 80) return 1;
        if (preferScore < 50) return 0;
        return null; // 过滤中间值
    }

    private FeatureSet createFeatureSet(
            MallRecBooksUserDto.UserDto user,
            MallRecBooksBookDto.BookDto book,
            int preferScore,
            int label
    ) {
        FeatureSet feature = new FeatureSet();

        // 数值特征
        feature.add("book_avg_rating", book.getBookAvgRating());
        feature.add("book_rating_std", book.getBookRatingStd());
        feature.add("user_avg_rating", user.getUserAvgRating());
        feature.add("user_rating_std", user.getUserRatingStd());
        feature.add("prefer_scores", (float) preferScore);
        try {
            feature.add("page_count", book.getPageCount() != null ? Float.parseFloat(book.getPageCount()) : 50.0f);
        } catch (NumberFormatException e) {
            feature.add("page_count", 50.0f); // 默认值处理非法数字
        }

        feature.add("price", book.getPrice() != null ? book.getPrice().floatValue() : 50.0f);

        try {
            feature.add("pub_year", book.getPubYear() != null ? Float.parseFloat(book.getPubYear()) : 2025.0f);
        } catch (NumberFormatException e) {
            feature.add("pub_year", 2025.0f); // 默认值处理非法数字
        }

        // 整型特征
        feature.add("book_rating_count", book.getBookRatingCount());
        feature.add("user_rating_count", user.getUserRatingCount());
        feature.add("book_id", parseIdToLong(book.getBookId()));
        feature.add("user_id", parseIdToLong(user.getUserId()));
        feature.add("isPurchased", user.getIsPurchased());
        feature.add("isCart", user.getIsCart());
        feature.add("isCollect", user.getIsCollect());
        feature.add("isComments", user.getIsComments());

        // 标签特征
        feature.add("user_tag1", user.getUserTag1());
        feature.add("user_tag2", user.getUserTag2());
        feature.add("user_tag3", user.getUserTag3());
        feature.add("user_tag4", user.getUserTag4());
        feature.add("user_tag5", user.getUserTag5());
        feature.add("book_zh_tag1", book.getBookTag1());
        feature.add("book_zh_tag2", book.getBookTag2());
        feature.add("book_zh_tag3", book.getBookTag3());
        feature.add("book_zh_tag4", book.getBookTag4());
        feature.add("book_zh_tag5", book.getBookTag5());

        // 目标标签
        feature.add("label", label);

        return feature;
    }

    private long parseIdToLong(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            return id.hashCode() & 0x7fffffff; // 处理非数字ID
        }
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String convertToNeuralCFJson(List<FeatureSet> features) {
        ObjectNode root = objectMapper.createObjectNode();
        ArrayNode instances = root.putArray("instances");
        root.put("signature_name", "serving_default");

        features.parallelStream().forEach(feature -> {
            ObjectNode node = objectMapper.createObjectNode();

            // 数值型特征
            node.put("book_avg_rating", feature.getFloat("book_avg_rating"));
            node.put("book_rating_std", feature.getFloat("book_rating_std"));
            node.put("user_avg_rating", feature.getFloat("user_avg_rating"));
            node.put("user_rating_std", feature.getFloat("user_rating_std"));
            node.put("prefer_scores", feature.getFloat("prefer_scores"));
            node.put("page_count", feature.getFloat("page_count"));
            node.put("price", feature.getFloat("price"));
            node.put("pub_year", feature.getFloat("pub_year"));
            node.put("avg_positive_pubyear", 2021.69);

            // 整型特征
            node.put("book_rating_count", feature.getInt("book_rating_count"));
            node.put("user_rating_count", feature.getInt("user_rating_count"));
            node.put("book_id", feature.getLong("book_id"));
            node.put("user_id", feature.getLong("user_id"));
            node.put("isPurchased", feature.getInt("isPurchased"));
            node.put("isCart", feature.getInt("isCart"));
            node.put("isCollect", feature.getInt("isCollect"));
            node.put("isComments", feature.getInt("isComments"));

            // 字符串特征
            jsonPutStringFeature(instances, feature, node);

        });

        try {
            return objectMapper.writeValueAsString(root);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON生成失败", e);
        }
    }

    private void jsonPutStringFeature(ArrayNode instances, FeatureSet feature, ObjectNode node) {
        // 用户标签硬编码处理
        node.put("user_tag1", feature.getString("user_tag1") != null ? feature.getString("user_tag1") : "");
        node.put("user_tag2", feature.getString("user_tag2") != null ? feature.getString("user_tag2") : "");
        node.put("user_tag3", feature.getString("user_tag3") != null ? feature.getString("user_tag3") : "");
        node.put("user_tag4", feature.getString("user_tag4") != null ? feature.getString("user_tag4") : "");
        node.put("user_tag5", feature.getString("user_tag5") != null ? feature.getString("user_tag5") : "");

        // 书籍中文标签硬编码处理
        node.put("book_zh_tag1", feature.getString("book_zh_tag1") != null ? feature.getString("book_zh_tag1") : "");
        node.put("book_zh_tag2", feature.getString("book_zh_tag2") != null ? feature.getString("book_zh_tag2") : "");
        node.put("book_zh_tag3", feature.getString("book_zh_tag3") != null ? feature.getString("book_zh_tag3") : "");
        node.put("book_zh_tag4", feature.getString("book_zh_tag4") != null ? feature.getString("book_zh_tag4") : "");
        node.put("book_zh_tag5", feature.getString("book_zh_tag5") != null ? feature.getString("book_zh_tag5") : "");

        // 同步添加保障线程安全
        synchronized (instances) {
            instances.add(node);
        }
    }

    private String convertToDeepFMJson(List<FeatureSet> features) {
        ObjectNode root = objectMapper.createObjectNode();
        ArrayNode instances = root.putArray("instances");
        root.put("signature_name", "serving_default");

        features.parallelStream().forEach(feature -> {
            ObjectNode node = objectMapper.createObjectNode();

            // 数值型特征
            node.put("book_avg_rating", feature.getFloat("book_avg_rating"));
            node.put("book_rating_std", feature.getFloat("book_rating_std"));
            node.put("user_avg_rating", feature.getFloat("user_avg_rating"));
            node.put("user_rating_std", feature.getFloat("user_rating_std"));

            // 整型特征
            node.put("book_rating_count", feature.getInt("book_rating_count"));
            node.put("user_rating_count", feature.getInt("user_rating_count"));
            node.put("book_id", feature.getLong("book_id"));
            node.put("user_id", feature.getLong("user_id"));

            // 字符串特征
            jsonPutStringFeature(instances, feature, node);

        });

        try {
            return objectMapper.writeValueAsString(root);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON生成失败", e);
        }
    }

    // 特征容器类
    static class FeatureSet {
        private final Map<String, Object> data = new HashMap<>();

        public void add(String key, Object value) {
            data.put(key, value);
        }

        public float getFloat(String key) {
            float defaultValue = 55.0f;
            Object value = data.get(key);
            if (value instanceof Number) {
                return ((Number) value).floatValue();
            } else if (value instanceof String) {
                String strValue = (String) value;
                try {
                    return Float.parseFloat(strValue);
                } catch (NumberFormatException e) {
                    return defaultValue;
                }
            } else {
                return defaultValue;
            }
        }

        public int getInt(String key) {
            Object value = data.get(key);
            if (value == null) {
                return 0; // 或者返回一个默认值
            }
            if (value instanceof Number) {
                return ((Number) value).intValue();
            } else if (value instanceof String) {
                String strValue = (String) value;
                try {
                    return Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    return 0; // 解析失败时返回默认值
                }
            } else {
                throw new IllegalArgumentException("Key '" + key + "' is not a number or string: " + value.getClass());
            }
        }

        public long getLong(String key) {
            return (long) data.get(key);
        }

        public String getString(String key) {
            Object value = data.get(key);
            if (value == null) {
                return null;
            }
            if (value instanceof String) {
                return (String) value;
            } else {
                return value.toString(); // 将数值类型转换为字符串
            }
        }
    }
}
