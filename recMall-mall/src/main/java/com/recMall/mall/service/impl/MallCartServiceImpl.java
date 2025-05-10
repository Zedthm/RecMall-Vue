package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.MallCartMapper;
import com.recMall.mall.domain.MallCart;
import com.recMall.mall.service.IMallCartService;

/**
 * 购物车Service业务层处理
 * 
 * @author recMall
 * @date 2025-05-04
 */
@Service
public class MallCartServiceImpl implements IMallCartService 
{
    @Autowired
    private MallCartMapper mallCartMapper;

    /**
     * 查询购物车
     * 
     * @param id 购物车主键
     * @return 购物车
     */
    @Override
    public MallCart selectMallCartById(Long id)
    {
        return mallCartMapper.selectMallCartById(id);
    }

    /**
     * 查询购物车列表
     * 
     * @param mallCart 购物车
     * @return 购物车
     */
    @Override
    public List<MallCart> selectMallCartList(MallCart mallCart)
    {
        return mallCartMapper.selectMallCartList(mallCart);
    }

    /**
     * 新增购物车
     * 
     * @param mallCart 购物车
     * @return 结果
     */
    @Override
    public int insertMallCart(MallCart mallCart)
    {
        return mallCartMapper.insertMallCart(mallCart);
    }

    /**
     * 修改购物车
     * 
     * @param mallCart 购物车
     * @return 结果
     */
    @Override
    public int updateMallCart(MallCart mallCart)
    {
        return mallCartMapper.updateMallCart(mallCart);
    }

    /**
     * 批量删除购物车
     * 
     * @param ids 需要删除的购物车主键
     * @return 结果
     */
    @Override
    public int deleteMallCartByIds(Long[] ids)
    {
        return mallCartMapper.deleteMallCartByIds(ids);
    }

    /**
     * 删除购物车信息
     * 
     * @param id 购物车主键
     * @return 结果
     */
    @Override
    public int deleteMallCartById(Long id)
    {
        return mallCartMapper.deleteMallCartById(id);
    }
}
