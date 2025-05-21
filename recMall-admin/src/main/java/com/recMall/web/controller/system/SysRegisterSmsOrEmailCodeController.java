package com.recMall.web.controller.system;

import com.aliyuncs.exceptions.ClientException;
import com.recMall.common.constant.CacheConstants;
import com.recMall.common.core.controller.BaseController;
import com.recMall.common.core.domain.AjaxResult;
import com.recMall.common.core.redis.RedisCache;
import com.recMall.framework.web.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zedthm
 * @version 1.0
 * @date 2025/5/3 1:09
 * @description:
 */
@RestController
public class SysRegisterSmsOrEmailCodeController extends BaseController {
    @Resource
    private CaptchaService captchaService;

    @Resource
    private RedisCache redisCache;

    @PostMapping("/register/sendCode")
    public AjaxResult sendCode(@RequestBody Map<String, String> params) throws ClientException, MessagingException {
        String type = params.get("type");
        String account = params.get(type);

        if (!"phonenumber".equals(type) && !"email".equals(type)) {
            return AjaxResult.error("类型错误");
        }
        if (account == null || account.isEmpty()) {
            return AjaxResult.error("账号不能为空");
        }

        // 频率控制（1分钟限制）
        String rateKey = CacheConstants.REGISTER_CAPTCHA_CODE_KEY + type + ":" + account;
        if (redisCache.hasKey(rateKey)) {
            return AjaxResult.error("发送过于频繁");
        }
        redisCache.setCacheObject(rateKey, "1", 1, TimeUnit.MINUTES);

        boolean flag = captchaService.generateAndSendCode(account, type);
        if (flag) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error("发送失败");
        }
    }
}
