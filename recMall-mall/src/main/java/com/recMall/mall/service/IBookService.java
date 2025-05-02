package com.recMall.mall.service;

import java.util.List;
import com.recMall.mall.domain.Book;

/**
 * 图书核心信息Service接口
 * 
 * @author recMall
 * @date 2025-04-18
 */
public interface IBookService 
{
    /**
     * 查询图书核心信息
     * 
     * @param bookId 图书核心信息主键
     * @return 图书核心信息
     */
    public Book selectBookByBookId(String bookId);

    /**
     * 查询图书核心信息列表
     * 
     * @param book 图书核心信息
     * @return 图书核心信息集合
     */
    public List<Book> selectBookList(Book book);

    /**
     * 新增图书核心信息
     * 
     * @param book 图书核心信息
     * @return 结果
     */
    public int insertBook(Book book);

    /**
     * 修改图书核心信息
     * 
     * @param book 图书核心信息
     * @return 结果
     */
    public int updateBook(Book book);

    /**
     * 批量删除图书核心信息
     * 
     * @param bookIds 需要删除的图书核心信息主键集合
     * @return 结果
     */
    public int deleteBookByBookIds(String[] bookIds);

    /**
     * 删除图书核心信息信息
     * 
     * @param bookId 图书核心信息主键
     * @return 结果
     */
    public int deleteBookByBookId(String bookId);
}
