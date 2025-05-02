package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.BookTagMapper;
import com.recMall.mall.domain.BookTag;
import com.recMall.mall.service.IBookTagService;

/**
 * 书籍标签字典Service业务层处理
 * 
 * @author recMall
 * @date 2025-04-18
 */
@Service
public class BookTagServiceImpl implements IBookTagService 
{
    @Autowired
    private BookTagMapper bookTagMapper;

    /**
     * 查询书籍标签字典
     * 
     * @param tagId 书籍标签字典主键
     * @return 书籍标签字典
     */
    @Override
    public BookTag selectBookTagByTagId(String tagId)
    {
        return bookTagMapper.selectBookTagByTagId(tagId);
    }

    /**
     * 查询书籍标签字典列表
     * 
     * @param bookTag 书籍标签字典
     * @return 书籍标签字典
     */
    @Override
    public List<BookTag> selectBookTagList(BookTag bookTag)
    {
        return bookTagMapper.selectBookTagList(bookTag);
    }

    /**
     * 新增书籍标签字典
     * 
     * @param bookTag 书籍标签字典
     * @return 结果
     */
    @Override
    public int insertBookTag(BookTag bookTag)
    {
        return bookTagMapper.insertBookTag(bookTag);
    }

    /**
     * 修改书籍标签字典
     * 
     * @param bookTag 书籍标签字典
     * @return 结果
     */
    @Override
    public int updateBookTag(BookTag bookTag)
    {
        return bookTagMapper.updateBookTag(bookTag);
    }

    /**
     * 批量删除书籍标签字典
     * 
     * @param tagIds 需要删除的书籍标签字典主键
     * @return 结果
     */
    @Override
    public int deleteBookTagByTagIds(String[] tagIds)
    {
        return bookTagMapper.deleteBookTagByTagIds(tagIds);
    }

    /**
     * 删除书籍标签字典信息
     * 
     * @param tagId 书籍标签字典主键
     * @return 结果
     */
    @Override
    public int deleteBookTagByTagId(String tagId)
    {
        return bookTagMapper.deleteBookTagByTagId(tagId);
    }
}
