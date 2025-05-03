package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.MallTagsMapper;
import com.recMall.mall.domain.MallTags;
import com.recMall.mall.service.IMallTagsService;

/**
 * 存储系统标签信息Service业务层处理
 * 
 * @author recMall
 * @date 2025-05-03
 */
@Service
public class MallTagsServiceImpl implements IMallTagsService 
{
    @Autowired
    private MallTagsMapper mallTagsMapper;

    /**
     * 查询存储系统标签信息
     * 
     * @param tagId 存储系统标签信息主键
     * @return 存储系统标签信息
     */
    @Override
    public MallTags selectMallTagsByTagId(String tagId)
    {
        return mallTagsMapper.selectMallTagsByTagId(tagId);
    }

    /**
     * 查询存储系统标签信息列表
     * 
     * @param mallTags 存储系统标签信息
     * @return 存储系统标签信息
     */
    @Override
    public List<MallTags> selectMallTagsList(MallTags mallTags)
    {
        return mallTagsMapper.selectMallTagsList(mallTags);
    }

    /**
     * 新增存储系统标签信息
     * 
     * @param mallTags 存储系统标签信息
     * @return 结果
     */
    @Override
    public int insertMallTags(MallTags mallTags)
    {
        return mallTagsMapper.insertMallTags(mallTags);
    }

    /**
     * 修改存储系统标签信息
     * 
     * @param mallTags 存储系统标签信息
     * @return 结果
     */
    @Override
    public int updateMallTags(MallTags mallTags)
    {
        return mallTagsMapper.updateMallTags(mallTags);
    }

    /**
     * 批量删除存储系统标签信息
     * 
     * @param tagIds 需要删除的存储系统标签信息主键
     * @return 结果
     */
    @Override
    public int deleteMallTagsByTagIds(String[] tagIds)
    {
        return mallTagsMapper.deleteMallTagsByTagIds(tagIds);
    }

    /**
     * 删除存储系统标签信息信息
     * 
     * @param tagId 存储系统标签信息主键
     * @return 结果
     */
    @Override
    public int deleteMallTagsByTagId(String tagId)
    {
        return mallTagsMapper.deleteMallTagsByTagId(tagId);
    }
}
