package com.recMall.mall.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.recMall.common.annotation.Excel;
import com.recMall.common.core.domain.BaseEntity;

/**
 * 用户书评数据对象 book_review
 * 
 * @author recMall
 * @date 2025-04-18
 */
public class BookReview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String userId;

    /** 书籍ID */
    @Excel(name = "书籍ID")
    private String bookId;

    /** 书评唯一标识 */
    private String reviewId;

    /** 评分（1-5星） */
    @Excel(name = "评分", readConverterExp = "1=-5星")
    private String rating;

    /** 评论文本内容（最大支持65KB） */
    @Excel(name = "评论文本内容", readConverterExp = "最=大支持65KB")
    private String content;

    /** 首次创建时间 */
    @Excel(name = "首次创建时间")
    private String dateAdded;

    /** 最后更新时间 */
    @Excel(name = "最后更新时间")
    private String dateUpdated;

    /** 标记阅读完成时间 */
    @Excel(name = "标记阅读完成时间")
    private String readAt;

    /** 开始阅读时间 */
    @Excel(name = "开始阅读时间")
    private String startedAt;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private String helpfulVotes;

    /** 评论回复数 */
    @Excel(name = "评论回复数")
    private String commentCount;

    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }

    public void setBookId(String bookId) 
    {
        this.bookId = bookId;
    }

    public String getBookId() 
    {
        return bookId;
    }

    public void setReviewId(String reviewId) 
    {
        this.reviewId = reviewId;
    }

    public String getReviewId() 
    {
        return reviewId;
    }

    public void setRating(String rating) 
    {
        this.rating = rating;
    }

    public String getRating() 
    {
        return rating;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    public void setDateAdded(String dateAdded) 
    {
        this.dateAdded = dateAdded;
    }

    public String getDateAdded() 
    {
        return dateAdded;
    }

    public void setDateUpdated(String dateUpdated) 
    {
        this.dateUpdated = dateUpdated;
    }

    public String getDateUpdated() 
    {
        return dateUpdated;
    }

    public void setReadAt(String readAt) 
    {
        this.readAt = readAt;
    }

    public String getReadAt() 
    {
        return readAt;
    }

    public void setStartedAt(String startedAt) 
    {
        this.startedAt = startedAt;
    }

    public String getStartedAt() 
    {
        return startedAt;
    }

    public void setHelpfulVotes(String helpfulVotes) 
    {
        this.helpfulVotes = helpfulVotes;
    }

    public String getHelpfulVotes() 
    {
        return helpfulVotes;
    }

    public void setCommentCount(String commentCount) 
    {
        this.commentCount = commentCount;
    }

    public String getCommentCount() 
    {
        return commentCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("bookId", getBookId())
            .append("reviewId", getReviewId())
            .append("rating", getRating())
            .append("content", getContent())
            .append("dateAdded", getDateAdded())
            .append("dateUpdated", getDateUpdated())
            .append("readAt", getReadAt())
            .append("startedAt", getStartedAt())
            .append("helpfulVotes", getHelpfulVotes())
            .append("commentCount", getCommentCount())
            .toString();
    }
}
