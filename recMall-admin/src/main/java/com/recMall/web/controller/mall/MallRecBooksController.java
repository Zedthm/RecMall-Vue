package com.recMall.web.controller.mall;

import com.github.pagehelper.PageInfo;
import com.recMall.common.constant.HttpStatus;
import com.recMall.common.core.controller.BaseController;
import com.recMall.common.core.page.TableDataInfo;
import com.recMall.common.core.redis.RedisCache;
import com.recMall.common.utils.spring.SpringUtils;
import com.recMall.mall.domain.*;
import com.recMall.mall.service.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

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
    public RedisTemplate redisTemplate;
    @Autowired
    @Qualifier("dataQueryPool")
    private ExecutorService dataQueryPool;

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
    private static CopyOnWriteArrayList<Recommendation> backUp = new CopyOnWriteArrayList<>();

    @RequestMapping("/deep-fm{userId}")
    public TableDataInfo deepFM(@PathVariable("userId") String userId) {
//        return handleRecommendation(userId,MODEL_TYPE_DEEP_FM);
        return getRecBookList(userId);
    }

    @RequestMapping("/neural-cf{userId}")
    public TableDataInfo neuralCF(@PathVariable("userId") String userId) {
        return handleRecommendation(userId, MODEL_TYPE_NEURAL_CF);
    }

    private TableDataInfo handleRecommendation(String userId, String modelType) {
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

            logger.info("FeaturesJson 字符: {}", featuresJson.substring(0, Math.min(featuresJson.length(), 1000)));
            List<Recommendation> recommendations = new ArrayList<>();
            if (MODEL_TYPE_DEEP_FM.equals(modelType)) {
                List<Double> predictByDeepFM = modelService.batchPredictByDeepFM(featuresJson);
                recommendations = modelService.processPredictions(predictByDeepFM, userDto, bookDto);
            } else if (MODEL_TYPE_NEURAL_CF.equals(modelType)) {
                List<Double> predictByNeuralCF = modelService.batchPredictByNeuralCF(featuresJson);
                recommendations = modelService.processPredictions(predictByNeuralCF, userDto, bookDto);
            }

            return getRecBookList(recommendations, userId);
        } catch (Exception e) {
            e.printStackTrace();
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(HttpStatus.NOT_FOUND);
            rspData.setMsg("系统异常");
            rspData.setRows(null);
            rspData.setTotal(0);
            return rspData;
        }
    }

    private TableDataInfo getRecBookList(List<Recommendation> recommendations, String userId) {
        String userRecKey = "user:rec:" + userId;
        // 尝试从Redis获取用户推荐书籍列表
        List<Recommendation.BookRec> books = (List<Recommendation.BookRec>) redisTemplate.opsForValue().get(userRecKey);

        if (books == null) {
            // 缓存未命中，遍历查找
            books = new ArrayList<>();
            for (Recommendation rec : recommendations) {
                if (rec.getUserId().equals(userId)) {
                    books = rec.getBooks();
                    break;
                }
            }
            // 存入Redis，设置过期时间（存在书籍1小时，空数据1分钟防穿透）
            redisTemplate.opsForValue().set(userRecKey, books, books.isEmpty() ? 1 : 60, TimeUnit.MINUTES);
        }

        List<MallBooks> bookRecList = new ArrayList<>();
        for (Recommendation.BookRec book : books) {
            String bookKey = "book:info:" + book.getBookId();
            MallBooks mallBook = (MallBooks) redisTemplate.opsForValue().get(bookKey);

            if (mallBook == null) {
                // 缓存未命中，查询数据库
                mallBook = mallBooksService.selectMallBooksByBookId(book.getBookId());
                if (mallBook != null) {
                    // 存入Redis，1小时过期
                    redisTemplate.opsForValue().set(bookKey, mallBook, 1, TimeUnit.HOURS);
                } else {
                    // 处理空值，防止缓存穿透
                    redisTemplate.opsForValue().set(bookKey, new MallBooks(), 1, TimeUnit.MINUTES);
                }
            }
            // 确保非空才加入列表
            if (mallBook != null && mallBook.getBookId() != null) {
                bookRecList.add(mallBook);
            }
        }

        return getDataTable(bookRecList);
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

    private TableDataInfo getRecBookList(String userId) {
        // 第一层缓存：用户推荐书籍列表
        String userRecKey = "user:rec:" + userId;
        List<Recommendation.BookRec> books = getCachedUserRecommendations(userRecKey, userId);

        // 第二层缓存：书籍详细信息
        List<MallBooks> bookRecList = loadBooksWithCache(books);

        return getDataTable(bookRecList);
    }

    /**
     * 带缓存的用户推荐查询
     */
    private List<Recommendation.BookRec> getCachedUserRecommendations(String cacheKey, String userId) {
        // 尝试从缓存获取
        List<Recommendation.BookRec> books = (List<Recommendation.BookRec>) redisTemplate.opsForValue().get(cacheKey);

        if (books != null) {
            return books;
        }

        // 缓存未命中时查询推荐服务（不再依赖参数）
        handleRecThread(userId, MODEL_TYPE_DEEP_FM);
        Recommendation userRec = null;
        for (Recommendation recommendation : backUp) {
            if (recommendation.getUserId().equals(userId)) {
                userRec = recommendation;
            }
        }
        books = (userRec != null) ? userRec.getBooks() : Collections.emptyList();

        // 缓存策略：正常数据1小时，空数据30秒防穿透
        redisTemplate.opsForValue().set(cacheKey, books,
                books.isEmpty() ? 30 : 60, TimeUnit.MINUTES);

        return books;
    }

    /**
     * 带缓存的书籍加载
     */
    private List<MallBooks> loadBooksWithCache(List<Recommendation.BookRec> books) {
        List<MallBooks> result = new ArrayList<>();

        for (Recommendation.BookRec book : books) {
            String bookKey = "book:detail:" + book.getBookId();
//            MallBooks cachedBook = (MallBooks) redisTemplate.opsForValue().get(bookKey);
            MallBooks cachedBook = mallBooksService.selectMallBooksByBookId(book.getBookId());
            if (cachedBook == null || cachedBook.getBookId() == null) {
                cachedBook = loadAndCacheBook(book.getBookId(), bookKey);
            }

            if (cachedBook != null) {
                result.add(cachedBook);
            }
        }
        return result;
    }

    /**
     * 带空值保护的书籍缓存
     */
    private MallBooks loadAndCacheBook(String bookId, String cacheKey) {
        MallBooks book = mallBooksService.selectMallBooksByBookId(bookId);

        if (book != null) {
            redisTemplate.opsForValue().set(cacheKey, book,
                    ThreadLocalRandom.current().nextInt(55, 65), // 随机过期防雪崩
                    TimeUnit.MINUTES);
        } else {
            // 空值保护：缓存伪对象
            MallBooks empty = new MallBooks();
            empty.setBookId("NULL");
            redisTemplate.opsForValue().set(cacheKey, empty, 2, TimeUnit.MINUTES);
        }
        return book;
    }


    private TableDataInfo handleRecThread(String userId, String modelType) {
        ExecutorService executor = (ExecutorService) SpringUtils.getBean("dataQueryPool");

        try {
            // 第一阶段：并行加载基础数据
            CompletableFuture<List<MallUserProfiles>> userProfilesFuture = CompletableFuture
                    .supplyAsync(() -> mallUserProfilesService.selectMallUserProfilesList(null), executor);

            CompletableFuture<List<MallCart>> cartsFuture = CompletableFuture
                    .supplyAsync(() -> mallCartService.selectMallCartList(null), executor);

            CompletableFuture<List<MallCollect>> collectsFuture = CompletableFuture
                    .supplyAsync(() -> mallCollectService.selectMallCollectList(null), executor);

            CompletableFuture<List<MallComment>> commentsFuture = CompletableFuture
                    .supplyAsync(() -> mallCommentService.selectMallCommentList(null), executor);

            CompletableFuture<List<MallOrders>> ordersFuture = CompletableFuture
                    .supplyAsync(() -> mallOrdersService.selectMallOrdersList(null), executor);

            CompletableFuture<List<MallBooks>> booksFuture = CompletableFuture
                    .supplyAsync(() -> mallBooksService.selectMallBooksList(null), executor);

            CompletableFuture<List<MallBookTags>> bookTagsFuture = CompletableFuture
                    .supplyAsync(() -> mallBookTagsService.selectMallBookTagsList(null), executor)
                    .thenCombineAsync(
                            CompletableFuture.supplyAsync(() -> mallTagsService.selectMallTagsList(null), executor),
                            (tags, allTags) -> tags,  // 合并相关数据
                            executor
                    );

            // 第二阶段：并行构建特征
            CompletableFuture<MallRecBooksUserDto> userDtoFuture = CompletableFuture
                    .allOf(userProfilesFuture, cartsFuture, collectsFuture, commentsFuture, ordersFuture)
                    .thenApplyAsync(v -> {
                        return featureService.buildUserDto(
                                userProfilesFuture.join(),
                                cartsFuture.join(),
                                collectsFuture.join(),
                                commentsFuture.join(),
                                ordersFuture.join()
                        );
                    }, executor);

            CompletableFuture<MallRecBooksBookDto> bookDtoFuture = booksFuture
                    .thenCombineAsync(bookTagsFuture, (books, tags) -> {
                        return featureService.buildBooksDto(books, tags, mallTagsService.selectMallTagsList(null));
                    }, executor);

            // 第三阶段：特征拼接与模型预测
            return userDtoFuture
                    .thenCombineAsync(bookDtoFuture, (userDto, bookDto) -> {
                        String featuresJson = featureService.buildFeature(modelType, userDto, bookDto);
                        logger.info("FeaturesJson: {}", abbreviate(featuresJson, 1000));

                        return modelType.equals(MODEL_TYPE_DEEP_FM) ?
                                modelService.batchPredictByDeepFM(featuresJson) :
                                modelService.batchPredictByNeuralCF(featuresJson);
                    }, executor)
                    .thenApplyAsync(predictions -> {
                        List<Recommendation> recommendations = modelService.processPredictions(
                                predictions,
                                userDtoFuture.join(),
                                bookDtoFuture.join()
                        );
                        backUp =  new CopyOnWriteArrayList<>(recommendations) ;
                        return getRecBookList(recommendations, userId);
                    }, executor)
                    .exceptionally(e -> {
                        logger.error("Processing failed", e);
                        return buildErrorResponse();
                    })
                    .get(10, TimeUnit.SECONDS); // 设置整体超时

        } catch (TimeoutException e) {
            logger.warn("Request timeout");
            return buildTimeoutResponse();
        } catch (Exception e) {
            logger.error("System error", e);
            return buildErrorResponse();
        }
    }

    // 辅助方法
    private TableDataInfo buildErrorResponse() {
        TableDataInfo rsp = new TableDataInfo();
        rsp.setCode(HttpStatus.NOT_FOUND);
        rsp.setMsg("请求处理失败");
        return rsp;
    }

    private TableDataInfo buildTimeoutResponse() {
        TableDataInfo rsp = new TableDataInfo();
        rsp.setCode(HttpStatus.NOT_FOUND);
        rsp.setMsg("请求超时");
        return rsp;
    }

    private String abbreviate(String str, int maxWidth) {
        return str.length() > maxWidth ? str.substring(0, maxWidth) + "..." : str;
    }
}
