package com.recMall.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.recMall.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.recMall.mall.domain.MallBookTags;
import com.recMall.mall.mapper.MallBooksMapper;
import com.recMall.mall.domain.MallBooks;
import com.recMall.mall.service.IMallBooksService;

/**
 * 商品信息-书籍核心数据Service业务层处理
 * 
 * @author recMall
 * @date 2025-05-03
 */
@Service
public class MallBooksServiceImpl implements IMallBooksService 
{
    @Autowired
    private MallBooksMapper mallBooksMapper;

    /**
     * 查询商品信息-书籍核心数据
     * 
     * @param bookId 商品信息-书籍核心数据主键
     * @return 商品信息-书籍核心数据
     */
    @Override
    public MallBooks selectMallBooksByBookId(String bookId)
    {
        return mallBooksMapper.selectMallBooksByBookId(bookId);
    }

    /**
     * 查询商品信息-书籍核心数据列表
     * 
     * @param mallBooks 商品信息-书籍核心数据
     * @return 商品信息-书籍核心数据
     */
    @Override
    public List<MallBooks> selectMallBooksList(MallBooks mallBooks)
    {
        return mallBooksMapper.selectMallBooksList(mallBooks);
    }

    /**
     * 新增商品信息-书籍核心数据
     * 
     * @param mallBooks 商品信息-书籍核心数据
     * @return 结果
     */
    @Transactional
    @Override
    public int insertMallBooks(MallBooks mallBooks)
    {
        int rows = mallBooksMapper.insertMallBooks(mallBooks);
        insertMallBookTags(mallBooks);
        return rows;
    }

    /**
     * 修改商品信息-书籍核心数据
     * 
     * @param mallBooks 商品信息-书籍核心数据
     * @return 结果
     */
    @Transactional
    @Override
    public int updateMallBooks(MallBooks mallBooks)
    {
        mallBooksMapper.deleteMallBookTagsByBookId(mallBooks.getBookId());
        insertMallBookTags(mallBooks);
        return mallBooksMapper.updateMallBooks(mallBooks);
    }

    /**
     * 批量删除商品信息-书籍核心数据
     * 
     * @param bookIds 需要删除的商品信息-书籍核心数据主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMallBooksByBookIds(String[] bookIds)
    {
        mallBooksMapper.deleteMallBookTagsByBookIds(bookIds);
        return mallBooksMapper.deleteMallBooksByBookIds(bookIds);
    }

    /**
     * 删除商品信息-书籍核心数据信息
     * 
     * @param bookId 商品信息-书籍核心数据主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMallBooksByBookId(String bookId)
    {
        mallBooksMapper.deleteMallBookTagsByBookId(bookId);
        return mallBooksMapper.deleteMallBooksByBookId(bookId);
    }

    /**
     * 新增书籍与标签的关联关系信息
     * 
     * @param mallBooks 商品信息-书籍核心数据对象
     */
    public void insertMallBookTags(MallBooks mallBooks)
    {
        List<MallBookTags> mallBookTagsList = mallBooks.getMallBookTagsList();
        String bookId = mallBooks.getBookId();
        if (StringUtils.isNotNull(mallBookTagsList))
        {
            List<MallBookTags> list = new ArrayList<MallBookTags>();
            for (MallBookTags mallBookTags : mallBookTagsList)
            {
                mallBookTags.setBookId(bookId);
                list.add(mallBookTags);
            }
            if (list.size() > 0)
            {
                mallBooksMapper.batchMallBookTags(list);
            }
        }
    }
}
