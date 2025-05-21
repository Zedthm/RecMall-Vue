package com.recMall.framework.web.service;

import com.aliyuncs.exceptions.ClientException;
import com.recMall.common.constant.CacheConstants;
import com.recMall.common.core.redis.RedisCache;
import com.recMall.common.utils.MailUtils;
import com.recMall.common.utils.RandomUtil;
import com.recMall.common.utils.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class CaptchaService {
    @Autowired
    private RedisCache redisCache;
    
    // 生成并存储验证码
    public boolean generateAndSendCode(String account, String type) throws ClientException, MessagingException {
        String code = RandomUtil.randomNumbers(6);
        String key = CacheConstants.REGISTER_CAPTCHA_CODE_KEY + type + ":" + account;
        redisCache.setCacheObject(key, code, 5, TimeUnit.MINUTES);
        
        // 实际发送逻辑
        if ("phonenumber".equals(type)) {
            SmsUtil.sendSms(account, "您的验证码是：" + code);
        } else if ("email".equals(type)) {
            String title = "注册验证码";
            String text = "您的验证码是：" + code;
            Set<String> set = new HashSet<>();
            set.add(account);
            return MailUtils.sendEmail(set, text, title);

        }
        return false;
    }

    // 验证码校验
    public boolean validateCode(String account, String type, String code) {
        String key = CacheConstants.REGISTER_CAPTCHA_CODE_KEY + type + ":" + account;
        String storedCode = redisCache.getCacheObject(key);
        return code.equals(storedCode);
    }


}