package com.recMall.mall.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.recMall.common.annotation.Excel;
import com.recMall.common.core.domain.BaseEntity;

/**
 * 用户画像及评分数据对象 mall_user_profiles
 * 
 * @author recMall
 * @date 2025-05-03
 */
public class MallUserProfiles extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private String userId;

    /** 平均评分 */
    @Excel(name = "平均评分")
    private BigDecimal userAvgRating;

    /** 评分标准差 */
    @Excel(name = "评分标准差")
    private BigDecimal userRatingStd;

    /** 有效评分次数 */
    @Excel(name = "有效评分次数")
    private String userRatingCount;

    /** 一级兴趣标签 */
    @Excel(name = "一级兴趣标签")
    private String userTag1;

    /** 二级兴趣标签 */
    @Excel(name = "二级兴趣标签")
    private String userTag2;

    /** 三级兴趣标签 */
    @Excel(name = "三级兴趣标签")
    private String userTag3;

    /** 四级兴趣标签 */
    @Excel(name = "四级兴趣标签")
    private String userTag4;

    /** 五级兴趣标签 */
    @Excel(name = "五级兴趣标签")
    private String userTag5;

    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }

    public void setUserAvgRating(BigDecimal userAvgRating) 
    {
        this.userAvgRating = userAvgRating;
    }

    public BigDecimal getUserAvgRating() 
    {
        return userAvgRating;
    }

    public void setUserRatingStd(BigDecimal userRatingStd) 
    {
        this.userRatingStd = userRatingStd;
    }

    public BigDecimal getUserRatingStd() 
    {
        return userRatingStd;
    }

    public void setUserRatingCount(String userRatingCount) 
    {
        this.userRatingCount = userRatingCount;
    }

    public String getUserRatingCount() 
    {
        return userRatingCount;
    }

    public void setUserTag1(String userTag1) 
    {
        this.userTag1 = userTag1;
    }

    public String getUserTag1() 
    {
        return userTag1;
    }

    public void setUserTag2(String userTag2) 
    {
        this.userTag2 = userTag2;
    }

    public String getUserTag2() 
    {
        return userTag2;
    }

    public void setUserTag3(String userTag3) 
    {
        this.userTag3 = userTag3;
    }

    public String getUserTag3() 
    {
        return userTag3;
    }

    public void setUserTag4(String userTag4) 
    {
        this.userTag4 = userTag4;
    }

    public String getUserTag4() 
    {
        return userTag4;
    }

    public void setUserTag5(String userTag5) 
    {
        this.userTag5 = userTag5;
    }

    public String getUserTag5() 
    {
        return userTag5;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("userAvgRating", getUserAvgRating())
            .append("userRatingStd", getUserRatingStd())
            .append("userRatingCount", getUserRatingCount())
            .append("userTag1", getUserTag1())
            .append("userTag2", getUserTag2())
            .append("userTag3", getUserTag3())
            .append("userTag4", getUserTag4())
            .append("userTag5", getUserTag5())
            .toString();
    }
}
