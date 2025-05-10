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
import com.recMall.mall.domain.MallComment;
import com.recMall.mall.service.IMallCommentService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 评论信息Controller
 * 
 * @author recMall
 * @date 2025-05-04
 */
@RestController
@RequestMapping("/mall/comment")
public class MallCommentController extends BaseController
{
    @Autowired
    private IMallCommentService mallCommentService;

    /**
     * 查询评论信息列表
     */
    @PreAuthorize("@ss.hasPermi('mall:comment:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallComment mallComment)
    {
        startPage();
        List<MallComment> list = mallCommentService.selectMallCommentList(mallComment);
        return getDataTable(list);
    }

    /**
     * 导出评论信息列表
     */
    @PreAuthorize("@ss.hasPermi('mall:comment:export')")
    @Log(title = "评论信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallComment mallComment)
    {
        List<MallComment> list = mallCommentService.selectMallCommentList(mallComment);
        ExcelUtil<MallComment> util = new ExcelUtil<MallComment>(MallComment.class);
        util.exportExcel(response, list, "评论信息数据");
    }

    /**
     * 获取评论信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:comment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mallCommentService.selectMallCommentById(id));
    }

    /**
     * 新增评论信息
     */
    @PreAuthorize("@ss.hasPermi('mall:comment:add')")
    @Log(title = "评论信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallComment mallComment)
    {
        return toAjax(mallCommentService.insertMallComment(mallComment));
    }

    /**
     * 修改评论信息
     */
    @PreAuthorize("@ss.hasPermi('mall:comment:edit')")
    @Log(title = "评论信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallComment mallComment)
    {
        return toAjax(mallCommentService.updateMallComment(mallComment));
    }

    /**
     * 删除评论信息
     */
    @PreAuthorize("@ss.hasPermi('mall:comment:remove')")
    @Log(title = "评论信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mallCommentService.deleteMallCommentByIds(ids));
    }
}
