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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private static final Logger log = LoggerFactory.getLogger(MallRecBooksController.class);
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

    @PreAuthorize("@ss.hasPermi('mall:books:query')")
    @RequestMapping("/deep-fm{userId}")
    public TableDataInfo deepFM(@PathVariable("userId") String userId) {
        return handleRecThread(userId, MODEL_TYPE_DEEP_FM);
    }

    @PreAuthorize("@ss.hasPermi('mall:books:query')")
    @RequestMapping("/neural-cf{userId}")
    public TableDataInfo neuralCF(@PathVariable("userId") String userId) {
        return handleRecThread(userId, MODEL_TYPE_NEURAL_CF);
    }

    private TableDataInfo handleRecommendation(String userId, String modelType) {
        String userRecKey = "user:rec:" + modelType + userId;
        // 尝试从Redis获取用户推荐书籍列表
        List<Recommendation.BookRec> redisBooks = (List<Recommendation.BookRec>) redisTemplate.opsForValue().get(userRecKey);

        if (redisBooks == null) {
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
                return getRecBookList(recommendations, userId, modelType);
            } catch (Exception e) {
                e.printStackTrace();
                TableDataInfo rspData = new TableDataInfo();
                rspData.setCode(HttpStatus.NOT_FOUND);
                rspData.setMsg("系统异常");
                rspData.setRows(null);
                rspData.setTotal(0);
                return rspData;
            }
        } else {
            List<MallBooks> bookRecList = new ArrayList<>();
            for (Recommendation.BookRec book : redisBooks) {
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
    }

    private TableDataInfo getRecBookList(List<Recommendation> recommendations, String userId, String modelType) {
        Logger log = LoggerFactory.getLogger(this.getClass());
        log.info("[开始处理] 用户ID: {}", userId);

        String userRecKey = "user:rec:" + modelType + ":" + userId;
        List<Recommendation.BookRec> books = (List<Recommendation.BookRec>) redisTemplate.opsForValue().get(userRecKey);
        log.debug("尝试读取Redis缓存: key={}, 结果={}", userRecKey, (books != null) ? "命中" : "未命中");

        if (books == null) {
            log.info("缓存未命中, 开始遍历推荐列表...");
            List<Recommendation.BookRec> currentUserBooks = null;

            for (Recommendation rec : recommendations) {
                String loopUserId = rec.getUserId();
                String userKey = "user:rec:" + modelType + ":" + loopUserId;
                List<Recommendation.BookRec> cachedBooks = (List<Recommendation.BookRec>) redisTemplate.opsForValue().get(userKey);

                if (cachedBooks == null) {
                    log.debug("正在处理用户 {} 的缓存注册", loopUserId);
                    List<Recommendation.BookRec> recBooks = rec.getBooks();

                    // 记录缓存设置信息
                    String cacheStatus = redisTemplate.opsForValue().setIfAbsent(
                            userKey,
                            recBooks,
                            recBooks.isEmpty() ? 1 : 60 * 48,
                            TimeUnit.MINUTES
                    ) ? "新缓存写入" : "已存在（跳过）";

                    log.info("用户 {} 缓存状态: {}, 数据量={}, 过期时间={}分钟",
                            loopUserId,
                            cacheStatus,
                            recBooks.size(),
                            recBooks.isEmpty() ? 1 : 60 * 48);

                    // 记录当前目标用户的书籍
                    if (loopUserId.equals(userId)) {
                        currentUserBooks = recBooks;
                        log.debug("发现目标用户 {} 的原始数据", userId);
                    }
                } else {
                    log.debug("用户 {} 的缓存已存在，跳过处理", loopUserId);
                }
            }

            // 确定最终数据源
            books = (currentUserBooks != null) ? currentUserBooks : Collections.emptyList();
            log.info("确定最终数据: 用户 {} 的书籍数量={}", userId, books.size());

            // 缓存当前用户数据
            long expireTime = books.isEmpty() ? 1 : 60;
            redisTemplate.opsForValue().set(userRecKey, books, expireTime, TimeUnit.MINUTES);
            log.info("写入用户缓存: key={}, 数量={}, 过期时间={}分钟",
                    userRecKey,
                    books.size(),
                    expireTime);
        } else {
            log.info("缓存命中有效数据: 用户 {} 书籍数量={}", userId, books.size());
        }

        // 书籍信息处理阶段
        log.debug("开始处理书籍详细信息...");
        List<MallBooks> bookRecList = new ArrayList<>();
        int missingCount = 0;

        for (Recommendation.BookRec book : books) {
            String bookId = book.getBookId();
            String bookKey = "book:info:" + bookId;
            MallBooks mallBook = (MallBooks) redisTemplate.opsForValue().get(bookKey);
            log.debug("书籍 {} 缓存状态: {}", bookId, (mallBook != null) ? "命中" : "未命中");

            if (mallBook == null) {
                log.info("查询数据库获取书籍: {}", bookId);
                mallBook = mallBooksService.selectMallBooksByBookId(bookId);

                if (mallBook != null) {
                    redisTemplate.opsForValue().set(bookKey, mallBook, 1, TimeUnit.HOURS);
                    log.info("缓存书籍数据: {} (1小时)", bookId);
                } else {
                    redisTemplate.opsForValue().set(bookKey, new MallBooks(), 1, TimeUnit.MINUTES);
                    log.warn("缓存空书籍占位符: {} (1分钟)", bookId);
                    missingCount++;
                }
            }

            if (mallBook != null && mallBook.getBookId() != null) {
                bookRecList.add(mallBook);
            }
        }

        log.info("处理完成: 总书籍数={}, 缺失书籍数={}", bookRecList.size(), missingCount);
        return getDataTable(bookRecList);
    }

    private TableDataInfo handleRecThread(String userId, String modelType) {
        ExecutorService executor = (ExecutorService) SpringUtils.getBean("dataQueryPool");
        String userRecKey = "user:rec:" + modelType + ":" + userId;
        // 尝试从Redis获取用户推荐书籍列表
        List<Recommendation.BookRec> redisBooks = (List<Recommendation.BookRec>) redisTemplate.opsForValue().get(userRecKey);

        if (redisBooks == null) {
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
                            backUp = new CopyOnWriteArrayList<Recommendation>(recommendations);
                            return getRecBookList(recommendations, userId, modelType);
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
        } else {
            List<MallBooks> bookRecList = new ArrayList<>();
            for (Recommendation.BookRec book : redisBooks) {
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
