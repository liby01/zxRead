package com.by.zx.manager.controller;

import com.by.zx.manager.service.SysUserService;
import com.by.zx.manager.service.ValidateCodeService;
import com.by.zx.model.dto.system.LoginDto;
import com.by.zx.model.entity.system.SysUser;
import com.by.zx.model.vo.common.Result;
import com.by.zx.model.vo.common.ResultCodeEnum;
import com.by.zx.model.vo.system.LoginVo;
import com.by.zx.model.vo.system.ValidateCodeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "登录接口")
@RestController
@RequestMapping("/admin/system/index")
//@CrossOrigin(allowCredentials = "true",originPatterns = "*",allowedHeaders = "*")可解决跨域访问，不推荐
// 推荐添加配置类WebMvcConfiguration解决跨域请求
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;

    //生成图片验证码
    @Operation(summary = "获取验证码key、value")
    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo , ResultCodeEnum.SUCCESS) ;
    }

    //用户登录
    @Operation(summary = "登录方法")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) { //传入LoginDto类型，返回Result类型格式
        LoginVo loginVo = sysUserService.login(loginDto) ;//调用sysUserService.login()方法
        return Result.build(loginVo , ResultCodeEnum.SUCCESS) ;
    }

    //获取当前登录用户信息
    @Operation(summary = "获取当前登录用户信息")
    @GetMapping(value = "/getUserInfo")
    public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token) {//getUserInfo(HttpServerRequest request)
        //1、从请求头获取token
        //String token = request.getHeader("token");
        //2、根据token查询redis获取用户信息
        SysUser sysUser = sysUserService.getUserInfo(token);
        //3、返回用户信息
        return Result.build(sysUser,ResultCodeEnum.SUCCESS);
    }
//    @Operation(summary = "获取当前登录用户信息")
//    @GetMapping(value = "/getUserInfo")
//    public Result getUserInfo() {
//        return Result.build(AuthContextUtil.get(),ResultCodeEnum.SUCCESS);
//    }

    //用户退出
    @Operation(summary = "用户退出")
    @GetMapping("/logout")
    public Result logout(@RequestHeader(name = "token") String token) {
        //删除redis里面的token
        sysUserService.logout(token);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
