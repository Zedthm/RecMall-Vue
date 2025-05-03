package com.recMall.mall.mapper;

import java.util.List;
import com.recMall.mall.domain.MallBooks;
import com.recMall.mall.domain.MallBookTags;

/**
 * 商品信息-书籍核心数据Mapper接口
 * 
 * @author recMall
 * @date 2025-05-03
 */
public interface MallBooksMapper 
{
    /**
     * 查询商品信息-书籍核心数据
     * 
     * @param bookId 商品信息-书籍核心数据主键
     * @return 商品信息-书籍核心数据
     */
    public MallBooks selectMallBooksByBookId(String bookId);

    /**
     * 查询商品信息-书籍核心数据列表
     * 
     * @param mallBooks 商品信息-书籍核心数据
     * @return 商品信息-书籍核心数据集合
     */
    public List<MallBooks> selectMallBooksList(MallBooks mallBooks);

    /**
     * 新增商品信息-书籍核心数据
     * 
     * @param mallBooks 商品信息-书籍核心数据
     * @return 结果
     */
    public int insertMallBooks(MallBooks mallBooks);

    /**
     * 修改商品信息-书籍核心数据
     * 
     * @param mallBooks 商品信息-书籍核心数据
     * @return 结果
     */
    public int updateMallBooks(MallBooks mallBooks);

    /**
     * 删除商品信息-书籍核心数据
     * 
     * @param bookId 商品信息-书籍核心数据主键
     * @return 结果
     */
    public int deleteMallBooksByBookId(String bookId);

    /**
     * 批量删除商品信息-书籍核心数据
     * 
     * @param bookIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMallBooksByBookIds(String[] bookIds);

    /**
     * 批量删除书籍与标签的关联关系
     * 
     * @param bookIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMallBookTagsByBookIds(String[] bookIds);
    
    /**
     * 批量新增书籍与标签的关联关系
     * 
     * @param mallBookTagsList 书籍与标签的关联关系列表
     * @return 结果
     */
    public int batchMallBookTags(List<MallBookTags> mallBookTagsList);
    

    /**
     * 通过商品信息-书籍核心数据主键删除书籍与标签的关联关系信息
     * 
     * @param bookId 商品信息-书籍核心数据ID
     * @return 结果
     */
    public int deleteMallBookTagsByBookId(String bookId);
}
