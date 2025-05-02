package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recMall.mall.mapper.AuthorMapper;
import com.recMall.mall.domain.Author;
import com.recMall.mall.service.IAuthorService;

/**
 * 书籍作者信息Service业务层处理
 * 
 * @author recMall
 * @date 2025-04-18
 */
@Service
public class AuthorServiceImpl implements IAuthorService 
{
    @Autowired
    private AuthorMapper authorMapper;

    /**
     * 查询书籍作者信息
     * 
     * @param authorId 书籍作者信息主键
     * @return 书籍作者信息
     */
    @Override
    public Author selectAuthorByAuthorId(String authorId)
    {
        return authorMapper.selectAuthorByAuthorId(authorId);
    }

    /**
     * 查询书籍作者信息列表
     * 
     * @param author 书籍作者信息
     * @return 书籍作者信息
     */
    @Override
    public List<Author> selectAuthorList(Author author)
    {
        return authorMapper.selectAuthorList(author);
    }

    /**
     * 新增书籍作者信息
     * 
     * @param author 书籍作者信息
     * @return 结果
     */
    @Override
    public int insertAuthor(Author author)
    {
        return authorMapper.insertAuthor(author);
    }

    /**
     * 修改书籍作者信息
     * 
     * @param author 书籍作者信息
     * @return 结果
     */
    @Override
    public int updateAuthor(Author author)
    {
        return authorMapper.updateAuthor(author);
    }

    /**
     * 批量删除书籍作者信息
     * 
     * @param authorIds 需要删除的书籍作者信息主键
     * @return 结果
     */
    @Override
    public int deleteAuthorByAuthorIds(String[] authorIds)
    {
        return authorMapper.deleteAuthorByAuthorIds(authorIds);
    }

    /**
     * 删除书籍作者信息信息
     * 
     * @param authorId 书籍作者信息主键
     * @return 结果
     */
    @Override
    public int deleteAuthorByAuthorId(String authorId)
    {
        return authorMapper.deleteAuthorByAuthorId(authorId);
    }
}
