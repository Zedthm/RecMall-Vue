package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.MallOrdersMapper;
import com.recMall.mall.domain.MallOrders;
import com.recMall.mall.service.IMallOrdersService;

/**
 * 订单信息Service业务层处理
 * 
 * @author recMall
 * @date 2025-05-04
 */
@Service
public class MallOrdersServiceImpl implements IMallOrdersService 
{
    @Autowired
    private MallOrdersMapper mallOrdersMapper;

    /**
     * 查询订单信息
     * 
     * @param id 订单信息主键
     * @return 订单信息
     */
    @Override
    public MallOrders selectMallOrdersById(Long id)
    {
        return mallOrdersMapper.selectMallOrdersById(id);
    }

    /**
     * 查询订单信息列表
     * 
     * @param mallOrders 订单信息
     * @return 订单信息
     */
    @Override
    public List<MallOrders> selectMallOrdersList(MallOrders mallOrders)
    {
        return mallOrdersMapper.selectMallOrdersList(mallOrders);
    }

    /**
     * 新增订单信息
     * 
     * @param mallOrders 订单信息
     * @return 结果
     */
    @Override
    public int insertMallOrders(MallOrders mallOrders)
    {
        return mallOrdersMapper.insertMallOrders(mallOrders);
    }

    /**
     * 修改订单信息
     * 
     * @param mallOrders 订单信息
     * @return 结果
     */
    @Override
    public int updateMallOrders(MallOrders mallOrders)
    {
        return mallOrdersMapper.updateMallOrders(mallOrders);
    }

    /**
     * 批量删除订单信息
     * 
     * @param ids 需要删除的订单信息主键
     * @return 结果
     */
    @Override
    public int deleteMallOrdersByIds(Long[] ids)
    {
        return mallOrdersMapper.deleteMallOrdersByIds(ids);
    }

    /**
     * 删除订单信息信息
     * 
     * @param id 订单信息主键
     * @return 结果
     */
    @Override
    public int deleteMallOrdersById(Long id)
    {
        return mallOrdersMapper.deleteMallOrdersById(id);
    }
}
