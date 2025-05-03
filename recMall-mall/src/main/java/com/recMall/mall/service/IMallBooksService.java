package com.recMall.mall.service;

import java.util.List;
import com.recMall.mall.domain.MallBooks;

/**
 * 商品信息-书籍核心数据Service接口
 * 
 * @author recMall
 * @date 2025-05-03
 */
public interface IMallBooksService 
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
     * 批量删除商品信息-书籍核心数据
     * 
     * @param bookIds 需要删除的商品信息-书籍核心数据主键集合
     * @return 结果
     */
    public int deleteMallBooksByBookIds(String[] bookIds);

    /**
     * 删除商品信息-书籍核心数据信息
     * 
     * @param bookId 商品信息-书籍核心数据主键
     * @return 结果
     */
    public int deleteMallBooksByBookId(String bookId);
}
