package com.recMall.web.controller.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.code.kaptcha.Producer;
import com.recMall.common.config.RecMallConfig;
import com.recMall.common.constant.CacheConstants;
import com.recMall.common.constant.Constants;
import com.recMall.common.core.domain.AjaxResult;
import com.recMall.common.core.redis.RedisCache;
import com.recMall.common.utils.sign.Base64;
import com.recMall.common.utils.uuid.IdUtils;
import com.recMall.system.service.ISysConfigService;

/**
 * 验证码操作处理
 * 
 * @author zedthm
 */
@Api(tags = "系统认证模块", value = "CaptchaController")
@RestController
public class CaptchaController
{
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;
    
    @Autowired
    private ISysConfigService configService;
    /**
     * 生成验证码
     */
    @ApiOperation(
            value = "获取验证码图片",
            notes = "生成图形验证码并返回Base64编码图片，需前端携带uuid进行验证",
            response = AjaxResult.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "验证码生成成功",
                    examples = @Example({
                            @ExampleProperty(value =
                                    "{'captchaEnabled': true, 'uuid': 'd3d944...', 'img': 'data:image/jpeg;base64,/9j/4AAQ...'}",
                                    mediaType = "application/json")
                    })),
            @ApiResponse(code = 500, message = "验证码生成失败（如图片写入异常）")
    })
    @GetMapping("/captchaImage")
    public AjaxResult getCode(HttpServletResponse response) throws IOException
    {
        AjaxResult ajax = AjaxResult.success();
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        ajax.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled)
        {
            return ajax;
        }

        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        String captchaType = RecMallConfig.getCaptchaType();
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return AjaxResult.error(e.getMessage());
        }

        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }
}
