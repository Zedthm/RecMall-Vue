package com.recMall.mall.mapper;

import java.util.List;
import com.recMall.mall.domain.MallCategories;

/**
 * 书籍分类目录Mapper接口
 * 
 * @author recMall
 * @date 2025-05-04
 */
public interface MallCategoriesMapper 
{
    /**
     * 查询书籍分类目录
     * 
     * @param categoryId 书籍分类目录主键
     * @return 书籍分类目录
     */
    public MallCategories selectMallCategoriesByCategoryId(String categoryId);

    /**
     * 查询书籍分类目录列表
     * 
     * @param mallCategories 书籍分类目录
     * @return 书籍分类目录集合
     */
    public List<MallCategories> selectMallCategoriesList(MallCategories mallCategories);

    /**
     * 新增书籍分类目录
     * 
     * @param mallCategories 书籍分类目录
     * @return 结果
     */
    public int insertMallCategories(MallCategories mallCategories);

    /**
     * 修改书籍分类目录
     * 
     * @param mallCategories 书籍分类目录
     * @return 结果
     */
    public int updateMallCategories(MallCategories mallCategories);

    /**
     * 删除书籍分类目录
     * 
     * @param categoryId 书籍分类目录主键
     * @return 结果
     */
    public int deleteMallCategoriesByCategoryId(String categoryId);

    /**
     * 批量删除书籍分类目录
     * 
     * @param categoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMallCategoriesByCategoryIds(String[] categoryIds);
}
