package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.MallCategoryTagsMapper;
import com.recMall.mall.domain.MallCategoryTags;
import com.recMall.mall.service.IMallCategoryTagsService;

/**
 * 分类与标签的关联关系Service业务层处理
 * 
 * @author recMall
 * @date 2025-05-03
 */
@Service
public class MallCategoryTagsServiceImpl implements IMallCategoryTagsService 
{
    @Autowired
    private MallCategoryTagsMapper mallCategoryTagsMapper;

    /**
     * 查询分类与标签的关联关系
     * 
     * @param categoryId 分类与标签的关联关系主键
     * @return 分类与标签的关联关系
     */
    @Override
    public MallCategoryTags selectMallCategoryTagsByCategoryId(String categoryId)
    {
        return mallCategoryTagsMapper.selectMallCategoryTagsByCategoryId(categoryId);
    }

    /**
     * 查询分类与标签的关联关系列表
     * 
     * @param mallCategoryTags 分类与标签的关联关系
     * @return 分类与标签的关联关系
     */
    @Override
    public List<MallCategoryTags> selectMallCategoryTagsList(MallCategoryTags mallCategoryTags)
    {
        return mallCategoryTagsMapper.selectMallCategoryTagsList(mallCategoryTags);
    }

    /**
     * 新增分类与标签的关联关系
     * 
     * @param mallCategoryTags 分类与标签的关联关系
     * @return 结果
     */
    @Override
    public int insertMallCategoryTags(MallCategoryTags mallCategoryTags)
    {
        return mallCategoryTagsMapper.insertMallCategoryTags(mallCategoryTags);
    }

    /**
     * 修改分类与标签的关联关系
     * 
     * @param mallCategoryTags 分类与标签的关联关系
     * @return 结果
     */
    @Override
    public int updateMallCategoryTags(MallCategoryTags mallCategoryTags)
    {
        return mallCategoryTagsMapper.updateMallCategoryTags(mallCategoryTags);
    }

    /**
     * 批量删除分类与标签的关联关系
     * 
     * @param categoryIds 需要删除的分类与标签的关联关系主键
     * @return 结果
     */
    @Override
    public int deleteMallCategoryTagsByCategoryIds(String[] categoryIds)
    {
        return mallCategoryTagsMapper.deleteMallCategoryTagsByCategoryIds(categoryIds);
    }

    /**
     * 删除分类与标签的关联关系信息
     * 
     * @param categoryId 分类与标签的关联关系主键
     * @return 结果
     */
    @Override
    public int deleteMallCategoryTagsByCategoryId(String categoryId)
    {
        return mallCategoryTagsMapper.deleteMallCategoryTagsByCategoryId(categoryId);
    }
}
