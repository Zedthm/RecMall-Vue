package com.recMall.mall.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.recMall.common.annotation.Excel;
import com.recMall.common.core.domain.BaseEntity;

/**
 * 书籍与标签多对多关系对象 book_tag_rel
 * 
 * @author recMall
 * @date 2025-04-18
 */
public class BookTagRel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 书籍ID */
    private String bookId;

    /** 标签ID */
    private String tagId;

    public void setBookId(String bookId) 
    {
        this.bookId = bookId;
    }

    public String getBookId() 
    {
        return bookId;
    }

    public void setTagId(String tagId) 
    {
        this.tagId = tagId;
    }

    public String getTagId() 
    {
        return tagId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("bookId", getBookId())
            .append("tagId", getTagId())
            .toString();
    }
}
