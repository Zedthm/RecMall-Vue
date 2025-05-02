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
import com.recMall.mall.domain.BookTag;
import com.recMall.mall.service.IBookTagService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 书籍标签字典Controller
 * 
 * @author recMall
 * @date 2025-04-18
 */
@RestController
@RequestMapping("/mall/bookTag")
public class BookTagController extends BaseController
{
    @Autowired
    private IBookTagService bookTagService;

    /**
     * 查询书籍标签字典列表
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTag:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookTag bookTag)
    {
        startPage();
        List<BookTag> list = bookTagService.selectBookTagList(bookTag);
        return getDataTable(list);
    }

    /**
     * 导出书籍标签字典列表
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTag:export')")
    @Log(title = "书籍标签字典", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BookTag bookTag)
    {
        List<BookTag> list = bookTagService.selectBookTagList(bookTag);
        ExcelUtil<BookTag> util = new ExcelUtil<BookTag>(BookTag.class);
        util.exportExcel(response, list, "书籍标签字典数据");
    }

    /**
     * 获取书籍标签字典详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTag:query')")
    @GetMapping(value = "/{tagId}")
    public AjaxResult getInfo(@PathVariable("tagId") String tagId)
    {
        return success(bookTagService.selectBookTagByTagId(tagId));
    }

    /**
     * 新增书籍标签字典
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTag:add')")
    @Log(title = "书籍标签字典", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookTag bookTag)
    {
        return toAjax(bookTagService.insertBookTag(bookTag));
    }

    /**
     * 修改书籍标签字典
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTag:edit')")
    @Log(title = "书籍标签字典", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookTag bookTag)
    {
        return toAjax(bookTagService.updateBookTag(bookTag));
    }

    /**
     * 删除书籍标签字典
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTag:remove')")
    @Log(title = "书籍标签字典", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tagIds}")
    public AjaxResult remove(@PathVariable String[] tagIds)
    {
        return toAjax(bookTagService.deleteBookTagByTagIds(tagIds));
    }
}
