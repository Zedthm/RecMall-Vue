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
import com.recMall.mall.domain.MallCategoryTags;
import com.recMall.mall.service.IMallCategoryTagsService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 分类与标签的关联关系Controller
 * 
 * @author recMall
 * @date 2025-05-03
 */
@RestController
@RequestMapping("/mall/categoryTagsRel")
public class MallCategoryTagsController extends BaseController
{
    @Autowired
    private IMallCategoryTagsService mallCategoryTagsService;

    /**
     * 查询分类与标签的关联关系列表
     */
    @PreAuthorize("@ss.hasPermi('mall:categoryTagsRel:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallCategoryTags mallCategoryTags)
    {
        startPage();
        List<MallCategoryTags> list = mallCategoryTagsService.selectMallCategoryTagsList(mallCategoryTags);
        return getDataTable(list);
    }

    /**
     * 导出分类与标签的关联关系列表
     */
    @PreAuthorize("@ss.hasPermi('mall:categoryTagsRel:export')")
    @Log(title = "分类与标签的关联关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallCategoryTags mallCategoryTags)
    {
        List<MallCategoryTags> list = mallCategoryTagsService.selectMallCategoryTagsList(mallCategoryTags);
        ExcelUtil<MallCategoryTags> util = new ExcelUtil<MallCategoryTags>(MallCategoryTags.class);
        util.exportExcel(response, list, "分类与标签的关联关系数据");
    }

    /**
     * 获取分类与标签的关联关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:categoryTagsRel:query')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") String categoryId)
    {
        return success(mallCategoryTagsService.selectMallCategoryTagsByCategoryId(categoryId));
    }

    /**
     * 新增分类与标签的关联关系
     */
    @PreAuthorize("@ss.hasPermi('mall:categoryTagsRel:add')")
    @Log(title = "分类与标签的关联关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallCategoryTags mallCategoryTags)
    {
        return toAjax(mallCategoryTagsService.insertMallCategoryTags(mallCategoryTags));
    }

    /**
     * 修改分类与标签的关联关系
     */
    @PreAuthorize("@ss.hasPermi('mall:categoryTagsRel:edit')")
    @Log(title = "分类与标签的关联关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallCategoryTags mallCategoryTags)
    {
        return toAjax(mallCategoryTagsService.updateMallCategoryTags(mallCategoryTags));
    }

    /**
     * 删除分类与标签的关联关系
     */
    @PreAuthorize("@ss.hasPermi('mall:categoryTagsRel:remove')")
    @Log(title = "分类与标签的关联关系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable String[] categoryIds)
    {
        return toAjax(mallCategoryTagsService.deleteMallCategoryTagsByCategoryIds(categoryIds));
    }
}
