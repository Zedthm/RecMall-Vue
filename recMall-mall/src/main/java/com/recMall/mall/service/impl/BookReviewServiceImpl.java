package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.BookReviewMapper;
import com.recMall.mall.domain.BookReview;
import com.recMall.mall.service.IBookReviewService;

/**
 * 用户书评数据Service业务层处理
 * 
 * @author recMall
 * @date 2025-04-18
 */
@Service
public class BookReviewServiceImpl implements IBookReviewService 
{
    @Autowired
    private BookReviewMapper bookReviewMapper;

    /**
     * 查询用户书评数据
     * 
     * @param reviewId 用户书评数据主键
     * @return 用户书评数据
     */
    @Override
    public BookReview selectBookReviewByReviewId(String reviewId)
    {
        return bookReviewMapper.selectBookReviewByReviewId(reviewId);
    }

    /**
     * 查询用户书评数据列表
     * 
     * @param bookReview 用户书评数据
     * @return 用户书评数据
     */
    @Override
    public List<BookReview> selectBookReviewList(BookReview bookReview)
    {
        return bookReviewMapper.selectBookReviewList(bookReview);
    }

    /**
     * 新增用户书评数据
     * 
     * @param bookReview 用户书评数据
     * @return 结果
     */
    @Override
    public int insertBookReview(BookReview bookReview)
    {
        return bookReviewMapper.insertBookReview(bookReview);
    }

    /**
     * 修改用户书评数据
     * 
     * @param bookReview 用户书评数据
     * @return 结果
     */
    @Override
    public int updateBookReview(BookReview bookReview)
    {
        return bookReviewMapper.updateBookReview(bookReview);
    }

    /**
     * 批量删除用户书评数据
     * 
     * @param reviewIds 需要删除的用户书评数据主键
     * @return 结果
     */
    @Override
    public int deleteBookReviewByReviewIds(String[] reviewIds)
    {
        return bookReviewMapper.deleteBookReviewByReviewIds(reviewIds);
    }

    /**
     * 删除用户书评数据信息
     * 
     * @param reviewId 用户书评数据主键
     * @return 结果
     */
    @Override
    public int deleteBookReviewByReviewId(String reviewId)
    {
        return bookReviewMapper.deleteBookReviewByReviewId(reviewId);
    }
}
