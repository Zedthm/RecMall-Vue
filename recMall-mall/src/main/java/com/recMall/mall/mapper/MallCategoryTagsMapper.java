package com.recMall.mall.mapper;

import java.util.List;
import com.recMall.mall.domain.MallCategoryTags;

/**
 * 分类与标签的关联关系Mapper接口
 * 
 * @author recMall
 * @date 2025-05-03
 */
public interface MallCategoryTagsMapper 
{
    /**
     * 查询分类与标签的关联关系
     * 
     * @param categoryId 分类与标签的关联关系主键
     * @return 分类与标签的关联关系
     */
    public MallCategoryTags selectMallCategoryTagsByCategoryId(String categoryId);

    /**
     * 查询分类与标签的关联关系列表
     * 
     * @param mallCategoryTags 分类与标签的关联关系
     * @return 分类与标签的关联关系集合
     */
    public List<MallCategoryTags> selectMallCategoryTagsList(MallCategoryTags mallCategoryTags);

    /**
     * 新增分类与标签的关联关系
     * 
     * @param mallCategoryTags 分类与标签的关联关系
     * @return 结果
     */
    public int insertMallCategoryTags(MallCategoryTags mallCategoryTags);

    /**
     * 修改分类与标签的关联关系
     * 
     * @param mallCategoryTags 分类与标签的关联关系
     * @return 结果
     */
    public int updateMallCategoryTags(MallCategoryTags mallCategoryTags);

    /**
     * 删除分类与标签的关联关系
     * 
     * @param categoryId 分类与标签的关联关系主键
     * @return 结果
     */
    public int deleteMallCategoryTagsByCategoryId(String categoryId);

    /**
     * 批量删除分类与标签的关联关系
     * 
     * @param categoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMallCategoryTagsByCategoryIds(String[] categoryIds);
}
