package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.MallBookTagsMapper;
import com.recMall.mall.domain.MallBookTags;
import com.recMall.mall.service.IMallBookTagsService;

/**
 * 书籍与标签的关联关系Service业务层处理
 * 
 * @author recMall
 * @date 2025-05-03
 */
@Service
public class MallBookTagsServiceImpl implements IMallBookTagsService 
{
    @Autowired
    private MallBookTagsMapper mallBookTagsMapper;

    /**
     * 查询书籍与标签的关联关系
     * 
     * @param bookId 书籍与标签的关联关系主键
     * @return 书籍与标签的关联关系
     */
    @Override
    public MallBookTags selectMallBookTagsByBookId(String bookId)
    {
        return mallBookTagsMapper.selectMallBookTagsByBookId(bookId);
    }

    /**
     * 查询书籍与标签的关联关系列表
     * 
     * @param mallBookTags 书籍与标签的关联关系
     * @return 书籍与标签的关联关系
     */
    @Override
    public List<MallBookTags> selectMallBookTagsList(MallBookTags mallBookTags)
    {
        return mallBookTagsMapper.selectMallBookTagsList(mallBookTags);
    }

    /**
     * 新增书籍与标签的关联关系
     * 
     * @param mallBookTags 书籍与标签的关联关系
     * @return 结果
     */
    @Override
    public int insertMallBookTags(MallBookTags mallBookTags)
    {
        return mallBookTagsMapper.insertMallBookTags(mallBookTags);
    }

    /**
     * 修改书籍与标签的关联关系
     * 
     * @param mallBookTags 书籍与标签的关联关系
     * @return 结果
     */
    @Override
    public int updateMallBookTags(MallBookTags mallBookTags)
    {
        return mallBookTagsMapper.updateMallBookTags(mallBookTags);
    }

    /**
     * 批量删除书籍与标签的关联关系
     * 
     * @param bookIds 需要删除的书籍与标签的关联关系主键
     * @return 结果
     */
    @Override
    public int deleteMallBookTagsByBookIds(String[] bookIds)
    {
        return mallBookTagsMapper.deleteMallBookTagsByBookIds(bookIds);
    }

    /**
     * 删除书籍与标签的关联关系信息
     * 
     * @param bookId 书籍与标签的关联关系主键
     * @return 结果
     */
    @Override
    public int deleteMallBookTagsByBookId(String bookId)
    {
        return mallBookTagsMapper.deleteMallBookTagsByBookId(bookId);
    }
}
