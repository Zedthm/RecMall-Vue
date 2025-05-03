package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.MallAddressMapper;
import com.recMall.mall.domain.MallAddress;
import com.recMall.mall.service.IMallAddressService;

/**
 * 地址信息Service业务层处理
 * 
 * @author recMall
 * @date 2025-05-03
 */
@Service
public class MallAddressServiceImpl implements IMallAddressService 
{
    @Autowired
    private MallAddressMapper mallAddressMapper;

    /**
     * 查询地址信息
     * 
     * @param id 地址信息主键
     * @return 地址信息
     */
    @Override
    public MallAddress selectMallAddressById(Long id)
    {
        return mallAddressMapper.selectMallAddressById(id);
    }

    /**
     * 查询地址信息列表
     * 
     * @param mallAddress 地址信息
     * @return 地址信息
     */
    @Override
    public List<MallAddress> selectMallAddressList(MallAddress mallAddress)
    {
        return mallAddressMapper.selectMallAddressList(mallAddress);
    }

    /**
     * 新增地址信息
     * 
     * @param mallAddress 地址信息
     * @return 结果
     */
    @Override
    public int insertMallAddress(MallAddress mallAddress)
    {
        return mallAddressMapper.insertMallAddress(mallAddress);
    }

    /**
     * 修改地址信息
     * 
     * @param mallAddress 地址信息
     * @return 结果
     */
    @Override
    public int updateMallAddress(MallAddress mallAddress)
    {
        return mallAddressMapper.updateMallAddress(mallAddress);
    }

    /**
     * 批量删除地址信息
     * 
     * @param ids 需要删除的地址信息主键
     * @return 结果
     */
    @Override
    public int deleteMallAddressByIds(Long[] ids)
    {
        return mallAddressMapper.deleteMallAddressByIds(ids);
    }

    /**
     * 删除地址信息信息
     * 
     * @param id 地址信息主键
     * @return 结果
     */
    @Override
    public int deleteMallAddressById(Long id)
    {
        return mallAddressMapper.deleteMallAddressById(id);
    }
}
