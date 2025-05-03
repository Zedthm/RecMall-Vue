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
import com.recMall.mall.domain.MallUserProfiles;
import com.recMall.mall.service.IMallUserProfilesService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 用户画像及评分数据Controller
 * 
 * @author recMall
 * @date 2025-05-03
 */
@RestController
@RequestMapping("/mall/profiles")
public class MallUserProfilesController extends BaseController
{
    @Autowired
    private IMallUserProfilesService mallUserProfilesService;

    /**
     * 查询用户画像及评分数据列表
     */
    @PreAuthorize("@ss.hasPermi('mall:profiles:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallUserProfiles mallUserProfiles)
    {
        startPage();
        List<MallUserProfiles> list = mallUserProfilesService.selectMallUserProfilesList(mallUserProfiles);
        return getDataTable(list);
    }

    /**
     * 导出用户画像及评分数据列表
     */
    @PreAuthorize("@ss.hasPermi('mall:profiles:export')")
    @Log(title = "用户画像及评分数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallUserProfiles mallUserProfiles)
    {
        List<MallUserProfiles> list = mallUserProfilesService.selectMallUserProfilesList(mallUserProfiles);
        ExcelUtil<MallUserProfiles> util = new ExcelUtil<MallUserProfiles>(MallUserProfiles.class);
        util.exportExcel(response, list, "用户画像及评分数据数据");
    }

    /**
     * 获取用户画像及评分数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:profiles:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") String userId)
    {
        return success(mallUserProfilesService.selectMallUserProfilesByUserId(userId));
    }

    /**
     * 新增用户画像及评分数据
     */
    @PreAuthorize("@ss.hasPermi('mall:profiles:add')")
    @Log(title = "用户画像及评分数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallUserProfiles mallUserProfiles)
    {
        return toAjax(mallUserProfilesService.insertMallUserProfiles(mallUserProfiles));
    }

    /**
     * 修改用户画像及评分数据
     */
    @PreAuthorize("@ss.hasPermi('mall:profiles:edit')")
    @Log(title = "用户画像及评分数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallUserProfiles mallUserProfiles)
    {
        return toAjax(mallUserProfilesService.updateMallUserProfiles(mallUserProfiles));
    }

    /**
     * 删除用户画像及评分数据
     */
    @PreAuthorize("@ss.hasPermi('mall:profiles:remove')")
    @Log(title = "用户画像及评分数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable String[] userIds)
    {
        return toAjax(mallUserProfilesService.deleteMallUserProfilesByUserIds(userIds));
    }
}
