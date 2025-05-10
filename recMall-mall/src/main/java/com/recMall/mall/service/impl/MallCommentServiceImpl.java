package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.MallCommentMapper;
import com.recMall.mall.domain.MallComment;
import com.recMall.mall.service.IMallCommentService;

/**
 * 评论信息Service业务层处理
 * 
 * @author recMall
 * @date 2025-05-04
 */
@Service
public class MallCommentServiceImpl implements IMallCommentService 
{
    @Autowired
    private MallCommentMapper mallCommentMapper;

    /**
     * 查询评论信息
     * 
     * @param id 评论信息主键
     * @return 评论信息
     */
    @Override
    public MallComment selectMallCommentById(Long id)
    {
        return mallCommentMapper.selectMallCommentById(id);
    }

    /**
     * 查询评论信息列表
     * 
     * @param mallComment 评论信息
     * @return 评论信息
     */
    @Override
    public List<MallComment> selectMallCommentList(MallComment mallComment)
    {
        return mallCommentMapper.selectMallCommentList(mallComment);
    }

    /**
     * 新增评论信息
     * 
     * @param mallComment 评论信息
     * @return 结果
     */
    @Override
    public int insertMallComment(MallComment mallComment)
    {
        return mallCommentMapper.insertMallComment(mallComment);
    }

    /**
     * 修改评论信息
     * 
     * @param mallComment 评论信息
     * @return 结果
     */
    @Override
    public int updateMallComment(MallComment mallComment)
    {
        return mallCommentMapper.updateMallComment(mallComment);
    }

    /**
     * 批量删除评论信息
     * 
     * @param ids 需要删除的评论信息主键
     * @return 结果
     */
    @Override
    public int deleteMallCommentByIds(Long[] ids)
    {
        return mallCommentMapper.deleteMallCommentByIds(ids);
    }

    /**
     * 删除评论信息信息
     * 
     * @param id 评论信息主键
     * @return 结果
     */
    @Override
    public int deleteMallCommentById(Long id)
    {
        return mallCommentMapper.deleteMallCommentById(id);
    }
}
