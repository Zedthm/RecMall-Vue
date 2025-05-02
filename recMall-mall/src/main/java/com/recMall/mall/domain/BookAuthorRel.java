package com.recMall.mall.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.recMall.common.annotation.Excel;
import com.recMall.common.core.domain.BaseEntity;

/**
 * 书籍与作者多对多关系对象 book_author_rel
 * 
 * @author recMall
 * @date 2025-04-18
 */
public class BookAuthorRel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 书籍ID */
    private String bookId;

    /** 作者ID */
    private String authorId;

    public void setBookId(String bookId) 
    {
        this.bookId = bookId;
    }

    public String getBookId() 
    {
        return bookId;
    }

    public void setAuthorId(String authorId) 
    {
        this.authorId = authorId;
    }

    public String getAuthorId() 
    {
        return authorId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("bookId", getBookId())
            .append("authorId", getAuthorId())
            .toString();
    }
}
