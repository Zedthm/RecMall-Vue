package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.BookAuthorRelMapper;
import com.recMall.mall.domain.BookAuthorRel;
import com.recMall.mall.service.IBookAuthorRelService;

/**
 * 书籍与作者多对多关系Service业务层处理
 * 
 * @author recMall
 * @date 2025-04-18
 */
@Service
public class BookAuthorRelServiceImpl implements IBookAuthorRelService 
{
    @Autowired
    private BookAuthorRelMapper bookAuthorRelMapper;

    /**
     * 查询书籍与作者多对多关系
     * 
     * @param bookId 书籍与作者多对多关系主键
     * @return 书籍与作者多对多关系
     */
    @Override
    public BookAuthorRel selectBookAuthorRelByBookId(String bookId)
    {
        return bookAuthorRelMapper.selectBookAuthorRelByBookId(bookId);
    }

    /**
     * 查询书籍与作者多对多关系列表
     * 
     * @param bookAuthorRel 书籍与作者多对多关系
     * @return 书籍与作者多对多关系
     */
    @Override
    public List<BookAuthorRel> selectBookAuthorRelList(BookAuthorRel bookAuthorRel)
    {
        return bookAuthorRelMapper.selectBookAuthorRelList(bookAuthorRel);
    }

    /**
     * 新增书籍与作者多对多关系
     * 
     * @param bookAuthorRel 书籍与作者多对多关系
     * @return 结果
     */
    @Override
    public int insertBookAuthorRel(BookAuthorRel bookAuthorRel)
    {
        return bookAuthorRelMapper.insertBookAuthorRel(bookAuthorRel);
    }

    /**
     * 修改书籍与作者多对多关系
     * 
     * @param bookAuthorRel 书籍与作者多对多关系
     * @return 结果
     */
    @Override
    public int updateBookAuthorRel(BookAuthorRel bookAuthorRel)
    {
        return bookAuthorRelMapper.updateBookAuthorRel(bookAuthorRel);
    }

    /**
     * 批量删除书籍与作者多对多关系
     * 
     * @param bookIds 需要删除的书籍与作者多对多关系主键
     * @return 结果
     */
    @Override
    public int deleteBookAuthorRelByBookIds(String[] bookIds)
    {
        return bookAuthorRelMapper.deleteBookAuthorRelByBookIds(bookIds);
    }

    /**
     * 删除书籍与作者多对多关系信息
     * 
     * @param bookId 书籍与作者多对多关系主键
     * @return 结果
     */
    @Override
    public int deleteBookAuthorRelByBookId(String bookId)
    {
        return bookAuthorRelMapper.deleteBookAuthorRelByBookId(bookId);
    }
}
