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
import com.recMall.mall.domain.BookRatingStats;
import com.recMall.mall.service.IBookRatingStatsService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 书籍评分统计Controller
 * 
 * @author recMall
 * @date 2025-04-18
 */
@RestController
@RequestMapping("/mall/bookStats")
public class BookRatingStatsController extends BaseController
{
    @Autowired
    private IBookRatingStatsService bookRatingStatsService;

    /**
     * 查询书籍评分统计列表
     */
    @PreAuthorize("@ss.hasPermi('mall:bookStats:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookRatingStats bookRatingStats)
    {
        startPage();
        List<BookRatingStats> list = bookRatingStatsService.selectBookRatingStatsList(bookRatingStats);
        return getDataTable(list);
    }

    /**
     * 导出书籍评分统计列表
     */
    @PreAuthorize("@ss.hasPermi('mall:bookStats:export')")
    @Log(title = "书籍评分统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BookRatingStats bookRatingStats)
    {
        List<BookRatingStats> list = bookRatingStatsService.selectBookRatingStatsList(bookRatingStats);
        ExcelUtil<BookRatingStats> util = new ExcelUtil<BookRatingStats>(BookRatingStats.class);
        util.exportExcel(response, list, "书籍评分统计数据");
    }

    /**
     * 获取书籍评分统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:bookStats:query')")
    @GetMapping(value = "/{bookId}")
    public AjaxResult getInfo(@PathVariable("bookId") String bookId)
    {
        return success(bookRatingStatsService.selectBookRatingStatsByBookId(bookId));
    }

    /**
     * 新增书籍评分统计
     */
    @PreAuthorize("@ss.hasPermi('mall:bookStats:add')")
    @Log(title = "书籍评分统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookRatingStats bookRatingStats)
    {
        return toAjax(bookRatingStatsService.insertBookRatingStats(bookRatingStats));
    }

    /**
     * 修改书籍评分统计
     */
    @PreAuthorize("@ss.hasPermi('mall:bookStats:edit')")
    @Log(title = "书籍评分统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookRatingStats bookRatingStats)
    {
        return toAjax(bookRatingStatsService.updateBookRatingStats(bookRatingStats));
    }

    /**
     * 删除书籍评分统计
     */
    @PreAuthorize("@ss.hasPermi('mall:bookStats:remove')")
    @Log(title = "书籍评分统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bookIds}")
    public AjaxResult remove(@PathVariable String[] bookIds)
    {
        return toAjax(bookRatingStatsService.deleteBookRatingStatsByBookIds(bookIds));
    }
}
