package com.recMall.mall.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.recMall.common.annotation.Excel;
import com.recMall.common.core.domain.BaseEntity;

/**
 * 图书核心信息对象 book
 * 
 * @author recMall
 * @date 2025-04-18
 */
public class Book extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 书籍唯一标识（自增主键） */
    private String bookId;

    /** 书籍全称（含副标题） */
    @Excel(name = "书籍全称", readConverterExp = "含=副标题")
    private String title;

    /** ISBN-10（无分隔符，示例：0123456789） */
    @Excel(name = "ISBN-10", readConverterExp = "无=分隔符，示例：0123456789")
    private String isbn10;

    /** ISBN-13（无分隔符，示例：9780123456789） */
    @Excel(name = "ISBN-13", readConverterExp = "无=分隔符，示例：9780123456789")
    private String isbn13;

    /** 总页数（范围：0-65535） */
    @Excel(name = "总页数", readConverterExp = "范=围：0-65535")
    private String pageCount;

    /** 出版社全称 */
    @Excel(name = "出版社全称")
    private String publisher;

    /** 出版日期（时间戳） */
    @Excel(name = "出版日期", readConverterExp = "时=间戳")
    private String publishDate;

    /** 定价 */
    @Excel(name = "定价")
    private BigDecimal price;

    /** 封面图URL（最大长度512字符） */
    @Excel(name = "封面图URL", readConverterExp = "最=大长度512字符")
    private String coverImageUrl;

    /** 书籍详细描述（支持富文本） */
    @Excel(name = "书籍详细描述", readConverterExp = "支=持富文本")
    private String description;

    /** 上架状态（1:在售, 0:下架） */
    @Excel(name = "上架状态", readConverterExp = "1=:在售,,0=:下架")
    private Integer isAvailable;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 最后更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    /** 数据版本号（乐观锁） */
    @Excel(name = "数据版本号", readConverterExp = "乐=观锁")
    private String version;

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

    public void setIsbn10(String isbn10) 
    {
        this.isbn10 = isbn10;
    }

    public String getIsbn10() 
    {
        return isbn10;
    }

    public void setIsbn13(String isbn13) 
    {
        this.isbn13 = isbn13;
    }

    public String getIsbn13() 
    {
        return isbn13;
    }

    public void setPageCount(String pageCount) 
    {
        this.pageCount = pageCount;
    }

    public String getPageCount() 
    {
        return pageCount;
    }

    public void setPublisher(String publisher) 
    {
        this.publisher = publisher;
    }

    public String getPublisher() 
    {
        return publisher;
    }

    public void setPublishDate(String publishDate) 
    {
        this.publishDate = publishDate;
    }

    public String getPublishDate() 
    {
        return publishDate;
    }

    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }

    public void setCoverImageUrl(String coverImageUrl) 
    {
        this.coverImageUrl = coverImageUrl;
    }

    public String getCoverImageUrl() 
    {
        return coverImageUrl;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setIsAvailable(Integer isAvailable) 
    {
        this.isAvailable = isAvailable;
    }

    public Integer getIsAvailable() 
    {
        return isAvailable;
    }

    public void setCreatedTime(Date createdTime) 
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() 
    {
        return createdTime;
    }

    public void setUpdatedTime(Date updatedTime) 
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime() 
    {
        return updatedTime;
    }

    public void setVersion(String version) 
    {
        this.version = version;
    }

    public String getVersion() 
    {
        return version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("bookId", getBookId())
            .append("title", getTitle())
            .append("isbn10", getIsbn10())
            .append("isbn13", getIsbn13())
            .append("pageCount", getPageCount())
            .append("publisher", getPublisher())
            .append("publishDate", getPublishDate())
            .append("price", getPrice())
            .append("coverImageUrl", getCoverImageUrl())
            .append("description", getDescription())
            .append("isAvailable", getIsAvailable())
            .append("createdTime", getCreatedTime())
            .append("updatedTime", getUpdatedTime())
            .append("version", getVersion())
            .toString();
    }
}
