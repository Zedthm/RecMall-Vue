package com.recMall.mall.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.recMall.common.annotation.Excel;
import com.recMall.common.core.domain.BaseEntity;

/**
 * 订单信息对象 mall_orders
 * 
 * @author recMall
 * @date 2025-05-05
 */
public class MallOrders extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 订单ID */
    @Excel(name = "订单ID")
    private String orderId;

    /** 商品ID */
    @Excel(name = "商品ID")
    private String goodsId;

    /** 店铺ID */
    @Excel(name = "店铺ID")
    private String merchantId;

    /** 商品数量 */
    @Excel(name = "商品数量")
    private Long num;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String userId;

    /** 订单价格 */
    @Excel(name = "订单价格")
    private BigDecimal price;

    /** 地址ID */
    @Excel(name = "地址ID")
    private Long addressId;

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

    public void setOrderId(String orderId) 
    {
        this.orderId = orderId;
    }

    public String getOrderId() 
    {
        return orderId;
    }

    public void setGoodsId(String goodsId) 
    {
        this.goodsId = goodsId;
    }

    public String getGoodsId() 
    {
        return goodsId;
    }

    public void setMerchantId(String merchantId) 
    {
        this.merchantId = merchantId;
    }

    public String getMerchantId() 
    {
        return merchantId;
    }

    public void setNum(Long num) 
    {
        this.num = num;
    }

    public Long getNum() 
    {
        return num;
    }

    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }

    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }

    public void setAddressId(Long addressId) 
    {
        this.addressId = addressId;
    }

    public Long getAddressId() 
    {
        return addressId;
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
            .append("orderId", getOrderId())
            .append("goodsId", getGoodsId())
            .append("merchantId", getMerchantId())
            .append("num", getNum())
            .append("userId", getUserId())
            .append("price", getPrice())
            .append("addressId", getAddressId())
            .append("status", getStatus())
            .append("time", getTime())
            .toString();
    }
}
