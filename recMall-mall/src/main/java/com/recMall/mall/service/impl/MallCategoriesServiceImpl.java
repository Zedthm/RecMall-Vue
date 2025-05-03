package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.MallCategoriesMapper;
import com.recMall.mall.domain.MallCategories;
import com.recMall.mall.service.IMallCategoriesService;

/**
 * 书籍分类目录Service业务层处理
 * 
 * @author recMall
 * @date 2025-05-03
 */
@Service
public class MallCategoriesServiceImpl implements IMallCategoriesService 
{
    @Autowired
    private MallCategoriesMapper mallCategoriesMapper;

    /**
     * 查询书籍分类目录
     * 
     * @param categoryId 书籍分类目录主键
     * @return 书籍分类目录
     */
    @Override
    public MallCategories selectMallCategoriesByCategoryId(String categoryId)
    {
        return mallCategoriesMapper.selectMallCategoriesByCategoryId(categoryId);
    }

    /**
     * 查询书籍分类目录列表
     * 
     * @param mallCategories 书籍分类目录
     * @return 书籍分类目录
     */
    @Override
    public List<MallCategories> selectMallCategoriesList(MallCategories mallCategories)
    {
        return mallCategoriesMapper.selectMallCategoriesList(mallCategories);
    }

    /**
     * 新增书籍分类目录
     * 
     * @param mallCategories 书籍分类目录
     * @return 结果
     */
    @Override
    public int insertMallCategories(MallCategories mallCategories)
    {
        return mallCategoriesMapper.insertMallCategories(mallCategories);
    }

    /**
     * 修改书籍分类目录
     * 
     * @param mallCategories 书籍分类目录
     * @return 结果
     */
    @Override
    public int updateMallCategories(MallCategories mallCategories)
    {
        return mallCategoriesMapper.updateMallCategories(mallCategories);
    }

    /**
     * 批量删除书籍分类目录
     * 
     * @param categoryIds 需要删除的书籍分类目录主键
     * @return 结果
     */
    @Override
    public int deleteMallCategoriesByCategoryIds(String[] categoryIds)
    {
        return mallCategoriesMapper.deleteMallCategoriesByCategoryIds(categoryIds);
    }

    /**
     * 删除书籍分类目录信息
     * 
     * @param categoryId 书籍分类目录主键
     * @return 结果
     */
    @Override
    public int deleteMallCategoriesByCategoryId(String categoryId)
    {
        return mallCategoriesMapper.deleteMallCategoriesByCategoryId(categoryId);
    }
}
