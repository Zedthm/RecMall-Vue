package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.BookCategoryMapper;
import com.recMall.mall.domain.BookCategory;
import com.recMall.mall.service.IBookCategoryService;

/**
 * 书籍类别Service业务层处理
 * 
 * @author recMall
 * @date 2025-04-18
 */
@Service
public class BookCategoryServiceImpl implements IBookCategoryService 
{
    @Autowired
    private BookCategoryMapper bookCategoryMapper;

    /**
     * 查询书籍类别
     * 
     * @param categoryId 书籍类别主键
     * @return 书籍类别
     */
    @Override
    public BookCategory selectBookCategoryByCategoryId(Long categoryId)
    {
        return bookCategoryMapper.selectBookCategoryByCategoryId(categoryId);
    }

    /**
     * 查询书籍类别列表
     * 
     * @param bookCategory 书籍类别
     * @return 书籍类别
     */
    @Override
    public List<BookCategory> selectBookCategoryList(BookCategory bookCategory)
    {
        return bookCategoryMapper.selectBookCategoryList(bookCategory);
    }

    /**
     * 新增书籍类别
     * 
     * @param bookCategory 书籍类别
     * @return 结果
     */
    @Override
    public int insertBookCategory(BookCategory bookCategory)
    {
        return bookCategoryMapper.insertBookCategory(bookCategory);
    }

    /**
     * 修改书籍类别
     * 
     * @param bookCategory 书籍类别
     * @return 结果
     */
    @Override
    public int updateBookCategory(BookCategory bookCategory)
    {
        return bookCategoryMapper.updateBookCategory(bookCategory);
    }

    /**
     * 批量删除书籍类别
     * 
     * @param categoryIds 需要删除的书籍类别主键
     * @return 结果
     */
    @Override
    public int deleteBookCategoryByCategoryIds(Long[] categoryIds)
    {
        return bookCategoryMapper.deleteBookCategoryByCategoryIds(categoryIds);
    }

    /**
     * 删除书籍类别信息
     * 
     * @param categoryId 书籍类别主键
     * @return 结果
     */
    @Override
    public int deleteBookCategoryByCategoryId(Long categoryId)
    {
        return bookCategoryMapper.deleteBookCategoryByCategoryId(categoryId);
    }
}
