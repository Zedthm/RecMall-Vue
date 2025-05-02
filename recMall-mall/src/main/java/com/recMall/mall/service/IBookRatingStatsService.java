package com.recMall.mall.service;

import java.util.List;
import com.recMall.mall.domain.BookRatingStats;

/**
 * 书籍评分统计Service接口
 * 
 * @author recMall
 * @date 2025-04-18
 */
public interface IBookRatingStatsService 
{
    /**
     * 查询书籍评分统计
     * 
     * @param bookId 书籍评分统计主键
     * @return 书籍评分统计
     */
    public BookRatingStats selectBookRatingStatsByBookId(String bookId);

    /**
     * 查询书籍评分统计列表
     * 
     * @param bookRatingStats 书籍评分统计
     * @return 书籍评分统计集合
     */
    public List<BookRatingStats> selectBookRatingStatsList(BookRatingStats bookRatingStats);

    /**
     * 新增书籍评分统计
     * 
     * @param bookRatingStats 书籍评分统计
     * @return 结果
     */
    public int insertBookRatingStats(BookRatingStats bookRatingStats);

    /**
     * 修改书籍评分统计
     * 
     * @param bookRatingStats 书籍评分统计
     * @return 结果
     */
    public int updateBookRatingStats(BookRatingStats bookRatingStats);

    /**
     * 批量删除书籍评分统计
     * 
     * @param bookIds 需要删除的书籍评分统计主键集合
     * @return 结果
     */
    public int deleteBookRatingStatsByBookIds(String[] bookIds);

    /**
     * 删除书籍评分统计信息
     * 
     * @param bookId 书籍评分统计主键
     * @return 结果
     */
    public int deleteBookRatingStatsByBookId(String bookId);
}
