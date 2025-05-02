package com.recMall.mall.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.recMall.common.annotation.Excel;
import com.recMall.common.core.domain.BaseEntity;

/**
 * 书籍作者信息对象 author
 * 
 * @author recMall
 * @date 2025-04-18
 */
public class Author extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 作者唯一标识（固定8位哈希） */
    private String authorId;

    /** 作者姓名（支持国际化姓名） */
    @Excel(name = "作者姓名", readConverterExp = "支=持国际化姓名")
    private String name;

    public void setAuthorId(String authorId) 
    {
        this.authorId = authorId;
    }

    public String getAuthorId() 
    {
        return authorId;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("authorId", getAuthorId())
            .append("name", getName())
            .toString();
    }
}
