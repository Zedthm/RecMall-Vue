package com.recMall.mall.service;

import java.util.List;
import com.recMall.mall.domain.MallBookTags;

/**
 * 书籍与标签的关联关系Service接口
 * 
 * @author recMall
 * @date 2025-05-03
 */
public interface IMallBookTagsService 
{
    /**
     * 查询书籍与标签的关联关系
     * 
     * @param bookId 书籍与标签的关联关系主键
     * @return 书籍与标签的关联关系
     */
    public MallBookTags selectMallBookTagsByBookId(String bookId);

    /**
     * 查询书籍与标签的关联关系列表
     * 
     * @param mallBookTags 书籍与标签的关联关系
     * @return 书籍与标签的关联关系集合
     */
    public List<MallBookTags> selectMallBookTagsList(MallBookTags mallBookTags);

    /**
     * 新增书籍与标签的关联关系
     * 
     * @param mallBookTags 书籍与标签的关联关系
     * @return 结果
     */
    public int insertMallBookTags(MallBookTags mallBookTags);

    /**
     * 修改书籍与标签的关联关系
     * 
     * @param mallBookTags 书籍与标签的关联关系
     * @return 结果
     */
    public int updateMallBookTags(MallBookTags mallBookTags);

    /**
     * 批量删除书籍与标签的关联关系
     * 
     * @param bookIds 需要删除的书籍与标签的关联关系主键集合
     * @return 结果
     */
    public int deleteMallBookTagsByBookIds(String[] bookIds);

    /**
     * 删除书籍与标签的关联关系信息
     * 
     * @param bookId 书籍与标签的关联关系主键
     * @return 结果
     */
    public int deleteMallBookTagsByBookId(String bookId);
}
