package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.MallUserProfilesMapper;
import com.recMall.mall.domain.MallUserProfiles;
import com.recMall.mall.service.IMallUserProfilesService;

/**
 * 用户画像及评分数据Service业务层处理
 * 
 * @author recMall
 * @date 2025-05-03
 */
@Service
public class MallUserProfilesServiceImpl implements IMallUserProfilesService 
{
    @Autowired
    private MallUserProfilesMapper mallUserProfilesMapper;

    /**
     * 查询用户画像及评分数据
     * 
     * @param userId 用户画像及评分数据主键
     * @return 用户画像及评分数据
     */
    @Override
    public MallUserProfiles selectMallUserProfilesByUserId(String userId)
    {
        return mallUserProfilesMapper.selectMallUserProfilesByUserId(userId);
    }

    /**
     * 查询用户画像及评分数据列表
     * 
     * @param mallUserProfiles 用户画像及评分数据
     * @return 用户画像及评分数据
     */
    @Override
    public List<MallUserProfiles> selectMallUserProfilesList(MallUserProfiles mallUserProfiles)
    {
        return mallUserProfilesMapper.selectMallUserProfilesList(mallUserProfiles);
    }

    /**
     * 新增用户画像及评分数据
     * 
     * @param mallUserProfiles 用户画像及评分数据
     * @return 结果
     */
    @Override
    public int insertMallUserProfiles(MallUserProfiles mallUserProfiles)
    {
        return mallUserProfilesMapper.insertMallUserProfiles(mallUserProfiles);
    }

    /**
     * 修改用户画像及评分数据
     * 
     * @param mallUserProfiles 用户画像及评分数据
     * @return 结果
     */
    @Override
    public int updateMallUserProfiles(MallUserProfiles mallUserProfiles)
    {
        return mallUserProfilesMapper.updateMallUserProfiles(mallUserProfiles);
    }

    /**
     * 批量删除用户画像及评分数据
     * 
     * @param userIds 需要删除的用户画像及评分数据主键
     * @return 结果
     */
    @Override
    public int deleteMallUserProfilesByUserIds(String[] userIds)
    {
        return mallUserProfilesMapper.deleteMallUserProfilesByUserIds(userIds);
    }

    /**
     * 删除用户画像及评分数据信息
     * 
     * @param userId 用户画像及评分数据主键
     * @return 结果
     */
    @Override
    public int deleteMallUserProfilesByUserId(String userId)
    {
        return mallUserProfilesMapper.deleteMallUserProfilesByUserId(userId);
    }
}
