package com.by.zx.user.controller;

import com.by.zx.model.vo.common.Result;
import com.by.zx.model.vo.common.ResultCodeEnum;
import com.by.zx.user.service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "短信调用")
@RestController // 标记这是一个 REST 控制器类，使其成为 Spring 容器中的一个 Bean
@RequestMapping("api/user/sms") // 映射请求路径前缀为 "api/user/sms"
public class SmsController {

    @Autowired // 自动注入 SmsService 实例
    private SmsService smsService;

    @Operation(summary = "获取验证码")
    @GetMapping(value = "/sendCode/{phone}") // 映射 HTTP GET 请求到 "/sendCode/{phone}" 路径
    public Result sendValidateCode(@PathVariable String phone) {
        // 调用 SmsService 的 sendValidateCode 方法发送验证码
        smsService.sendValidateCode(phone);
        // 返回操作结果，封装到 Result 对象中，设置返回码为成功
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}

