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
import com.recMall.mall.domain.BookCategoryRel;
import com.recMall.mall.service.IBookCategoryRelService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 书籍类别关系Controller
 * 
 * @author recMall
 * @date 2025-04-18
 */
@RestController
@RequestMapping("/mall/bookCategoryRel")
public class BookCategoryRelController extends BaseController
{
    @Autowired
    private IBookCategoryRelService bookCategoryRelService;

    /**
     * 查询书籍类别关系列表
     */
    @PreAuthorize("@ss.hasPermi('mall:bookCategoryRel:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookCategoryRel bookCategoryRel)
    {
        startPage();
        List<BookCategoryRel> list = bookCategoryRelService.selectBookCategoryRelList(bookCategoryRel);
        return getDataTable(list);
    }

    /**
     * 导出书籍类别关系列表
     */
    @PreAuthorize("@ss.hasPermi('mall:bookCategoryRel:export')")
    @Log(title = "书籍类别关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BookCategoryRel bookCategoryRel)
    {
        List<BookCategoryRel> list = bookCategoryRelService.selectBookCategoryRelList(bookCategoryRel);
        ExcelUtil<BookCategoryRel> util = new ExcelUtil<BookCategoryRel>(BookCategoryRel.class);
        util.exportExcel(response, list, "书籍类别关系数据");
    }

    /**
     * 获取书籍类别关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:bookCategoryRel:query')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId)
    {
        return success(bookCategoryRelService.selectBookCategoryRelByCategoryId(categoryId));
    }

    /**
     * 新增书籍类别关系
     */
    @PreAuthorize("@ss.hasPermi('mall:bookCategoryRel:add')")
    @Log(title = "书籍类别关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookCategoryRel bookCategoryRel)
    {
        return toAjax(bookCategoryRelService.insertBookCategoryRel(bookCategoryRel));
    }

    /**
     * 修改书籍类别关系
     */
    @PreAuthorize("@ss.hasPermi('mall:bookCategoryRel:edit')")
    @Log(title = "书籍类别关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookCategoryRel bookCategoryRel)
    {
        return toAjax(bookCategoryRelService.updateBookCategoryRel(bookCategoryRel));
    }

    /**
     * 删除书籍类别关系
     */
    @PreAuthorize("@ss.hasPermi('mall:bookCategoryRel:remove')")
    @Log(title = "书籍类别关系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds)
    {
        return toAjax(bookCategoryRelService.deleteBookCategoryRelByCategoryIds(categoryIds));
    }
}
