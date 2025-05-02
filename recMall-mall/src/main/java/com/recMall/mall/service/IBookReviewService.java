package com.recMall.mall.service;

import java.util.List;
import com.recMall.mall.domain.BookReview;

/**
 * 用户书评数据Service接口
 * 
 * @author recMall
 * @date 2025-04-18
 */
public interface IBookReviewService 
{
    /**
     * 查询用户书评数据
     * 
     * @param reviewId 用户书评数据主键
     * @return 用户书评数据
     */
    public BookReview selectBookReviewByReviewId(String reviewId);

    /**
     * 查询用户书评数据列表
     * 
     * @param bookReview 用户书评数据
     * @return 用户书评数据集合
     */
    public List<BookReview> selectBookReviewList(BookReview bookReview);

    /**
     * 新增用户书评数据
     * 
     * @param bookReview 用户书评数据
     * @return 结果
     */
    public int insertBookReview(BookReview bookReview);

    /**
     * 修改用户书评数据
     * 
     * @param bookReview 用户书评数据
     * @return 结果
     */
    public int updateBookReview(BookReview bookReview);

    /**
     * 批量删除用户书评数据
     * 
     * @param reviewIds 需要删除的用户书评数据主键集合
     * @return 结果
     */
    public int deleteBookReviewByReviewIds(String[] reviewIds);

    /**
     * 删除用户书评数据信息
     * 
     * @param reviewId 用户书评数据主键
     * @return 结果
     */
    public int deleteBookReviewByReviewId(String reviewId);
}
