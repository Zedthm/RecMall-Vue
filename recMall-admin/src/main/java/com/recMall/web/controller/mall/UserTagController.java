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
import com.recMall.mall.domain.UserTag;
import com.recMall.mall.service.IUserTagService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 用户标签字典Controller
 * 
 * @author recMall
 * @date 2025-04-18
 */
@RestController
@RequestMapping("/mall/userTag")
public class UserTagController extends BaseController
{
    @Autowired
    private IUserTagService userTagService;

    /**
     * 查询用户标签字典列表
     */
    @PreAuthorize("@ss.hasPermi('mall:userTag:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserTag userTag)
    {
        startPage();
        List<UserTag> list = userTagService.selectUserTagList(userTag);
        return getDataTable(list);
    }

    /**
     * 导出用户标签字典列表
     */
    @PreAuthorize("@ss.hasPermi('mall:userTag:export')")
    @Log(title = "用户标签字典", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserTag userTag)
    {
        List<UserTag> list = userTagService.selectUserTagList(userTag);
        ExcelUtil<UserTag> util = new ExcelUtil<UserTag>(UserTag.class);
        util.exportExcel(response, list, "用户标签字典数据");
    }

    /**
     * 获取用户标签字典详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:userTag:query')")
    @GetMapping(value = "/{tagId}")
    public AjaxResult getInfo(@PathVariable("tagId") String tagId)
    {
        return success(userTagService.selectUserTagByTagId(tagId));
    }

    /**
     * 新增用户标签字典
     */
    @PreAuthorize("@ss.hasPermi('mall:userTag:add')")
    @Log(title = "用户标签字典", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserTag userTag)
    {
        return toAjax(userTagService.insertUserTag(userTag));
    }

    /**
     * 修改用户标签字典
     */
    @PreAuthorize("@ss.hasPermi('mall:userTag:edit')")
    @Log(title = "用户标签字典", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserTag userTag)
    {
        return toAjax(userTagService.updateUserTag(userTag));
    }

    /**
     * 删除用户标签字典
     */
    @PreAuthorize("@ss.hasPermi('mall:userTag:remove')")
    @Log(title = "用户标签字典", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tagIds}")
    public AjaxResult remove(@PathVariable String[] tagIds)
    {
        return toAjax(userTagService.deleteUserTagByTagIds(tagIds));
    }
}
