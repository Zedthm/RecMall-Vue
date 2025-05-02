package com.recMall.mall.mapper;

import java.util.List;
import com.recMall.mall.domain.BookCategory;

/**
 * 书籍类别Mapper接口
 * 
 * @author recMall
 * @date 2025-04-18
 */
public interface BookCategoryMapper 
{
    /**
     * 查询书籍类别
     * 
     * @param categoryId 书籍类别主键
     * @return 书籍类别
     */
    public BookCategory selectBookCategoryByCategoryId(Long categoryId);

    /**
     * 查询书籍类别列表
     * 
     * @param bookCategory 书籍类别
     * @return 书籍类别集合
     */
    public List<BookCategory> selectBookCategoryList(BookCategory bookCategory);

    /**
     * 新增书籍类别
     * 
     * @param bookCategory 书籍类别
     * @return 结果
     */
    public int insertBookCategory(BookCategory bookCategory);

    /**
     * 修改书籍类别
     * 
     * @param bookCategory 书籍类别
     * @return 结果
     */
    public int updateBookCategory(BookCategory bookCategory);

    /**
     * 删除书籍类别
     * 
     * @param categoryId 书籍类别主键
     * @return 结果
     */
    public int deleteBookCategoryByCategoryId(Long categoryId);

    /**
     * 批量删除书籍类别
     * 
     * @param categoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBookCategoryByCategoryIds(Long[] categoryIds);
}
