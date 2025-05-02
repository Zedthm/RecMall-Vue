package com.recMall.web.controller.mall;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.spi.CopyOnWrite;
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
import com.recMall.mall.domain.UserTagRel;
import com.recMall.mall.service.IUserTagRelService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 用户与标签多对多关系Controller
 * 
 * @author recMall
 * @date 2025-04-18
 */
@RestController
@RequestMapping("/mall/userTagRel")
public class UserTagRelController extends BaseController
{
    @Autowired
    private IUserTagRelService userTagRelService;
    /**
     * 查询用户与标签多对多关系列表
     */
    @PreAuthorize("@ss.hasPermi('mall:userTagRel:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserTagRel userTagRel)
    {
        startPage();
        List<UserTagRel> list = userTagRelService.selectUserTagRelList(userTagRel);
        return getDataTable(list);
    }

    /**
     * 导出用户与标签多对多关系列表
     */
    @PreAuthorize("@ss.hasPermi('mall:userTagRel:export')")
    @Log(title = "用户与标签多对多关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserTagRel userTagRel)
    {
        List<UserTagRel> list = userTagRelService.selectUserTagRelList(userTagRel);
        ExcelUtil<UserTagRel> util = new ExcelUtil<UserTagRel>(UserTagRel.class);
        util.exportExcel(response, list, "用户与标签多对多关系数据");
    }

    /**
     * 获取用户与标签多对多关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:userTagRel:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") String userId)
    {
        return success(userTagRelService.selectUserTagRelByUserId(userId));
    }

    /**
     * 新增用户与标签多对多关系
     */
    @PreAuthorize("@ss.hasPermi('mall:userTagRel:add')")
    @Log(title = "用户与标签多对多关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserTagRel userTagRel)
    {
        return toAjax(userTagRelService.insertUserTagRel(userTagRel));
    }

    /**
     * 修改用户与标签多对多关系
     */
    @PreAuthorize("@ss.hasPermi('mall:userTagRel:edit')")
    @Log(title = "用户与标签多对多关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserTagRel userTagRel)
    {
        return toAjax(userTagRelService.updateUserTagRel(userTagRel));
    }

    /**
     * 删除用户与标签多对多关系
     */
    @PreAuthorize("@ss.hasPermi('mall:userTagRel:remove')")
    @Log(title = "用户与标签多对多关系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable String[] userIds)
    {
        return toAjax(userTagRelService.deleteUserTagRelByUserIds(userIds));
    }
}
