package com.recMall.web.controller.system;

import com.aliyuncs.exceptions.ClientException;
import com.recMall.common.constant.CacheConstants;
import com.recMall.common.core.redis.RedisCache;
import com.recMall.framework.web.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.recMall.common.core.controller.BaseController;
import com.recMall.common.core.domain.AjaxResult;
import com.recMall.common.core.domain.model.RegisterBody;
import com.recMall.common.utils.StringUtils;
import com.recMall.framework.web.service.SysRegisterService;
import com.recMall.system.service.ISysConfigService;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 注册验证
 * 
 * @author zedthm
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
