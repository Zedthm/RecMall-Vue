package com.recMall.mall.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.recMall.common.annotation.Excel;
import com.recMall.common.core.domain.BaseEntity;

/**
 * 评论信息对象 mall_comment
 * 
 * @author recMall
 * @date 2025-05-05
 */
public class MallComment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String userId;

    /** 商品ID */
    @Excel(name = "商品ID")
    private String goodsId;

    /** 店铺ID */
    @Excel(name = "店铺ID")
    private String merchantId;

    /** 评论内容 */
    @Excel(name = "评论内容")
    private String content;

    /** 评分 */
    @Excel(name = "评分")
    private Long scores;

    /** 评论时间 */
    @Excel(name = "评论时间")
    private String time;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
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

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    public void setScores(Long scores) 
    {
        this.scores = scores;
    }

    public Long getScores() 
    {
        return scores;
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
            .append("userId", getUserId())
            .append("goodsId", getGoodsId())
            .append("merchantId", getMerchantId())
            .append("content", getContent())
            .append("scores", getScores())
            .append("time", getTime())
            .toString();
    }
}
