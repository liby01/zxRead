package com.by.zx.user.service.impl;

import com.by.zx.user.service.SmsService;
import com.by.zx.utils.HttpUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //发送验证码
    @Override
    public void sendValidateCode(String phone) {

        //1、生成验证码,随机4位
        String code = RandomStringUtils.randomNumeric(4);
        //2、将生成的验证码放到redis里面，设置过期时间
        redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
        //3、向手机号发送短信验证码
        sendMessage(phone,code);
    }

    //发送短信验证码的方法
    private void sendMessage(String phone, String code) {
    // 阿里云短信服务的基础URL
        String host = "https://gyytz.market.alicloudapi.com";
        // 短信发送的具体路径
        String path = "/sms/smsSend";
        // 请求方法
        String method = "POST";
        // 替换为你自己的AppCode
        String appcode = "f2b2e2f21e90495985f04ab6b6ae3910";

        // 设置请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + appcode);

        // 设置查询参数
        Map<String, String> querys = new HashMap<>();
        querys.put("mobile", phone); // 替换为目标手机号
        querys.put("param", "**code**:"+code+",**minute**:5");

        // smsSignId（短信前缀）和templateId（短信模板），可登录国阳云控制台自助申请。参考文档：http://help.guoyangyun.com/Problem/Qm.html
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "6f9bb40edd06454b8d911ff02770eff8");

        // 设置请求体
        Map<String, String> bodys = new HashMap<>();

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            //System.out.println(response.toString());
            // 获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
