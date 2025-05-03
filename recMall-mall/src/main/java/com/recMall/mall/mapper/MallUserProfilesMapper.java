package com.recMall.mall.mapper;

import java.util.List;
import com.recMall.mall.domain.MallUserProfiles;

/**
 * 用户画像及评分数据Mapper接口
 * 
 * @author recMall
 * @date 2025-05-03
 */
public interface MallUserProfilesMapper 
{
    /**
     * 查询用户画像及评分数据
     * 
     * @param userId 用户画像及评分数据主键
     * @return 用户画像及评分数据
     */
    public MallUserProfiles selectMallUserProfilesByUserId(String userId);

    /**
     * 查询用户画像及评分数据列表
     * 
     * @param mallUserProfiles 用户画像及评分数据
     * @return 用户画像及评分数据集合
     */
    public List<MallUserProfiles> selectMallUserProfilesList(MallUserProfiles mallUserProfiles);

    /**
     * 新增用户画像及评分数据
     * 
     * @param mallUserProfiles 用户画像及评分数据
     * @return 结果
     */
    public int insertMallUserProfiles(MallUserProfiles mallUserProfiles);

    /**
     * 修改用户画像及评分数据
     * 
     * @param mallUserProfiles 用户画像及评分数据
     * @return 结果
     */
    public int updateMallUserProfiles(MallUserProfiles mallUserProfiles);

    /**
     * 删除用户画像及评分数据
     * 
     * @param userId 用户画像及评分数据主键
     * @return 结果
     */
    public int deleteMallUserProfilesByUserId(String userId);

    /**
     * 批量删除用户画像及评分数据
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMallUserProfilesByUserIds(String[] userIds);
}
