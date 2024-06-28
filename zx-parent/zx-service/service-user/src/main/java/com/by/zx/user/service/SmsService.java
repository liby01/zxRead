package com.by.zx.user.service;

public interface SmsService {

    //发送验证码
    void sendValidateCode(String phone);
}
