package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.BookMapper;
import com.recMall.mall.domain.Book;
import com.recMall.mall.service.IBookService;

/**
 * 图书核心信息Service业务层处理
 * 
 * @author recMall
 * @date 2025-04-18
 */
@Service
public class BookServiceImpl implements IBookService 
{
    @Autowired
    private BookMapper bookMapper;

    /**
     * 查询图书核心信息
     * 
     * @param bookId 图书核心信息主键
     * @return 图书核心信息
     */
    @Override
    public Book selectBookByBookId(String bookId)
    {
        return bookMapper.selectBookByBookId(bookId);
    }

    /**
     * 查询图书核心信息列表
     * 
     * @param book 图书核心信息
     * @return 图书核心信息
     */
    @Override
    public List<Book> selectBookList(Book book)
    {
        return bookMapper.selectBookList(book);
    }

    /**
     * 新增图书核心信息
     * 
     * @param book 图书核心信息
     * @return 结果
     */
    @Override
    public int insertBook(Book book)
    {
        return bookMapper.insertBook(book);
    }

    /**
     * 修改图书核心信息
     * 
     * @param book 图书核心信息
     * @return 结果
     */
    @Override
    public int updateBook(Book book)
    {
        return bookMapper.updateBook(book);
    }

    /**
     * 批量删除图书核心信息
     * 
     * @param bookIds 需要删除的图书核心信息主键
     * @return 结果
     */
    @Override
    public int deleteBookByBookIds(String[] bookIds)
    {
        return bookMapper.deleteBookByBookIds(bookIds);
    }

    /**
     * 删除图书核心信息信息
     * 
     * @param bookId 图书核心信息主键
     * @return 结果
     */
    @Override
    public int deleteBookByBookId(String bookId)
    {
        return bookMapper.deleteBookByBookId(bookId);
    }
}
