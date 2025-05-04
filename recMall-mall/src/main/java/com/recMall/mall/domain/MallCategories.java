package com.recMall.mall.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.recMall.common.annotation.Excel;
import com.recMall.common.core.domain.BaseEntity;

/**
 * 书籍分类目录对象 mall_categories
 * 
 * @author recMall
 * @date 2025-05-04
 */
public class MallCategories extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 分类唯一标识 */
    private String categoryId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String categoryName;

    /** 分类图片URL */
    @Excel(name = "分类图片URL")
    private String img;

    public void setCategoryId(String categoryId) 
    {
        this.categoryId = categoryId;
    }

    public String getCategoryId() 
    {
        return categoryId;
    }

    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() 
    {
        return categoryName;
    }

    public void setImg(String img) 
    {
        this.img = img;
    }

    public String getImg() 
    {
        return img;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("categoryId", getCategoryId())
            .append("categoryName", getCategoryName())
            .append("img", getImg())
            .toString();
    }
}
