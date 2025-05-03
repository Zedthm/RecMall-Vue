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
import com.recMall.mall.domain.MallOrders;
import com.recMall.mall.service.IMallOrdersService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 订单信息Controller
 * 
 * @author recMall
 * @date 2025-05-03
 */
@RestController
@RequestMapping("/mall/orders")
public class MallOrdersController extends BaseController
{
    @Autowired
    private IMallOrdersService mallOrdersService;

    /**
     * 查询订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('mall:orders:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallOrders mallOrders)
    {
        startPage();
        List<MallOrders> list = mallOrdersService.selectMallOrdersList(mallOrders);
        return getDataTable(list);
    }

    /**
     * 导出订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('mall:orders:export')")
    @Log(title = "订单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallOrders mallOrders)
    {
        List<MallOrders> list = mallOrdersService.selectMallOrdersList(mallOrders);
        ExcelUtil<MallOrders> util = new ExcelUtil<MallOrders>(MallOrders.class);
        util.exportExcel(response, list, "订单信息数据");
    }

    /**
     * 获取订单信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:orders:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mallOrdersService.selectMallOrdersById(id));
    }

    /**
     * 新增订单信息
     */
    @PreAuthorize("@ss.hasPermi('mall:orders:add')")
    @Log(title = "订单信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallOrders mallOrders)
    {
        return toAjax(mallOrdersService.insertMallOrders(mallOrders));
    }

    /**
     * 修改订单信息
     */
    @PreAuthorize("@ss.hasPermi('mall:orders:edit')")
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallOrders mallOrders)
    {
        return toAjax(mallOrdersService.updateMallOrders(mallOrders));
    }

    /**
     * 删除订单信息
     */
    @PreAuthorize("@ss.hasPermi('mall:orders:remove')")
    @Log(title = "订单信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mallOrdersService.deleteMallOrdersByIds(ids));
    }
}
