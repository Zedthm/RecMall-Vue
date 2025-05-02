package com.recMall.web.controller.mall;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.recMall.common.annotation.Log;
import com.recMall.common.core.controller.BaseController;
import com.recMall.common.core.domain.AjaxResult;
import com.recMall.common.enums.BusinessType;
import com.recMall.mall.domain.BookTagRel;
import com.recMall.mall.service.IBookTagRelService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 书籍与标签多对多关系Controller
 * 
 * @author recMall
 * @date 2025-04-18
 */
@RestController
@RequestMapping("/mall/bookTagsRel")
public class BookTagRelController extends BaseController
{
    @Autowired
    private IBookTagRelService bookTagRelService;

    /**
     * 查询书籍与标签多对多关系列表
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTagsRel:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookTagRel bookTagRel)
    {
        startPage();
        List<BookTagRel> list = bookTagRelService.selectBookTagRelList(bookTagRel);
        return getDataTable(list);
    }

    /**
     * 导出书籍与标签多对多关系列表
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTagsRel:export')")
    @Log(title = "书籍与标签多对多关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BookTagRel bookTagRel)
    {
        List<BookTagRel> list = bookTagRelService.selectBookTagRelList(bookTagRel);
        ExcelUtil<BookTagRel> util = new ExcelUtil<BookTagRel>(BookTagRel.class);
        util.exportExcel(response, list, "书籍与标签多对多关系数据");
    }

    /**
     * 获取书籍与标签多对多关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTagsRel:query')")
    @GetMapping(value = "/{bookId}")
    public AjaxResult getInfo(@PathVariable("bookId") String bookId)
    {
        return success(bookTagRelService.selectBookTagRelByBookId(bookId));
    }

    /**
     * 新增书籍与标签多对多关系
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTagsRel:add')")
    @Log(title = "书籍与标签多对多关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookTagRel bookTagRel)
    {
        return toAjax(bookTagRelService.insertBookTagRel(bookTagRel));
    }

    /**
     * 修改书籍与标签多对多关系
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTagsRel:edit')")
    @Log(title = "书籍与标签多对多关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookTagRel bookTagRel)
    {
        return toAjax(bookTagRelService.updateBookTagRel(bookTagRel));
    }

    /**
     * 删除书籍与标签多对多关系
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTagsRel:remove')")
    @Log(title = "书籍与标签多对多关系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bookIds}")
    public AjaxResult remove(@PathVariable String[] bookIds)
    {
        return toAjax(bookTagRelService.deleteBookTagRelByBookIds(bookIds));
    }
}
