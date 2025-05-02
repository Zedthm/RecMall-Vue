package com.recMall.mall.mapper;

import java.util.List;
import com.recMall.mall.domain.Collect;

/**
 * 收藏信息Mapper接口
 * 
 * @author recMall
 * @date 2025-04-18
 */
public interface CollectMapper 
{
    /**
     * 查询收藏信息
     * 
     * @param id 收藏信息主键
     * @return 收藏信息
     */
    public Collect selectCollectById(Long id);

    /**
     * 查询收藏信息列表
     * 
     * @param collect 收藏信息
     * @return 收藏信息集合
     */
    public List<Collect> selectCollectList(Collect collect);

    /**
     * 新增收藏信息
     * 
     * @param collect 收藏信息
     * @return 结果
     */
    public int insertCollect(Collect collect);

    /**
     * 修改收藏信息
     * 
     * @param collect 收藏信息
     * @return 结果
     */
    public int updateCollect(Collect collect);

    /**
     * 删除收藏信息
     * 
     * @param id 收藏信息主键
     * @return 结果
     */
    public int deleteCollectById(Long id);

    /**
     * 批量删除收藏信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCollectByIds(Long[] ids);
}
