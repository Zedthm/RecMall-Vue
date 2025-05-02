package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.UserTagRelMapper;
import com.recMall.mall.domain.UserTagRel;
import com.recMall.mall.service.IUserTagRelService;

/**
 * 用户与标签多对多关系Service业务层处理
 * 
 * @author recMall
 * @date 2025-04-18
 */
@Service
public class UserTagRelServiceImpl implements IUserTagRelService 
{
    @Autowired
    private UserTagRelMapper userTagRelMapper;

    /**
     * 查询用户与标签多对多关系
     * 
     * @param userId 用户与标签多对多关系主键
     * @return 用户与标签多对多关系
     */
    @Override
    public UserTagRel selectUserTagRelByUserId(String userId)
    {
        return userTagRelMapper.selectUserTagRelByUserId(userId);
    }

    /**
     * 查询用户与标签多对多关系列表
     * 
     * @param userTagRel 用户与标签多对多关系
     * @return 用户与标签多对多关系
     */
    @Override
    public List<UserTagRel> selectUserTagRelList(UserTagRel userTagRel)
    {
        return userTagRelMapper.selectUserTagRelList(userTagRel);
    }

    /**
     * 新增用户与标签多对多关系
     * 
     * @param userTagRel 用户与标签多对多关系
     * @return 结果
     */
    @Override
    public int insertUserTagRel(UserTagRel userTagRel)
    {
        return userTagRelMapper.insertUserTagRel(userTagRel);
    }

    /**
     * 修改用户与标签多对多关系
     * 
     * @param userTagRel 用户与标签多对多关系
     * @return 结果
     */
    @Override
    public int updateUserTagRel(UserTagRel userTagRel)
    {
        return userTagRelMapper.updateUserTagRel(userTagRel);
    }

    /**
     * 批量删除用户与标签多对多关系
     * 
     * @param userIds 需要删除的用户与标签多对多关系主键
     * @return 结果
     */
    @Override
    public int deleteUserTagRelByUserIds(String[] userIds)
    {
        return userTagRelMapper.deleteUserTagRelByUserIds(userIds);
    }

    /**
     * 删除用户与标签多对多关系信息
     * 
     * @param userId 用户与标签多对多关系主键
     * @return 结果
     */
    @Override
    public int deleteUserTagRelByUserId(String userId)
    {
        return userTagRelMapper.deleteUserTagRelByUserId(userId);
    }
}
