package com.recMall.mall.service;

import java.util.List;
import com.recMall.mall.domain.MallComment;

/**
 * 评论信息Service接口
 * 
 * @author recMall
 * @date 2025-05-04
 */
public interface IMallCommentService 
{
    /**
     * 查询评论信息
     * 
     * @param id 评论信息主键
     * @return 评论信息
     */
    public MallComment selectMallCommentById(Long id);

    /**
     * 查询评论信息列表
     * 
     * @param mallComment 评论信息
     * @return 评论信息集合
     */
    public List<MallComment> selectMallCommentList(MallComment mallComment);

    /**
     * 新增评论信息
     * 
     * @param mallComment 评论信息
     * @return 结果
     */
    public int insertMallComment(MallComment mallComment);

    /**
     * 修改评论信息
     * 
     * @param mallComment 评论信息
     * @return 结果
     */
    public int updateMallComment(MallComment mallComment);

    /**
     * 批量删除评论信息
     * 
     * @param ids 需要删除的评论信息主键集合
     * @return 结果
     */
    public int deleteMallCommentByIds(Long[] ids);

    /**
     * 删除评论信息信息
     * 
     * @param id 评论信息主键
     * @return 结果
     */
    public int deleteMallCommentById(Long id);
}
