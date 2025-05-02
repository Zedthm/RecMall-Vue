package com.recMall.mall.service;

import java.util.List;
import com.recMall.mall.domain.UserTag;

/**
 * 用户标签字典Service接口
 * 
 * @author recMall
 * @date 2025-04-18
 */
public interface IUserTagService 
{
    /**
     * 查询用户标签字典
     * 
     * @param tagId 用户标签字典主键
     * @return 用户标签字典
     */
    public UserTag selectUserTagByTagId(String tagId);

    /**
     * 查询用户标签字典列表
     * 
     * @param userTag 用户标签字典
     * @return 用户标签字典集合
     */
    public List<UserTag> selectUserTagList(UserTag userTag);

    /**
     * 新增用户标签字典
     * 
     * @param userTag 用户标签字典
     * @return 结果
     */
    public int insertUserTag(UserTag userTag);

    /**
     * 修改用户标签字典
     * 
     * @param userTag 用户标签字典
     * @return 结果
     */
    public int updateUserTag(UserTag userTag);

    /**
     * 批量删除用户标签字典
     * 
     * @param tagIds 需要删除的用户标签字典主键集合
     * @return 结果
     */
    public int deleteUserTagByTagIds(String[] tagIds);

    /**
     * 删除用户标签字典信息
     * 
     * @param tagId 用户标签字典主键
     * @return 结果
     */
    public int deleteUserTagByTagId(String tagId);
}
