package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.MallCollectMapper;
import com.recMall.mall.domain.MallCollect;
import com.recMall.mall.service.IMallCollectService;

/**
 * 收藏信息Service业务层处理
 * 
 * @author recMall
 * @date 2025-05-04
 */
@Service
public class MallCollectServiceImpl implements IMallCollectService 
{
    @Autowired
    private MallCollectMapper mallCollectMapper;

    /**
     * 查询收藏信息
     * 
     * @param id 收藏信息主键
     * @return 收藏信息
     */
    @Override
    public MallCollect selectMallCollectById(Long id)
    {
        return mallCollectMapper.selectMallCollectById(id);
    }

    /**
     * 查询收藏信息列表
     * 
     * @param mallCollect 收藏信息
     * @return 收藏信息
     */
    @Override
    public List<MallCollect> selectMallCollectList(MallCollect mallCollect)
    {
        return mallCollectMapper.selectMallCollectList(mallCollect);
    }

    /**
     * 新增收藏信息
     * 
     * @param mallCollect 收藏信息
     * @return 结果
     */
    @Override
    public int insertMallCollect(MallCollect mallCollect)
    {
        return mallCollectMapper.insertMallCollect(mallCollect);
    }

    /**
     * 修改收藏信息
     * 
     * @param mallCollect 收藏信息
     * @return 结果
     */
    @Override
    public int updateMallCollect(MallCollect mallCollect)
    {
        return mallCollectMapper.updateMallCollect(mallCollect);
    }

    /**
     * 批量删除收藏信息
     * 
     * @param ids 需要删除的收藏信息主键
     * @return 结果
     */
    @Override
    public int deleteMallCollectByIds(Long[] ids)
    {
        return mallCollectMapper.deleteMallCollectByIds(ids);
    }

    /**
     * 删除收藏信息信息
     * 
     * @param id 收藏信息主键
     * @return 结果
     */
    @Override
    public int deleteMallCollectById(Long id)
    {
        return mallCollectMapper.deleteMallCollectById(id);
    }
}
