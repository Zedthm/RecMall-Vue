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
import com.recMall.mall.domain.Cart;
import com.recMall.mall.service.ICartService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 购物车Controller
 * 
 * @author recMall
 * @date 2025-04-18
 */
@RestController
@RequestMapping("/mall/cart")
public class CartController extends BaseController
{
    @Autowired
    private ICartService cartService;

    /**
     * 查询购物车列表
     */
    @PreAuthorize("@ss.hasPermi('mall:cart:list')")
    @GetMapping("/list")
    public TableDataInfo list(Cart cart)
    {
        startPage();
        List<Cart> list = cartService.selectCartList(cart);
        return getDataTable(list);
    }

    /**
     * 导出购物车列表
     */
    @PreAuthorize("@ss.hasPermi('mall:cart:export')")
    @Log(title = "购物车", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Cart cart)
    {
        List<Cart> list = cartService.selectCartList(cart);
        ExcelUtil<Cart> util = new ExcelUtil<Cart>(Cart.class);
        util.exportExcel(response, list, "购物车数据");
    }

    /**
     * 获取购物车详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:cart:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(cartService.selectCartById(id));
    }

    /**
     * 新增购物车
     */
    @PreAuthorize("@ss.hasPermi('mall:cart:add')")
    @Log(title = "购物车", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Cart cart)
    {
        return toAjax(cartService.insertCart(cart));
    }

    /**
     * 修改购物车
     */
    @PreAuthorize("@ss.hasPermi('mall:cart:edit')")
    @Log(title = "购物车", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Cart cart)
    {
        return toAjax(cartService.updateCart(cart));
    }

    /**
     * 删除购物车
     */
    @PreAuthorize("@ss.hasPermi('mall:cart:remove')")
    @Log(title = "购物车", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cartService.deleteCartByIds(ids));
    }
}
