package com.recMall.mall.service;

import java.util.List;
import com.recMall.mall.domain.MallOrders;

/**
 * 订单信息Service接口
 * 
 * @author recMall
 * @date 2025-05-04
 */
public interface IMallOrdersService 
{
    /**
     * 查询订单信息
     * 
     * @param id 订单信息主键
     * @return 订单信息
     */
    public MallOrders selectMallOrdersById(Long id);

    /**
     * 查询订单信息列表
     * 
     * @param mallOrders 订单信息
     * @return 订单信息集合
     */
    public List<MallOrders> selectMallOrdersList(MallOrders mallOrders);

    /**
     * 新增订单信息
     * 
     * @param mallOrders 订单信息
     * @return 结果
     */
    public int insertMallOrders(MallOrders mallOrders);

    /**
     * 修改订单信息
     * 
     * @param mallOrders 订单信息
     * @return 结果
     */
    public int updateMallOrders(MallOrders mallOrders);

    /**
     * 批量删除订单信息
     * 
     * @param ids 需要删除的订单信息主键集合
     * @return 结果
     */
    public int deleteMallOrdersByIds(Long[] ids);

    /**
     * 删除订单信息信息
     * 
     * @param id 订单信息主键
     * @return 结果
     */
    public int deleteMallOrdersById(Long id);
}
