package com.recMall.common.utils;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SmsUtil {
    private static final Logger log = LoggerFactory.getLogger(SmsUtil.class);
    // 从配置读取（示例值需要替换）
    private static final String ACCESS_KEY_ID = "LTAI5tP34uU7HUZ3yQto2d2Y";
    private static final String ACCESS_SECRET = "D2PrNJ5vUOnT4Et7fmyJLqcMd3aGsc";
    private static final String SIGN_NAME = "阿里云短信测试";
    private static final String TEMPLATE_CODE = "SMS_154950909";
    private static final String REGION_ID = "cn-hangzhou";
    private static final String PRODUCT = "Dysmsapi";
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";


    public static void sendSms(String phone, String code) throws ClientException {
        try {
            IClientProfile profile = DefaultProfile.getProfile(REGION_ID, ACCESS_KEY_ID, ACCESS_SECRET);

            DefaultProfile.addEndpoint(REGION_ID, REGION_ID, PRODUCT, DOMAIN);

            IAcsClient acsClient = new DefaultAcsClient(profile);

            SendSmsRequest request = new SendSmsRequest();

            request.setMethod(MethodType.POST);

            // 手机号可以单个也可以多个（多个用逗号隔开，如：15*******13,13*******27,17*******56）
            request.setPhoneNumbers(phone);

            request.setSignName(SIGN_NAME);

            request.setTemplateCode(TEMPLATE_CODE);


            request.setTemplateParam("{\"phone\":\""+ phone +"\",\"code\":\""+ code +"\"}");

            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if ((sendSmsResponse.getCode() != null) && (sendSmsResponse.getCode().equals("OK"))) {
                log.info("发送成功,code:" + sendSmsResponse.getCode());
            } else {
                log.info("发送失败,code:" + sendSmsResponse.getCode());
            }
        } catch (ClientException e) {
            log.error("发送失败,系统错误！", e);
        }
    }
}