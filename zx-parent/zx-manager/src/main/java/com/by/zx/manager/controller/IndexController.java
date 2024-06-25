package com.by.zx.manager.controller;

import com.by.zx.manager.service.SysMenuService;
import com.by.zx.manager.service.SysUserService;
import com.by.zx.manager.service.ValidateCodeService;
import com.by.zx.model.dto.system.LoginDto;
import com.by.zx.model.entity.system.SysUser;
import com.by.zx.model.vo.common.Result;
import com.by.zx.model.vo.common.ResultCodeEnum;
import com.by.zx.model.vo.system.LoginVo;
import com.by.zx.model.vo.system.SysMenuVo;
import com.by.zx.model.vo.system.ValidateCodeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "登录接口") // Swagger注解，用于API文档生成，标记这个类为登录接口相关
@RestController
@RequestMapping("/admin/system/index")// 路径映射，定义了该控制器中所有方法的基础URL路径为"/admin/system/index"
//@CrossOrigin(allowCredentials = "true",originPatterns = "*",allowedHeaders = "*")可解决跨域访问，不推荐
// 推荐添加配置类WebMvcConfiguration解决跨域请求
public class IndexController {

    @Autowired
    private SysUserService sysUserService; // 自动注入SysUserService实例

    @Autowired
    private ValidateCodeService validateCodeService; // 自动注入ValidateCodeService实例

    @Autowired
    private SysMenuService sysMenuService; // 自动注入SysMenuService实例

    //生成图片验证码
    @Operation(summary = "获取验证码key、value")
    @GetMapping(value = "/generateValidateCode")
    // 定义HTTP GET方法，映射路径为"/admin/system/index/generateValidateCode"，用于生成验证码
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode(); // 调用验证码服务生成验证码
        return Result.build(validateCodeVo , ResultCodeEnum.SUCCESS) ; // 返回带有验证码信息的成功结果
    }

    //用户登录
    @Operation(summary = "登录方法")
    @PostMapping(value = "/login")
    // 定义HTTP POST方法，映射路径为"/admin/system/index/login"，用于用户登录
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) { // 接收登录DTO，并返回登录VO
        LoginVo loginVo = sysUserService.login(loginDto); // 调用用户服务进行登录操作
        return Result.build(loginVo , ResultCodeEnum.SUCCESS) ; // 返回登录结果
    }

    //获取当前登录用户信息
    @Operation(summary = "获取当前登录用户信息")
    @GetMapping(value = "/getUserInfo")
    // 定义HTTP GET方法，映射路径为"/admin/system/index/getUserInfo"，用于获取当前登录用户信息
    public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token) {
        // 接收请求头中的token参数，用于验证用户身份
        SysUser sysUser = sysUserService.getUserInfo(token); // 调用用户服务获取当前登录用户信息
        return Result.build(sysUser,ResultCodeEnum.SUCCESS); // 返回当前登录用户信息的结果
    }

    //用户退出
    @Operation(summary = "用户退出")
    @GetMapping("/logout")
    // 定义HTTP GET方法，映射路径为"/admin/system/index/logout"，用于用户退出登录
    public Result logout(@RequestHeader(name = "token") String token) {
        // 接收请求头中的token参数，用于从Redis中删除用户的登录信息
        sysUserService.logout(token); // 调用用户服务执行用户退出操作
        return Result.build(null,ResultCodeEnum.SUCCESS); // 返回退出成功的结果
    }

    //查询用户可以操作的菜单
    @Operation(summary = "查询用户可以操作的菜单")
    @GetMapping("/menus")
    public Result menus(){
        List<SysMenuVo> list =sysMenuService.findMenusByUserId();
        return Result.build(list,ResultCodeEnum.SUCCESS);
    }
}
