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
import com.recMall.mall.domain.BookCategory;
import com.recMall.mall.service.IBookCategoryService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 书籍类别Controller
 * 
 * @author recMall
 * @date 2025-04-18
 */
@RestController
@RequestMapping("/mall/category")
public class BookCategoryController extends BaseController
{
    @Autowired
    private IBookCategoryService bookCategoryService;

    /**
     * 查询书籍类别列表
     */
    @PreAuthorize("@ss.hasPermi('mall:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookCategory bookCategory)
    {
        startPage();
        List<BookCategory> list = bookCategoryService.selectBookCategoryList(bookCategory);
        return getDataTable(list);
    }

    /**
     * 导出书籍类别列表
     */
    @PreAuthorize("@ss.hasPermi('mall:category:export')")
    @Log(title = "书籍类别", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BookCategory bookCategory)
    {
        List<BookCategory> list = bookCategoryService.selectBookCategoryList(bookCategory);
        ExcelUtil<BookCategory> util = new ExcelUtil<BookCategory>(BookCategory.class);
        util.exportExcel(response, list, "书籍类别数据");
    }

    /**
     * 获取书籍类别详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:category:query')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId)
    {
        return success(bookCategoryService.selectBookCategoryByCategoryId(categoryId));
    }

    /**
     * 新增书籍类别
     */
    @PreAuthorize("@ss.hasPermi('mall:category:add')")
    @Log(title = "书籍类别", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookCategory bookCategory)
    {
        return toAjax(bookCategoryService.insertBookCategory(bookCategory));
    }

    /**
     * 修改书籍类别
     */
    @PreAuthorize("@ss.hasPermi('mall:category:edit')")
    @Log(title = "书籍类别", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookCategory bookCategory)
    {
        return toAjax(bookCategoryService.updateBookCategory(bookCategory));
    }

    /**
     * 删除书籍类别
     */
    @PreAuthorize("@ss.hasPermi('mall:category:remove')")
    @Log(title = "书籍类别", businessType = BusinessType.DELETE)
	@DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds)
    {
        return toAjax(bookCategoryService.deleteBookCategoryByCategoryIds(categoryIds));
    }
}
