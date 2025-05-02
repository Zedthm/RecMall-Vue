package com.recMall.mall.mapper;

import java.util.List;
import com.recMall.mall.domain.BookTagRel;

/**
 * 书籍与标签多对多关系Mapper接口
 * 
 * @author recMall
 * @date 2025-04-18
 */
public interface BookTagRelMapper 
{
    /**
     * 查询书籍与标签多对多关系
     * 
     * @param bookId 书籍与标签多对多关系主键
     * @return 书籍与标签多对多关系
     */
    public BookTagRel selectBookTagRelByBookId(String bookId);

    /**
     * 查询书籍与标签多对多关系列表
     * 
     * @param bookTagRel 书籍与标签多对多关系
     * @return 书籍与标签多对多关系集合
     */
    public List<BookTagRel> selectBookTagRelList(BookTagRel bookTagRel);

    /**
     * 新增书籍与标签多对多关系
     * 
     * @param bookTagRel 书籍与标签多对多关系
     * @return 结果
     */
    public int insertBookTagRel(BookTagRel bookTagRel);

    /**
     * 修改书籍与标签多对多关系
     * 
     * @param bookTagRel 书籍与标签多对多关系
     * @return 结果
     */
    public int updateBookTagRel(BookTagRel bookTagRel);

    /**
     * 删除书籍与标签多对多关系
     * 
     * @param bookId 书籍与标签多对多关系主键
     * @return 结果
     */
    public int deleteBookTagRelByBookId(String bookId);

    /**
     * 批量删除书籍与标签多对多关系
     * 
     * @param bookIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBookTagRelByBookIds(String[] bookIds);
}
