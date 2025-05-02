package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.UserTagMapper;
import com.recMall.mall.domain.UserTag;
import com.recMall.mall.service.IUserTagService;

/**
 * 用户标签字典Service业务层处理
 * 
 * @author recMall
 * @date 2025-04-18
 */
@Service
public class UserTagServiceImpl implements IUserTagService 
{
    @Autowired
    private UserTagMapper userTagMapper;

    /**
     * 查询用户标签字典
     * 
     * @param tagId 用户标签字典主键
     * @return 用户标签字典
     */
    @Override
    public UserTag selectUserTagByTagId(String tagId)
    {
        return userTagMapper.selectUserTagByTagId(tagId);
    }

    /**
     * 查询用户标签字典列表
     * 
     * @param userTag 用户标签字典
     * @return 用户标签字典
     */
    @Override
    public List<UserTag> selectUserTagList(UserTag userTag)
    {
        return userTagMapper.selectUserTagList(userTag);
    }

    /**
     * 新增用户标签字典
     * 
     * @param userTag 用户标签字典
     * @return 结果
     */
    @Override
    public int insertUserTag(UserTag userTag)
    {
        return userTagMapper.insertUserTag(userTag);
    }

    /**
     * 修改用户标签字典
     * 
     * @param userTag 用户标签字典
     * @return 结果
     */
    @Override
    public int updateUserTag(UserTag userTag)
    {
        return userTagMapper.updateUserTag(userTag);
    }

    /**
     * 批量删除用户标签字典
     * 
     * @param tagIds 需要删除的用户标签字典主键
     * @return 结果
     */
    @Override
    public int deleteUserTagByTagIds(String[] tagIds)
    {
        return userTagMapper.deleteUserTagByTagIds(tagIds);
    }

    /**
     * 删除用户标签字典信息
     * 
     * @param tagId 用户标签字典主键
     * @return 结果
     */
    @Override
    public int deleteUserTagByTagId(String tagId)
    {
        return userTagMapper.deleteUserTagByTagId(tagId);
    }
}
