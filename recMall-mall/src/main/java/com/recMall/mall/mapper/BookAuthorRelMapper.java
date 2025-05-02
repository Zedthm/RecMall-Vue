package com.recMall.mall.mapper;

import java.util.List;
import com.recMall.mall.domain.BookAuthorRel;

/**
 * 书籍与作者多对多关系Mapper接口
 * 
 * @author recMall
 * @date 2025-04-18
 */
public interface BookAuthorRelMapper 
{
    /**
     * 查询书籍与作者多对多关系
     * 
     * @param bookId 书籍与作者多对多关系主键
     * @return 书籍与作者多对多关系
     */
    public BookAuthorRel selectBookAuthorRelByBookId(String bookId);

    /**
     * 查询书籍与作者多对多关系列表
     * 
     * @param bookAuthorRel 书籍与作者多对多关系
     * @return 书籍与作者多对多关系集合
     */
    public List<BookAuthorRel> selectBookAuthorRelList(BookAuthorRel bookAuthorRel);

    /**
     * 新增书籍与作者多对多关系
     * 
     * @param bookAuthorRel 书籍与作者多对多关系
     * @return 结果
     */
    public int insertBookAuthorRel(BookAuthorRel bookAuthorRel);

    /**
     * 修改书籍与作者多对多关系
     * 
     * @param bookAuthorRel 书籍与作者多对多关系
     * @return 结果
     */
    public int updateBookAuthorRel(BookAuthorRel bookAuthorRel);

    /**
     * 删除书籍与作者多对多关系
     * 
     * @param bookId 书籍与作者多对多关系主键
     * @return 结果
     */
    public int deleteBookAuthorRelByBookId(String bookId);

    /**
     * 批量删除书籍与作者多对多关系
     * 
     * @param bookIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBookAuthorRelByBookIds(String[] bookIds);
}
