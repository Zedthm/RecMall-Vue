package com.recMall.common.core.domain.model;

/**
 * 用户注册对象
 * 
 * @author zedthm
 */
public class RegisterBody extends LoginBody
{
    private String registerType; // email/phonenumber
    private String email;
    private String phonenumber;
    private String smsCode;  // 短信验证码
    private String emailCode; // 邮件验证码

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }
}
