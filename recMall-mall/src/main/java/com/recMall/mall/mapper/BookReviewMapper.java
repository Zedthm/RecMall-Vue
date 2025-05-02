package com.recMall.mall.mapper;

import java.util.List;
import com.recMall.mall.domain.BookReview;

/**
 * 用户书评数据Mapper接口
 * 
 * @author recMall
 * @date 2025-04-18
 */
public interface BookReviewMapper 
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
     * 删除用户书评数据
     * 
     * @param reviewId 用户书评数据主键
     * @return 结果
     */
    public int deleteBookReviewByReviewId(String reviewId);

    /**
     * 批量删除用户书评数据
     * 
     * @param reviewIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBookReviewByReviewIds(String[] reviewIds);
}
