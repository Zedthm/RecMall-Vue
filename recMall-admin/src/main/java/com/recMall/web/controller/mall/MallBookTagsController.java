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
import com.recMall.mall.domain.MallBookTags;
import com.recMall.mall.service.IMallBookTagsService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 书籍与标签的关联关系Controller
 * 
 * @author recMall
 * @date 2025-05-03
 */
@RestController
@RequestMapping("/mall/bookTagsRel")
public class MallBookTagsController extends BaseController
{
    @Autowired
    private IMallBookTagsService mallBookTagsService;

    /**
     * 查询书籍与标签的关联关系列表
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTagsRel:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallBookTags mallBookTags)
    {
        startPage();
        List<MallBookTags> list = mallBookTagsService.selectMallBookTagsList(mallBookTags);
        return getDataTable(list);
    }

    /**
     * 导出书籍与标签的关联关系列表
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTagsRel:export')")
    @Log(title = "书籍与标签的关联关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallBookTags mallBookTags)
    {
        List<MallBookTags> list = mallBookTagsService.selectMallBookTagsList(mallBookTags);
        ExcelUtil<MallBookTags> util = new ExcelUtil<MallBookTags>(MallBookTags.class);
        util.exportExcel(response, list, "书籍与标签的关联关系数据");
    }

    /**
     * 获取书籍与标签的关联关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTagsRel:query')")
    @GetMapping(value = "/{bookId}")
    public AjaxResult getInfo(@PathVariable("bookId") String bookId)
    {
        return success(mallBookTagsService.selectMallBookTagsByBookId(bookId));
    }

    /**
     * 新增书籍与标签的关联关系
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTagsRel:add')")
    @Log(title = "书籍与标签的关联关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallBookTags mallBookTags)
    {
        return toAjax(mallBookTagsService.insertMallBookTags(mallBookTags));
    }

    /**
     * 修改书籍与标签的关联关系
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTagsRel:edit')")
    @Log(title = "书籍与标签的关联关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallBookTags mallBookTags)
    {
        return toAjax(mallBookTagsService.updateMallBookTags(mallBookTags));
    }

    /**
     * 删除书籍与标签的关联关系
     */
    @PreAuthorize("@ss.hasPermi('mall:bookTagsRel:remove')")
    @Log(title = "书籍与标签的关联关系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bookIds}")
    public AjaxResult remove(@PathVariable String[] bookIds)
    {
        return toAjax(mallBookTagsService.deleteMallBookTagsByBookIds(bookIds));
    }
}
