package com.recMall.mall.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.recMall.common.annotation.Excel;
import com.recMall.common.core.domain.BaseEntity;

/**
 * 书籍类别关系对象 book_category_rel
 * 
 * @author recMall
 * @date 2025-04-18
 */
public class BookCategoryRel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 分类目录ID */
    @Excel(name = "分类目录ID")
    private Long categoryId;

    /** 书籍ID */
    @Excel(name = "书籍ID")
    private Long bookId;

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setBookId(Long bookId) 
    {
        this.bookId = bookId;
    }

    public Long getBookId() 
    {
        return bookId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("categoryId", getCategoryId())
            .append("bookId", getBookId())
            .toString();
    }
}
