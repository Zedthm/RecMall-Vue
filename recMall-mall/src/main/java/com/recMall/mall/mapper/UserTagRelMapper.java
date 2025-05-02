package com.recMall.mall.mapper;

import java.util.List;
import com.recMall.mall.domain.UserTagRel;

/**
 * 用户与标签多对多关系Mapper接口
 * 
 * @author recMall
 * @date 2025-04-18
 */
public interface UserTagRelMapper 
{
    /**
     * 查询用户与标签多对多关系
     * 
     * @param userId 用户与标签多对多关系主键
     * @return 用户与标签多对多关系
     */
    public UserTagRel selectUserTagRelByUserId(String userId);

    /**
     * 查询用户与标签多对多关系列表
     * 
     * @param userTagRel 用户与标签多对多关系
     * @return 用户与标签多对多关系集合
     */
    public List<UserTagRel> selectUserTagRelList(UserTagRel userTagRel);

    /**
     * 新增用户与标签多对多关系
     * 
     * @param userTagRel 用户与标签多对多关系
     * @return 结果
     */
    public int insertUserTagRel(UserTagRel userTagRel);

    /**
     * 修改用户与标签多对多关系
     * 
     * @param userTagRel 用户与标签多对多关系
     * @return 结果
     */
    public int updateUserTagRel(UserTagRel userTagRel);

    /**
     * 删除用户与标签多对多关系
     * 
     * @param userId 用户与标签多对多关系主键
     * @return 结果
     */
    public int deleteUserTagRelByUserId(String userId);

    /**
     * 批量删除用户与标签多对多关系
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserTagRelByUserIds(String[] userIds);
}
