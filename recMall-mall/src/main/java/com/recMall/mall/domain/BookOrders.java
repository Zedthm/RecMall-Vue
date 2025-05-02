package com.recMall.mall.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.recMall.common.annotation.Excel;
import com.recMall.common.core.domain.BaseEntity;

/**
 * 书籍订单对象 book_orders
 * 
 * @author recMall
 * @date 2025-04-18
 */
public class BookOrders extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNumber;

    /** 商品ID */
    @Excel(name = "商品ID")
    private String bookId;

    /** 购买数量 */
    @Excel(name = "购买数量")
    private Long num;

    /** 下单者 */
    @Excel(name = "下单者")
    private Long userId;

    /** 订单状态 */
    @Excel(name = "订单状态")
    private String status;

    /** 下单时间 */
    @Excel(name = "下单时间")
    private String time;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setOrderNumber(String orderNumber) 
    {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() 
    {
        return orderNumber;
    }

    public void setBookId(String bookId) 
    {
        this.bookId = bookId;
    }

    public String getBookId() 
    {
        return bookId;
    }

    public void setNum(Long num) 
    {
        this.num = num;
    }

    public Long getNum() 
    {
        return num;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setTime(String time) 
    {
        this.time = time;
    }

    public String getTime() 
    {
        return time;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderNumber", getOrderNumber())
            .append("bookId", getBookId())
            .append("num", getNum())
            .append("userId", getUserId())
            .append("status", getStatus())
            .append("time", getTime())
            .toString();
    }
}
