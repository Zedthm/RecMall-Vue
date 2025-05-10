package com.recMall.mall.service;

import java.util.List;
import com.recMall.mall.domain.MallCollect;

/**
 * 收藏信息Service接口
 * 
 * @author recMall
 * @date 2025-05-04
 */
public interface IMallCollectService 
{
    /**
     * 查询收藏信息
     * 
     * @param id 收藏信息主键
     * @return 收藏信息
     */
    public MallCollect selectMallCollectById(Long id);

    /**
     * 查询收藏信息列表
     * 
     * @param mallCollect 收藏信息
     * @return 收藏信息集合
     */
    public List<MallCollect> selectMallCollectList(MallCollect mallCollect);

    /**
     * 新增收藏信息
     * 
     * @param mallCollect 收藏信息
     * @return 结果
     */
    public int insertMallCollect(MallCollect mallCollect);

    /**
     * 修改收藏信息
     * 
     * @param mallCollect 收藏信息
     * @return 结果
     */
    public int updateMallCollect(MallCollect mallCollect);

    /**
     * 批量删除收藏信息
     * 
     * @param ids 需要删除的收藏信息主键集合
     * @return 结果
     */
    public int deleteMallCollectByIds(Long[] ids);

    /**
     * 删除收藏信息信息
     * 
     * @param id 收藏信息主键
     * @return 结果
     */
    public int deleteMallCollectById(Long id);
}
