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
import com.recMall.mall.domain.BookOrders;
import com.recMall.mall.service.IBookOrdersService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 书籍订单Controller
 * 
 * @author recMall
 * @date 2025-04-18
 */
@RestController
@RequestMapping("/mall/orders")
public class BookOrdersController extends BaseController
{
    @Autowired
    private IBookOrdersService bookOrdersService;

    /**
     * 查询书籍订单列表
     */
    @PreAuthorize("@ss.hasPermi('mall:orders:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookOrders bookOrders)
    {
        startPage();
        List<BookOrders> list = bookOrdersService.selectBookOrdersList(bookOrders);
        return getDataTable(list);
    }

    /**
     * 导出书籍订单列表
     */
    @PreAuthorize("@ss.hasPermi('mall:orders:export')")
    @Log(title = "书籍订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BookOrders bookOrders)
    {
        List<BookOrders> list = bookOrdersService.selectBookOrdersList(bookOrders);
        ExcelUtil<BookOrders> util = new ExcelUtil<BookOrders>(BookOrders.class);
        util.exportExcel(response, list, "书籍订单数据");
    }

    /**
     * 获取书籍订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:orders:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bookOrdersService.selectBookOrdersById(id));
    }

    /**
     * 新增书籍订单
     */
    @PreAuthorize("@ss.hasPermi('mall:orders:add')")
    @Log(title = "书籍订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookOrders bookOrders)
    {
        return toAjax(bookOrdersService.insertBookOrders(bookOrders));
    }

    /**
     * 修改书籍订单
     */
    @PreAuthorize("@ss.hasPermi('mall:orders:edit')")
    @Log(title = "书籍订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookOrders bookOrders)
    {
        return toAjax(bookOrdersService.updateBookOrders(bookOrders));
    }

    /**
     * 删除书籍订单
     */
    @PreAuthorize("@ss.hasPermi('mall:orders:remove')")
    @Log(title = "书籍订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bookOrdersService.deleteBookOrdersByIds(ids));
    }
}
