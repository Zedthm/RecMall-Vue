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
import com.recMall.mall.domain.MallCategories;
import com.recMall.mall.service.IMallCategoriesService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 书籍分类目录Controller
 * 
 * @author recMall
 * @date 2025-05-03
 */
@RestController
@RequestMapping("/mall/categories")
public class MallCategoriesController extends BaseController
{
    @Autowired
    private IMallCategoriesService mallCategoriesService;

    /**
     * 查询书籍分类目录列表
     */
    @PreAuthorize("@ss.hasPermi('mall:categories:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallCategories mallCategories)
    {
        startPage();
        List<MallCategories> list = mallCategoriesService.selectMallCategoriesList(mallCategories);
        return getDataTable(list);
    }

    /**
     * 导出书籍分类目录列表
     */
    @PreAuthorize("@ss.hasPermi('mall:categories:export')")
    @Log(title = "书籍分类目录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallCategories mallCategories)
    {
        List<MallCategories> list = mallCategoriesService.selectMallCategoriesList(mallCategories);
        ExcelUtil<MallCategories> util = new ExcelUtil<MallCategories>(MallCategories.class);
        util.exportExcel(response, list, "书籍分类目录数据");
    }

    /**
     * 获取书籍分类目录详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:categories:query')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") String categoryId)
    {
        return success(mallCategoriesService.selectMallCategoriesByCategoryId(categoryId));
    }

    /**
     * 新增书籍分类目录
     */
    @PreAuthorize("@ss.hasPermi('mall:categories:add')")
    @Log(title = "书籍分类目录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallCategories mallCategories)
    {
        return toAjax(mallCategoriesService.insertMallCategories(mallCategories));
    }

    /**
     * 修改书籍分类目录
     */
    @PreAuthorize("@ss.hasPermi('mall:categories:edit')")
    @Log(title = "书籍分类目录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallCategories mallCategories)
    {
        return toAjax(mallCategoriesService.updateMallCategories(mallCategories));
    }

    /**
     * 删除书籍分类目录
     */
    @PreAuthorize("@ss.hasPermi('mall:categories:remove')")
    @Log(title = "书籍分类目录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable String[] categoryIds)
    {
        return toAjax(mallCategoriesService.deleteMallCategoriesByCategoryIds(categoryIds));
    }
}
