package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.BookOrdersMapper;
import com.recMall.mall.domain.BookOrders;
import com.recMall.mall.service.IBookOrdersService;

/**
 * 书籍订单Service业务层处理
 * 
 * @author recMall
 * @date 2025-04-18
 */
@Service
public class BookOrdersServiceImpl implements IBookOrdersService 
{
    @Autowired
    private BookOrdersMapper bookOrdersMapper;

    /**
     * 查询书籍订单
     * 
     * @param id 书籍订单主键
     * @return 书籍订单
     */
    @Override
    public BookOrders selectBookOrdersById(Long id)
    {
        return bookOrdersMapper.selectBookOrdersById(id);
    }

    /**
     * 查询书籍订单列表
     * 
     * @param bookOrders 书籍订单
     * @return 书籍订单
     */
    @Override
    public List<BookOrders> selectBookOrdersList(BookOrders bookOrders)
    {
        return bookOrdersMapper.selectBookOrdersList(bookOrders);
    }

    /**
     * 新增书籍订单
     * 
     * @param bookOrders 书籍订单
     * @return 结果
     */
    @Override
    public int insertBookOrders(BookOrders bookOrders)
    {
        return bookOrdersMapper.insertBookOrders(bookOrders);
    }

    /**
     * 修改书籍订单
     * 
     * @param bookOrders 书籍订单
     * @return 结果
     */
    @Override
    public int updateBookOrders(BookOrders bookOrders)
    {
        return bookOrdersMapper.updateBookOrders(bookOrders);
    }

    /**
     * 批量删除书籍订单
     * 
     * @param ids 需要删除的书籍订单主键
     * @return 结果
     */
    @Override
    public int deleteBookOrdersByIds(Long[] ids)
    {
        return bookOrdersMapper.deleteBookOrdersByIds(ids);
    }

    /**
     * 删除书籍订单信息
     * 
     * @param id 书籍订单主键
     * @return 结果
     */
    @Override
    public int deleteBookOrdersById(Long id)
    {
        return bookOrdersMapper.deleteBookOrdersById(id);
    }
}
