package com.recMall.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.recMall.common.config.RecMallConfig;
import com.recMall.common.utils.StringUtils;

/**
 * 首页
 *
 * @author zedthm
 */
@RestController
public class SysIndexController
{
    /** 系统基础配置 */
    @Autowired
    private RecMallConfig RecMallConfig;

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public String index()
    {
        return StringUtils.format("欢迎使用");
    }
}
