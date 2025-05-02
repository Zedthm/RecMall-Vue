package com.recMall.mall.service;

import java.util.List;
import com.recMall.mall.domain.BookCategoryRel;

/**
 * 书籍类别关系Service接口
 * 
 * @author recMall
 * @date 2025-04-18
 */
public interface IBookCategoryRelService 
{
    /**
     * 查询书籍类别关系
     * 
     * @param categoryId 书籍类别关系主键
     * @return 书籍类别关系
     */
    public BookCategoryRel selectBookCategoryRelByCategoryId(Long categoryId);

    /**
     * 查询书籍类别关系列表
     * 
     * @param bookCategoryRel 书籍类别关系
     * @return 书籍类别关系集合
     */
    public List<BookCategoryRel> selectBookCategoryRelList(BookCategoryRel bookCategoryRel);

    /**
     * 新增书籍类别关系
     * 
     * @param bookCategoryRel 书籍类别关系
     * @return 结果
     */
    public int insertBookCategoryRel(BookCategoryRel bookCategoryRel);

    /**
     * 修改书籍类别关系
     * 
     * @param bookCategoryRel 书籍类别关系
     * @return 结果
     */
    public int updateBookCategoryRel(BookCategoryRel bookCategoryRel);

    /**
     * 批量删除书籍类别关系
     * 
     * @param categoryIds 需要删除的书籍类别关系主键集合
     * @return 结果
     */
    public int deleteBookCategoryRelByCategoryIds(Long[] categoryIds);

    /**
     * 删除书籍类别关系信息
     * 
     * @param categoryId 书籍类别关系主键
     * @return 结果
     */
    public int deleteBookCategoryRelByCategoryId(Long categoryId);
}
