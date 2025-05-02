package com.recMall.mall.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.recMall.common.annotation.Excel;
import com.recMall.common.core.domain.BaseEntity;

/**
 * 用户与标签多对多关系对象 user_tag_rel
 * 
 * @author recMall
 * @date 2025-04-18
 */
public class UserTagRel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private String userId;

    /** 标签ID */
    private String tagId;

    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
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
            .append("userId", getUserId())
            .append("tagId", getTagId())
            .toString();
    }
}
