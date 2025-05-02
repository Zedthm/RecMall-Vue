package com.recMall.mall.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.recMall.common.annotation.Excel;
import com.recMall.common.core.domain.BaseEntity;

/**
 * 书籍评分统计对象 book_rating_stats
 * 
 * @author recMall
 * @date 2025-04-18
 */
public class BookRatingStats extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 书籍ID */
    private String bookId;

    /** 平均分（范围：0.00-5.00） */
    @Excel(name = "平均分", readConverterExp = "范=围：0.00-5.00")
    private BigDecimal avgRating;

    /** 总体标准差 */
    @Excel(name = "总体标准差")
    private BigDecimal stdPopulation;

    /** 样本标准差 */
    @Excel(name = "样本标准差")
    private BigDecimal stdDeviation;

    /** 评分总数 */
    @Excel(name = "评分总数")
    private String ratingCount;

    public void setBookId(String bookId) 
    {
        this.bookId = bookId;
    }

    public String getBookId() 
    {
        return bookId;
    }

    public void setAvgRating(BigDecimal avgRating) 
    {
        this.avgRating = avgRating;
    }

    public BigDecimal getAvgRating() 
    {
        return avgRating;
    }

    public void setStdPopulation(BigDecimal stdPopulation) 
    {
        this.stdPopulation = stdPopulation;
    }

    public BigDecimal getStdPopulation() 
    {
        return stdPopulation;
    }

    public void setStdDeviation(BigDecimal stdDeviation) 
    {
        this.stdDeviation = stdDeviation;
    }

    public BigDecimal getStdDeviation() 
    {
        return stdDeviation;
    }

    public void setRatingCount(String ratingCount) 
    {
        this.ratingCount = ratingCount;
    }

    public String getRatingCount() 
    {
        return ratingCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("bookId", getBookId())
            .append("avgRating", getAvgRating())
            .append("stdPopulation", getStdPopulation())
            .append("stdDeviation", getStdDeviation())
            .append("ratingCount", getRatingCount())
            .toString();
    }
}
