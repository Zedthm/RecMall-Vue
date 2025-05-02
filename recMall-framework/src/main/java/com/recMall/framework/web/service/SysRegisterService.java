package com.recMall.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.recMall.common.constant.CacheConstants;
import com.recMall.common.constant.Constants;
import com.recMall.common.constant.UserConstants;
import com.recMall.common.core.domain.entity.SysUser;
import com.recMall.common.core.domain.model.RegisterBody;
import com.recMall.common.core.redis.RedisCache;
import com.recMall.common.exception.user.CaptchaException;
import com.recMall.common.exception.user.CaptchaExpireException;
import com.recMall.common.utils.MessageUtils;
import com.recMall.common.utils.SecurityUtils;
import com.recMall.common.utils.StringUtils;
import com.recMall.framework.manager.AsyncManager;
import com.recMall.framework.manager.factory.AsyncFactory;
import com.recMall.system.service.ISysConfigService;
import com.recMall.system.service.ISysUserService;

/**
 * 注册校验方法
 * 
 * @author zedthm
 */
@Component
public class SysRegisterService
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private CaptchaService captchaService;

    /**
     * 注册
     */
    public String register(RegisterBody registerBody)
    {
        // 新增验证逻辑
        String registerType = registerBody.getRegisterType();
        String account = null;

        String msg = "", username = registerBody.getUsername(), password = registerBody.getPassword();
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);

        // 验证码开关
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled)
        {
            validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
        }
        // 验证注册类型
        if ("phonenumber".equals(registerType)) {
            account = registerBody.getPhonenumber();
            if (!validatePhone(account)) {
                return "手机号格式错误";
            }
            if (!captchaService.validateCode(account, "phone", registerBody.getSmsCode())) {
                return "短信验证码错误";
            }
        } else if ("email".equals(registerType)) {
            account = registerBody.getEmail();
            if (!validateEmail(account)) {
                return "邮箱格式错误";
            }
            if (!captchaService.validateCode(account, "email", registerBody.getEmailCode())) {
                return "邮箱验证码错误";
            }
        } else {
            return "不支持的注册方式";
        }
        if (StringUtils.isEmpty(username))
        {
            msg = "用户名不能为空";
        }
        else if (StringUtils.isEmpty(password))
        {
            msg = "用户密码不能为空";
        }
        else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            msg = "账户长度必须在2到20个字符之间";
        }
        else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            msg = "密码长度必须在5到20个字符之间";
        }
        else if (!userService.checkUserNameUnique(sysUser))
        {
            msg = "保存用户'" + username + "'失败，注册账号已存在";
        }
        else
        {
            sysUser.setNickName(username);
            sysUser.setPassword(SecurityUtils.encryptPassword(password));
            boolean regFlag = userService.registerUser(sysUser);
            if (!regFlag)
            {
                msg = "注册失败,请联系系统管理人员";
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            throw new CaptchaException();
        }
    }

    // 手机号正则验证
    private boolean validatePhone(String phone) {
        return phone.matches("^1[3-9]\\d{9}$");
    }

    // 邮箱正则验证
    private boolean validateEmail(String email) {
        return email.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }
}
