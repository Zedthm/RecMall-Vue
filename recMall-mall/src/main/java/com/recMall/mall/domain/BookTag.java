package com.recMall.mall.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.recMall.common.annotation.Excel;
import com.recMall.common.core.domain.BaseEntity;

/**
 * 书籍标签字典对象 book_tag
 * 
 * @author recMall
 * @date 2025-04-18
 */
public class BookTag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标签唯一标识（固定8位哈希） */
    private String tagId;

    /** 中文标签名称 */
    @Excel(name = "中文标签名称")
    private String nameZh;

    /** 英文标签名称 */
    @Excel(name = "英文标签名称")
    private String nameEn;

    public void setTagId(String tagId) 
    {
        this.tagId = tagId;
    }

    public String getTagId() 
    {
        return tagId;
    }

    public void setNameZh(String nameZh) 
    {
        this.nameZh = nameZh;
    }

    public String getNameZh() 
    {
        return nameZh;
    }

    public void setNameEn(String nameEn) 
    {
        this.nameEn = nameEn;
    }

    public String getNameEn() 
    {
        return nameEn;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tagId", getTagId())
            .append("nameZh", getNameZh())
            .append("nameEn", getNameEn())
            .toString();
    }
}
