package com.recMall.mall.mapper;

import java.util.List;
import com.recMall.mall.domain.BookOrders;

/**
 * 书籍订单Mapper接口
 * 
 * @author recMall
 * @date 2025-04-18
 */
public interface BookOrdersMapper 
{
    /**
     * 查询书籍订单
     * 
     * @param id 书籍订单主键
     * @return 书籍订单
     */
    public BookOrders selectBookOrdersById(Long id);

    /**
     * 查询书籍订单列表
     * 
     * @param bookOrders 书籍订单
     * @return 书籍订单集合
     */
    public List<BookOrders> selectBookOrdersList(BookOrders bookOrders);

    /**
     * 新增书籍订单
     * 
     * @param bookOrders 书籍订单
     * @return 结果
     */
    public int insertBookOrders(BookOrders bookOrders);

    /**
     * 修改书籍订单
     * 
     * @param bookOrders 书籍订单
     * @return 结果
     */
    public int updateBookOrders(BookOrders bookOrders);

    /**
     * 删除书籍订单
     * 
     * @param id 书籍订单主键
     * @return 结果
     */
    public int deleteBookOrdersById(Long id);

    /**
     * 批量删除书籍订单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBookOrdersByIds(Long[] ids);
}
