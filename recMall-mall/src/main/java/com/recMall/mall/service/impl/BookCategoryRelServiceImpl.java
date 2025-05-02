package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.BookCategoryRelMapper;
import com.recMall.mall.domain.BookCategoryRel;
import com.recMall.mall.service.IBookCategoryRelService;

/**
 * 书籍类别关系Service业务层处理
 * 
 * @author recMall
 * @date 2025-04-18
 */
@Service
public class BookCategoryRelServiceImpl implements IBookCategoryRelService 
{
    @Autowired
    private BookCategoryRelMapper bookCategoryRelMapper;

    /**
     * 查询书籍类别关系
     * 
     * @param categoryId 书籍类别关系主键
     * @return 书籍类别关系
     */
    @Override
    public BookCategoryRel selectBookCategoryRelByCategoryId(Long categoryId)
    {
        return bookCategoryRelMapper.selectBookCategoryRelByCategoryId(categoryId);
    }

    /**
     * 查询书籍类别关系列表
     * 
     * @param bookCategoryRel 书籍类别关系
     * @return 书籍类别关系
     */
    @Override
    public List<BookCategoryRel> selectBookCategoryRelList(BookCategoryRel bookCategoryRel)
    {
        return bookCategoryRelMapper.selectBookCategoryRelList(bookCategoryRel);
    }

    /**
     * 新增书籍类别关系
     * 
     * @param bookCategoryRel 书籍类别关系
     * @return 结果
     */
    @Override
    public int insertBookCategoryRel(BookCategoryRel bookCategoryRel)
    {
        return bookCategoryRelMapper.insertBookCategoryRel(bookCategoryRel);
    }

    /**
     * 修改书籍类别关系
     * 
     * @param bookCategoryRel 书籍类别关系
     * @return 结果
     */
    @Override
    public int updateBookCategoryRel(BookCategoryRel bookCategoryRel)
    {
        return bookCategoryRelMapper.updateBookCategoryRel(bookCategoryRel);
    }

    /**
     * 批量删除书籍类别关系
     * 
     * @param categoryIds 需要删除的书籍类别关系主键
     * @return 结果
     */
    @Override
    public int deleteBookCategoryRelByCategoryIds(Long[] categoryIds)
    {
        return bookCategoryRelMapper.deleteBookCategoryRelByCategoryIds(categoryIds);
    }

    /**
     * 删除书籍类别关系信息
     * 
     * @param categoryId 书籍类别关系主键
     * @return 结果
     */
    @Override
    public int deleteBookCategoryRelByCategoryId(Long categoryId)
    {
        return bookCategoryRelMapper.deleteBookCategoryRelByCategoryId(categoryId);
    }
}
