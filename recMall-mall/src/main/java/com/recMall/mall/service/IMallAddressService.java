package com.recMall.mall.service;

import java.util.List;
import com.recMall.mall.domain.MallAddress;

/**
 * 地址信息Service接口
 * 
 * @author recMall
 * @date 2025-05-03
 */
public interface IMallAddressService 
{
    /**
     * 查询地址信息
     * 
     * @param id 地址信息主键
     * @return 地址信息
     */
    public MallAddress selectMallAddressById(Long id);

    /**
     * 查询地址信息列表
     * 
     * @param mallAddress 地址信息
     * @return 地址信息集合
     */
    public List<MallAddress> selectMallAddressList(MallAddress mallAddress);

    /**
     * 新增地址信息
     * 
     * @param mallAddress 地址信息
     * @return 结果
     */
    public int insertMallAddress(MallAddress mallAddress);

    /**
     * 修改地址信息
     * 
     * @param mallAddress 地址信息
     * @return 结果
     */
    public int updateMallAddress(MallAddress mallAddress);

    /**
     * 批量删除地址信息
     * 
     * @param ids 需要删除的地址信息主键集合
     * @return 结果
     */
    public int deleteMallAddressByIds(Long[] ids);

    /**
     * 删除地址信息信息
     * 
     * @param id 地址信息主键
     * @return 结果
     */
    public int deleteMallAddressById(Long id);
}
