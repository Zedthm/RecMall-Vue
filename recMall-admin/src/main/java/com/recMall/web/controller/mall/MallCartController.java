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
import com.recMall.mall.domain.MallCart;
import com.recMall.mall.service.IMallCartService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 购物车Controller
 * 
 * @author recMall
 * @date 2025-05-04
 */
@RestController
@RequestMapping("/mall/cart")
public class MallCartController extends BaseController
{
    @Autowired
    private IMallCartService mallCartService;

    /**
     * 查询购物车列表
     */
    @PreAuthorize("@ss.hasPermi('mall:cart:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallCart mallCart)
    {
        startPage();
        List<MallCart> list = mallCartService.selectMallCartList(mallCart);
        return getDataTable(list);
    }

    /**
     * 导出购物车列表
     */
    @PreAuthorize("@ss.hasPermi('mall:cart:export')")
    @Log(title = "购物车", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallCart mallCart)
    {
        List<MallCart> list = mallCartService.selectMallCartList(mallCart);
        ExcelUtil<MallCart> util = new ExcelUtil<MallCart>(MallCart.class);
        util.exportExcel(response, list, "购物车数据");
    }

    /**
     * 获取购物车详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:cart:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mallCartService.selectMallCartById(id));
    }

    /**
     * 新增购物车
     */
    @PreAuthorize("@ss.hasPermi('mall:cart:add')")
    @Log(title = "购物车", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallCart mallCart)
    {
        return toAjax(mallCartService.insertMallCart(mallCart));
    }

    /**
     * 修改购物车
     */
    @PreAuthorize("@ss.hasPermi('mall:cart:edit')")
    @Log(title = "购物车", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallCart mallCart)
    {
        return toAjax(mallCartService.updateMallCart(mallCart));
    }

    /**
     * 删除购物车
     */
    @PreAuthorize("@ss.hasPermi('mall:cart:remove')")
    @Log(title = "购物车", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mallCartService.deleteMallCartByIds(ids));
    }
}
