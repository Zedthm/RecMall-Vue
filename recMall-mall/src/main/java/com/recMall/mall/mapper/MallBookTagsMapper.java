package com.recMall.mall.mapper;

import java.util.List;
import com.recMall.mall.domain.MallBookTags;

/**
 * 书籍与标签的关联关系Mapper接口
 * 
 * @author recMall
 * @date 2025-05-03
 */
public interface MallBookTagsMapper 
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
     * 删除书籍与标签的关联关系
     * 
     * @param bookId 书籍与标签的关联关系主键
     * @return 结果
     */
    public int deleteMallBookTagsByBookId(String bookId);

    /**
     * 批量删除书籍与标签的关联关系
     * 
     * @param bookIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMallBookTagsByBookIds(String[] bookIds);
}
