package com.recMall.mall.service;

import java.util.List;
import com.recMall.mall.domain.MallTags;

/**
 * 存储系统标签信息Service接口
 * 
 * @author recMall
 * @date 2025-05-03
 */
public interface IMallTagsService 
{
    /**
     * 查询存储系统标签信息
     * 
     * @param tagId 存储系统标签信息主键
     * @return 存储系统标签信息
     */
    public MallTags selectMallTagsByTagId(String tagId);

    /**
     * 查询存储系统标签信息列表
     * 
     * @param mallTags 存储系统标签信息
     * @return 存储系统标签信息集合
     */
    public List<MallTags> selectMallTagsList(MallTags mallTags);

    /**
     * 新增存储系统标签信息
     * 
     * @param mallTags 存储系统标签信息
     * @return 结果
     */
    public int insertMallTags(MallTags mallTags);

    /**
     * 修改存储系统标签信息
     * 
     * @param mallTags 存储系统标签信息
     * @return 结果
     */
    public int updateMallTags(MallTags mallTags);

    /**
     * 批量删除存储系统标签信息
     * 
     * @param tagIds 需要删除的存储系统标签信息主键集合
     * @return 结果
     */
    public int deleteMallTagsByTagIds(String[] tagIds);

    /**
     * 删除存储系统标签信息信息
     * 
     * @param tagId 存储系统标签信息主键
     * @return 结果
     */
    public int deleteMallTagsByTagId(String tagId);
}
