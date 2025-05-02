package com.recMall.mall.mapper;

import java.util.List;
import com.recMall.mall.domain.Author;

/**
 * 书籍作者信息Mapper接口
 * 
 * @author recMall
 * @date 2025-04-18
 */
public interface AuthorMapper 
{
    /**
     * 查询书籍作者信息
     * 
     * @param authorId 书籍作者信息主键
     * @return 书籍作者信息
     */
    public Author selectAuthorByAuthorId(String authorId);

    /**
     * 查询书籍作者信息列表
     * 
     * @param author 书籍作者信息
     * @return 书籍作者信息集合
     */
    public List<Author> selectAuthorList(Author author);

    /**
     * 新增书籍作者信息
     * 
     * @param author 书籍作者信息
     * @return 结果
     */
    public int insertAuthor(Author author);

    /**
     * 修改书籍作者信息
     * 
     * @param author 书籍作者信息
     * @return 结果
     */
    public int updateAuthor(Author author);

    /**
     * 删除书籍作者信息
     * 
     * @param authorId 书籍作者信息主键
     * @return 结果
     */
    public int deleteAuthorByAuthorId(String authorId);

    /**
     * 批量删除书籍作者信息
     * 
     * @param authorIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAuthorByAuthorIds(String[] authorIds);
}
