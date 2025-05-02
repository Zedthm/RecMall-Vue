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
import com.recMall.mall.domain.BookReview;
import com.recMall.mall.service.IBookReviewService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 用户书评数据Controller
 * 
 * @author recMall
 * @date 2025-04-18
 */
@RestController
@RequestMapping("/mall/review")
public class BookReviewController extends BaseController
{
    @Autowired
    private IBookReviewService bookReviewService;

    /**
     * 查询用户书评数据列表
     */
    @PreAuthorize("@ss.hasPermi('mall:review:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookReview bookReview)
    {
        startPage();
        List<BookReview> list = bookReviewService.selectBookReviewList(bookReview);
        return getDataTable(list);
    }

    /**
     * 导出用户书评数据列表
     */
    @PreAuthorize("@ss.hasPermi('mall:review:export')")
    @Log(title = "用户书评数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BookReview bookReview)
    {
        List<BookReview> list = bookReviewService.selectBookReviewList(bookReview);
        ExcelUtil<BookReview> util = new ExcelUtil<BookReview>(BookReview.class);
        util.exportExcel(response, list, "用户书评数据数据");
    }

    /**
     * 获取用户书评数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:review:query')")
    @GetMapping(value = "/{reviewId}")
    public AjaxResult getInfo(@PathVariable("reviewId") String reviewId)
    {
        return success(bookReviewService.selectBookReviewByReviewId(reviewId));
    }

    /**
     * 新增用户书评数据
     */
    @PreAuthorize("@ss.hasPermi('mall:review:add')")
    @Log(title = "用户书评数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookReview bookReview)
    {
        return toAjax(bookReviewService.insertBookReview(bookReview));
    }

    /**
     * 修改用户书评数据
     */
    @PreAuthorize("@ss.hasPermi('mall:review:edit')")
    @Log(title = "用户书评数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookReview bookReview)
    {
        return toAjax(bookReviewService.updateBookReview(bookReview));
    }

    /**
     * 删除用户书评数据
     */
    @PreAuthorize("@ss.hasPermi('mall:review:remove')")
    @Log(title = "用户书评数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reviewIds}")
    public AjaxResult remove(@PathVariable String[] reviewIds)
    {
        return toAjax(bookReviewService.deleteBookReviewByReviewIds(reviewIds));
    }
}
