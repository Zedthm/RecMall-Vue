package com.recMall.mall.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.recMall.common.annotation.Excel;
import com.recMall.common.core.domain.BaseEntity;

/**
 * 存储系统标签信息对象 mall_tags
 * 
 * @author recMall
 * @date 2025-05-03
 */
public class MallTags extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标签ID */
    private String tagId;

    /** 标签名称 */
    @Excel(name = "标签名称")
    private String tagName;

    public void setTagId(String tagId) 
    {
        this.tagId = tagId;
    }

    public String getTagId() 
    {
        return tagId;
    }

    public void setTagName(String tagName) 
    {
        this.tagName = tagName;
    }

    public String getTagName() 
    {
        return tagName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tagId", getTagId())
            .append("tagName", getTagName())
            .toString();
    }
}
