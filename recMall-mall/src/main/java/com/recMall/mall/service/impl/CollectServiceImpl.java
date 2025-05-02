package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.CollectMapper;
import com.recMall.mall.domain.Collect;
import com.recMall.mall.service.ICollectService;

/**
 * 收藏信息Service业务层处理
 * 
 * @author recMall
 * @date 2025-04-18
 */
@Service
public class CollectServiceImpl implements ICollectService 
{
    @Autowired
    private CollectMapper collectMapper;

    /**
     * 查询收藏信息
     * 
     * @param id 收藏信息主键
     * @return 收藏信息
     */
    @Override
    public Collect selectCollectById(Long id)
    {
        return collectMapper.selectCollectById(id);
    }

    /**
     * 查询收藏信息列表
     * 
     * @param collect 收藏信息
     * @return 收藏信息
     */
    @Override
    public List<Collect> selectCollectList(Collect collect)
    {
        return collectMapper.selectCollectList(collect);
    }

    /**
     * 新增收藏信息
     * 
     * @param collect 收藏信息
     * @return 结果
     */
    @Override
    public int insertCollect(Collect collect)
    {
        return collectMapper.insertCollect(collect);
    }

    /**
     * 修改收藏信息
     * 
     * @param collect 收藏信息
     * @return 结果
     */
    @Override
    public int updateCollect(Collect collect)
    {
        return collectMapper.updateCollect(collect);
    }

    /**
     * 批量删除收藏信息
     * 
     * @param ids 需要删除的收藏信息主键
     * @return 结果
     */
    @Override
    public int deleteCollectByIds(Long[] ids)
    {
        return collectMapper.deleteCollectByIds(ids);
    }

    /**
     * 删除收藏信息信息
     * 
     * @param id 收藏信息主键
     * @return 结果
     */
    @Override
    public int deleteCollectById(Long id)
    {
        return collectMapper.deleteCollectById(id);
    }
}
