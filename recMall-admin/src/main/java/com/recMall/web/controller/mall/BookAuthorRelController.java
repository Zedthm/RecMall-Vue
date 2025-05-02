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
import com.recMall.mall.domain.BookAuthorRel;
import com.recMall.mall.service.IBookAuthorRelService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 书籍与作者多对多关系Controller
 * 
 * @author recMall
 * @date 2025-04-18
 */
@RestController
@RequestMapping("/mall/rel")
public class BookAuthorRelController extends BaseController
{
    @Autowired
    private IBookAuthorRelService bookAuthorRelService;

    /**
     * 查询书籍与作者多对多关系列表
     */
    @PreAuthorize("@ss.hasPermi('mall:rel:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookAuthorRel bookAuthorRel)
    {
        startPage();
        List<BookAuthorRel> list = bookAuthorRelService.selectBookAuthorRelList(bookAuthorRel);
        return getDataTable(list);
    }

    /**
     * 导出书籍与作者多对多关系列表
     */
    @PreAuthorize("@ss.hasPermi('mall:rel:export')")
    @Log(title = "书籍与作者多对多关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BookAuthorRel bookAuthorRel)
    {
        List<BookAuthorRel> list = bookAuthorRelService.selectBookAuthorRelList(bookAuthorRel);
        ExcelUtil<BookAuthorRel> util = new ExcelUtil<BookAuthorRel>(BookAuthorRel.class);
        util.exportExcel(response, list, "书籍与作者多对多关系数据");
    }

    /**
     * 获取书籍与作者多对多关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:rel:query')")
    @GetMapping(value = "/{bookId}")
    public AjaxResult getInfo(@PathVariable("bookId") String bookId)
    {
        return success(bookAuthorRelService.selectBookAuthorRelByBookId(bookId));
    }

    /**
     * 新增书籍与作者多对多关系
     */
    @PreAuthorize("@ss.hasPermi('mall:rel:add')")
    @Log(title = "书籍与作者多对多关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookAuthorRel bookAuthorRel)
    {
        return toAjax(bookAuthorRelService.insertBookAuthorRel(bookAuthorRel));
    }

    /**
     * 修改书籍与作者多对多关系
     */
    @PreAuthorize("@ss.hasPermi('mall:rel:edit')")
    @Log(title = "书籍与作者多对多关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookAuthorRel bookAuthorRel)
    {
        return toAjax(bookAuthorRelService.updateBookAuthorRel(bookAuthorRel));
    }

    /**
     * 删除书籍与作者多对多关系
     */
    @PreAuthorize("@ss.hasPermi('mall:rel:remove')")
    @Log(title = "书籍与作者多对多关系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bookIds}")
    public AjaxResult remove(@PathVariable String[] bookIds)
    {
        return toAjax(bookAuthorRelService.deleteBookAuthorRelByBookIds(bookIds));
    }
}
