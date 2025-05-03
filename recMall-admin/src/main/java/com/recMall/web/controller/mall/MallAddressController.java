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
import com.recMall.mall.domain.MallAddress;
import com.recMall.mall.service.IMallAddressService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 地址信息Controller
 * 
 * @author recMall
 * @date 2025-05-03
 */
@RestController
@RequestMapping("/mall/address")
public class MallAddressController extends BaseController
{
    @Autowired
    private IMallAddressService mallAddressService;

    /**
     * 查询地址信息列表
     */
    @PreAuthorize("@ss.hasPermi('mall:address:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallAddress mallAddress)
    {
        startPage();
        List<MallAddress> list = mallAddressService.selectMallAddressList(mallAddress);
        return getDataTable(list);
    }

    /**
     * 导出地址信息列表
     */
    @PreAuthorize("@ss.hasPermi('mall:address:export')")
    @Log(title = "地址信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallAddress mallAddress)
    {
        List<MallAddress> list = mallAddressService.selectMallAddressList(mallAddress);
        ExcelUtil<MallAddress> util = new ExcelUtil<MallAddress>(MallAddress.class);
        util.exportExcel(response, list, "地址信息数据");
    }

    /**
     * 获取地址信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:address:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mallAddressService.selectMallAddressById(id));
    }

    /**
     * 新增地址信息
     */
    @PreAuthorize("@ss.hasPermi('mall:address:add')")
    @Log(title = "地址信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallAddress mallAddress)
    {
        return toAjax(mallAddressService.insertMallAddress(mallAddress));
    }

    /**
     * 修改地址信息
     */
    @PreAuthorize("@ss.hasPermi('mall:address:edit')")
    @Log(title = "地址信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallAddress mallAddress)
    {
        return toAjax(mallAddressService.updateMallAddress(mallAddress));
    }

    /**
     * 删除地址信息
     */
    @PreAuthorize("@ss.hasPermi('mall:address:remove')")
    @Log(title = "地址信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mallAddressService.deleteMallAddressByIds(ids));
    }
}
