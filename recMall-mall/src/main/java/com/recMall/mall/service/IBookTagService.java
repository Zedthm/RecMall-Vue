package com.recMall.mall.service;

import java.util.List;
import com.recMall.mall.domain.BookTag;

/**
 * 书籍标签字典Service接口
 * 
 * @author recMall
 * @date 2025-04-18
 */
public interface IBookTagService 
{
    /**
     * 查询书籍标签字典
     * 
     * @param tagId 书籍标签字典主键
     * @return 书籍标签字典
     */
    public BookTag selectBookTagByTagId(String tagId);

    /**
     * 查询书籍标签字典列表
     * 
     * @param bookTag 书籍标签字典
     * @return 书籍标签字典集合
     */
    public List<BookTag> selectBookTagList(BookTag bookTag);

    /**
     * 新增书籍标签字典
     * 
     * @param bookTag 书籍标签字典
     * @return 结果
     */
    public int insertBookTag(BookTag bookTag);

    /**
     * 修改书籍标签字典
     * 
     * @param bookTag 书籍标签字典
     * @return 结果
     */
    public int updateBookTag(BookTag bookTag);

    /**
     * 批量删除书籍标签字典
     * 
     * @param tagIds 需要删除的书籍标签字典主键集合
     * @return 结果
     */
    public int deleteBookTagByTagIds(String[] tagIds);

    /**
     * 删除书籍标签字典信息
     * 
     * @param tagId 书籍标签字典主键
     * @return 结果
     */
    public int deleteBookTagByTagId(String tagId);
}
