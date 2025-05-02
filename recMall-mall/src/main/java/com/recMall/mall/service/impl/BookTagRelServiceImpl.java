package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.BookTagRelMapper;
import com.recMall.mall.domain.BookTagRel;
import com.recMall.mall.service.IBookTagRelService;

/**
 * 书籍与标签多对多关系Service业务层处理
 * 
 * @author recMall
 * @date 2025-04-18
 */
@Service
public class BookTagRelServiceImpl implements IBookTagRelService 
{
    @Autowired
    private BookTagRelMapper bookTagRelMapper;

    /**
     * 查询书籍与标签多对多关系
     * 
     * @param bookId 书籍与标签多对多关系主键
     * @return 书籍与标签多对多关系
     */
    @Override
    public BookTagRel selectBookTagRelByBookId(String bookId)
    {
        return bookTagRelMapper.selectBookTagRelByBookId(bookId);
    }

    /**
     * 查询书籍与标签多对多关系列表
     * 
     * @param bookTagRel 书籍与标签多对多关系
     * @return 书籍与标签多对多关系
     */
    @Override
    public List<BookTagRel> selectBookTagRelList(BookTagRel bookTagRel)
    {
        return bookTagRelMapper.selectBookTagRelList(bookTagRel);
    }

    /**
     * 新增书籍与标签多对多关系
     * 
     * @param bookTagRel 书籍与标签多对多关系
     * @return 结果
     */
    @Override
    public int insertBookTagRel(BookTagRel bookTagRel)
    {
        return bookTagRelMapper.insertBookTagRel(bookTagRel);
    }

    /**
     * 修改书籍与标签多对多关系
     * 
     * @param bookTagRel 书籍与标签多对多关系
     * @return 结果
     */
    @Override
    public int updateBookTagRel(BookTagRel bookTagRel)
    {
        return bookTagRelMapper.updateBookTagRel(bookTagRel);
    }

    /**
     * 批量删除书籍与标签多对多关系
     * 
     * @param bookIds 需要删除的书籍与标签多对多关系主键
     * @return 结果
     */
    @Override
    public int deleteBookTagRelByBookIds(String[] bookIds)
    {
        return bookTagRelMapper.deleteBookTagRelByBookIds(bookIds);
    }

    /**
     * 删除书籍与标签多对多关系信息
     * 
     * @param bookId 书籍与标签多对多关系主键
     * @return 结果
     */
    @Override
    public int deleteBookTagRelByBookId(String bookId)
    {
        return bookTagRelMapper.deleteBookTagRelByBookId(bookId);
    }
}
