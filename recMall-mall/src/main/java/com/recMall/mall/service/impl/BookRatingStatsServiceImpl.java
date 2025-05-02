package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.BookRatingStatsMapper;
import com.recMall.mall.domain.BookRatingStats;
import com.recMall.mall.service.IBookRatingStatsService;

/**
 * 书籍评分统计Service业务层处理
 * 
 * @author recMall
 * @date 2025-04-18
 */
@Service
public class BookRatingStatsServiceImpl implements IBookRatingStatsService 
{
    @Autowired
    private BookRatingStatsMapper bookRatingStatsMapper;

    /**
     * 查询书籍评分统计
     * 
     * @param bookId 书籍评分统计主键
     * @return 书籍评分统计
     */
    @Override
    public BookRatingStats selectBookRatingStatsByBookId(String bookId)
    {
        return bookRatingStatsMapper.selectBookRatingStatsByBookId(bookId);
    }

    /**
     * 查询书籍评分统计列表
     * 
     * @param bookRatingStats 书籍评分统计
     * @return 书籍评分统计
     */
    @Override
    public List<BookRatingStats> selectBookRatingStatsList(BookRatingStats bookRatingStats)
    {
        return bookRatingStatsMapper.selectBookRatingStatsList(bookRatingStats);
    }

    /**
     * 新增书籍评分统计
     * 
     * @param bookRatingStats 书籍评分统计
     * @return 结果
     */
    @Override
    public int insertBookRatingStats(BookRatingStats bookRatingStats)
    {
        return bookRatingStatsMapper.insertBookRatingStats(bookRatingStats);
    }

    /**
     * 修改书籍评分统计
     * 
     * @param bookRatingStats 书籍评分统计
     * @return 结果
     */
    @Override
    public int updateBookRatingStats(BookRatingStats bookRatingStats)
    {
        return bookRatingStatsMapper.updateBookRatingStats(bookRatingStats);
    }

    /**
     * 批量删除书籍评分统计
     * 
     * @param bookIds 需要删除的书籍评分统计主键
     * @return 结果
     */
    @Override
    public int deleteBookRatingStatsByBookIds(String[] bookIds)
    {
        return bookRatingStatsMapper.deleteBookRatingStatsByBookIds(bookIds);
    }

    /**
     * 删除书籍评分统计信息
     * 
     * @param bookId 书籍评分统计主键
     * @return 结果
     */
    @Override
    public int deleteBookRatingStatsByBookId(String bookId)
    {
        return bookRatingStatsMapper.deleteBookRatingStatsByBookId(bookId);
    }
}
