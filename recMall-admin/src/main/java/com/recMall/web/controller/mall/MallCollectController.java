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
import com.recMall.mall.domain.MallCollect;
import com.recMall.mall.service.IMallCollectService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 收藏信息Controller
 * 
 * @author recMall
 * @date 2025-05-04
 */
@RestController
@RequestMapping("/mall/collect")
public class MallCollectController extends BaseController
{
    @Autowired
    private IMallCollectService mallCollectService;

    /**
     * 查询收藏信息列表
     */
    @PreAuthorize("@ss.hasPermi('mall:collect:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallCollect mallCollect)
    {
        startPage();
        List<MallCollect> list = mallCollectService.selectMallCollectList(mallCollect);
        return getDataTable(list);
    }

    /**
     * 导出收藏信息列表
     */
    @PreAuthorize("@ss.hasPermi('mall:collect:export')")
    @Log(title = "收藏信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallCollect mallCollect)
    {
        List<MallCollect> list = mallCollectService.selectMallCollectList(mallCollect);
        ExcelUtil<MallCollect> util = new ExcelUtil<MallCollect>(MallCollect.class);
        util.exportExcel(response, list, "收藏信息数据");
    }

    /**
     * 获取收藏信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:collect:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mallCollectService.selectMallCollectById(id));
    }

    /**
     * 新增收藏信息
     */
    @PreAuthorize("@ss.hasPermi('mall:collect:add')")
    @Log(title = "收藏信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallCollect mallCollect)
    {
        return toAjax(mallCollectService.insertMallCollect(mallCollect));
    }

    /**
     * 修改收藏信息
     */
    @PreAuthorize("@ss.hasPermi('mall:collect:edit')")
    @Log(title = "收藏信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallCollect mallCollect)
    {
        return toAjax(mallCollectService.updateMallCollect(mallCollect));
    }

    /**
     * 删除收藏信息
     */
    @PreAuthorize("@ss.hasPermi('mall:collect:remove')")
    @Log(title = "收藏信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mallCollectService.deleteMallCollectByIds(ids));
    }
}
