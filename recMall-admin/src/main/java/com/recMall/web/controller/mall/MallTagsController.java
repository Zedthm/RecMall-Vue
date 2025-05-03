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
import com.recMall.mall.domain.MallTags;
import com.recMall.mall.service.IMallTagsService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 存储系统标签信息Controller
 * 
 * @author recMall
 * @date 2025-05-03
 */
@RestController
@RequestMapping("/mall/tags")
public class MallTagsController extends BaseController
{
    @Autowired
    private IMallTagsService mallTagsService;

    /**
     * 查询存储系统标签信息列表
     */
    @PreAuthorize("@ss.hasPermi('mall:tags:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallTags mallTags)
    {
        startPage();
        List<MallTags> list = mallTagsService.selectMallTagsList(mallTags);
        return getDataTable(list);
    }

    /**
     * 导出存储系统标签信息列表
     */
    @PreAuthorize("@ss.hasPermi('mall:tags:export')")
    @Log(title = "存储系统标签信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallTags mallTags)
    {
        List<MallTags> list = mallTagsService.selectMallTagsList(mallTags);
        ExcelUtil<MallTags> util = new ExcelUtil<MallTags>(MallTags.class);
        util.exportExcel(response, list, "存储系统标签信息数据");
    }

    /**
     * 获取存储系统标签信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:tags:query')")
    @GetMapping(value = "/{tagId}")
    public AjaxResult getInfo(@PathVariable("tagId") String tagId)
    {
        return success(mallTagsService.selectMallTagsByTagId(tagId));
    }

    /**
     * 新增存储系统标签信息
     */
    @PreAuthorize("@ss.hasPermi('mall:tags:add')")
    @Log(title = "存储系统标签信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallTags mallTags)
    {
        return toAjax(mallTagsService.insertMallTags(mallTags));
    }

    /**
     * 修改存储系统标签信息
     */
    @PreAuthorize("@ss.hasPermi('mall:tags:edit')")
    @Log(title = "存储系统标签信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallTags mallTags)
    {
        return toAjax(mallTagsService.updateMallTags(mallTags));
    }

    /**
     * 删除存储系统标签信息
     */
    @PreAuthorize("@ss.hasPermi('mall:tags:remove')")
    @Log(title = "存储系统标签信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tagIds}")
    public AjaxResult remove(@PathVariable String[] tagIds)
    {
        return toAjax(mallTagsService.deleteMallTagsByTagIds(tagIds));
    }
}
