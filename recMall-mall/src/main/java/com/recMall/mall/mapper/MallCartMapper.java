package com.recMall.mall.mapper;

import java.util.List;
import com.recMall.mall.domain.MallCart;

/**
 * 购物车Mapper接口
 * 
 * @author recMall
 * @date 2025-05-04
 */
public interface MallCartMapper 
{
    /**
     * 查询购物车
     * 
     * @param id 购物车主键
     * @return 购物车
     */
    public MallCart selectMallCartById(Long id);

    /**
     * 查询购物车列表
     * 
     * @param mallCart 购物车
     * @return 购物车集合
     */
    public List<MallCart> selectMallCartList(MallCart mallCart);

    /**
     * 新增购物车
     * 
     * @param mallCart 购物车
     * @return 结果
     */
    public int insertMallCart(MallCart mallCart);

    /**
     * 修改购物车
     * 
     * @param mallCart 购物车
     * @return 结果
     */
    public int updateMallCart(MallCart mallCart);

    /**
     * 删除购物车
     * 
     * @param id 购物车主键
     * @return 结果
     */
    public int deleteMallCartById(Long id);

    /**
     * 批量删除购物车
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMallCartByIds(Long[] ids);
}
