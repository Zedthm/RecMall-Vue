package com.recMall.mall.domain;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.recMall.common.annotation.Excel;
import com.recMall.common.core.domain.BaseEntity;

/**
 * 商品信息-书籍核心数据对象 mall_books
 * 
 * @author recMall
 * @date 2025-05-03
 */
public class MallBooks extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 书籍唯一ID */
    private String bookId;

    /** 书籍标题 */
    @Excel(name = "书籍标题")
    private String title;

    /** 作者列表 */
    @Excel(name = "作者列表")
    private String authors;

    /** 出版年份 */
    @Excel(name = "出版年份")
    private String pubYear;

    /** 平均评分 */
    @Excel(name = "平均评分")
    private BigDecimal bookAvgRating;

    /** 评分标准差 */
    @Excel(name = "评分标准差")
    private BigDecimal bookRatingStd;

    /** 评分人数 */
    @Excel(name = "评分人数")
    private Long bookRatingCount;

    /** 页数 */
    @Excel(name = "页数")
    private String pageCount;

    /** 定价 */
    @Excel(name = "定价")
    private BigDecimal price;

    /** 封面图片URL */
    @Excel(name = "封面图片URL")
    private String img;

    /** 详细描述 */
    @Excel(name = "详细描述")
    private String description;

    /** 销售单位 */
    @Excel(name = "销售单位")
    private String units;

    /** 当前库存量 */
    @Excel(name = "当前库存量")
    private Long inventory;

    /** 归属商家名称 */
    @Excel(name = "归属商家名称")
    private String merchant;

    /** 书籍与标签的关联关系信息 */
    private List<MallBookTags> mallBookTagsList;

    public void setBookId(String bookId) 
    {
        this.bookId = bookId;
    }

    public String getBookId() 
    {
        return bookId;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setAuthors(String authors) 
    {
        this.authors = authors;
    }

    public String getAuthors() 
    {
        return authors;
    }

    public void setPubYear(String pubYear) 
    {
        this.pubYear = pubYear;
    }

    public String getPubYear() 
    {
        return pubYear;
    }

    public void setBookAvgRating(BigDecimal bookAvgRating) 
    {
        this.bookAvgRating = bookAvgRating;
    }

    public BigDecimal getBookAvgRating() 
    {
        return bookAvgRating;
    }

    public void setBookRatingStd(BigDecimal bookRatingStd) 
    {
        this.bookRatingStd = bookRatingStd;
    }

    public BigDecimal getBookRatingStd() 
    {
        return bookRatingStd;
    }

    public void setBookRatingCount(Long bookRatingCount) 
    {
        this.bookRatingCount = bookRatingCount;
    }

    public Long getBookRatingCount() 
    {
        return bookRatingCount;
    }

    public void setPageCount(String pageCount) 
    {
        this.pageCount = pageCount;
    }

    public String getPageCount() 
    {
        return pageCount;
    }

    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }

    public void setImg(String img) 
    {
        this.img = img;
    }

    public String getImg() 
    {
        return img;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setUnits(String units) 
    {
        this.units = units;
    }

    public String getUnits() 
    {
        return units;
    }

    public void setInventory(Long inventory) 
    {
        this.inventory = inventory;
    }

    public Long getInventory() 
    {
        return inventory;
    }

    public void setMerchant(String merchant) 
    {
        this.merchant = merchant;
    }

    public String getMerchant() 
    {
        return merchant;
    }

    public List<MallBookTags> getMallBookTagsList()
    {
        return mallBookTagsList;
    }

    public void setMallBookTagsList(List<MallBookTags> mallBookTagsList)
    {
        this.mallBookTagsList = mallBookTagsList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("bookId", getBookId())
            .append("title", getTitle())
            .append("authors", getAuthors())
            .append("pubYear", getPubYear())
            .append("bookAvgRating", getBookAvgRating())
            .append("bookRatingStd", getBookRatingStd())
            .append("bookRatingCount", getBookRatingCount())
            .append("pageCount", getPageCount())
            .append("price", getPrice())
            .append("img", getImg())
            .append("description", getDescription())
            .append("units", getUnits())
            .append("inventory", getInventory())
            .append("merchant", getMerchant())
            .append("mallBookTagsList", getMallBookTagsList())
            .toString();
    }
}
