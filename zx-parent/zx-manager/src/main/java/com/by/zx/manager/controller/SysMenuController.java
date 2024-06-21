package com.by.zx.manager.controller;

import com.by.zx.manager.service.SysMenuService;
import com.by.zx.model.entity.system.SysMenu;
import com.by.zx.model.vo.common.Result;
import com.by.zx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "菜单管理接口") // Swagger 注解，用于标记此类为菜单管理接口
@RestController // 标记该类为 Spring MVC 的 REST 控制器
@RequestMapping(value = "/admin/system/sysMenu") // 设置请求路径前缀为 "/admin/system/sysMenu"
public class SysMenuController {

    @Autowired // 自动注入 SysMenuService 实例
    private SysMenuService sysMenuService;

    // 菜单列表
    @Operation(summary = "获取所有菜单") // Swagger 注解，描述该操作的功能
    @GetMapping("/findNodes") // 处理 GET 请求，路径为 "/findNodes"
    public Result findNodes(){
        // 查询所有数据
        List<SysMenu> list = sysMenuService.findNodes(); // 调用服务层方法获取所有菜单节点
        return Result.build(list, ResultCodeEnum.SUCCESS); // 构建并返回结果对象，包含菜单列表和成功的状态码
    }

    // 菜单添加
    @Operation(summary = "菜单添加") // Swagger 注解，描述该操作的功能
    @PostMapping("/save") // 处理 POST 请求，路径为 "/save"
    public Result save(@RequestBody SysMenu sysMenu){ // 从请求体中获取 SysMenu 对象
        sysMenuService.save(sysMenu); // 调用服务层方法保存菜单
        return Result.build(null, ResultCodeEnum.SUCCESS); // 构建并返回结果对象，包含成功的状态码
    }

    // 菜单修改
    @Operation(summary = "菜单修改") // Swagger 注解，描述该操作的功能
    @PutMapping("/update") // 处理 PUT 请求，路径为 "/update"
    public Result update(@RequestBody SysMenu sysMenu){ // 从请求体中获取 SysMenu 对象
        sysMenuService.update(sysMenu); // 调用服务层方法更新菜单
        return Result.build(null, ResultCodeEnum.SUCCESS); // 构建并返回结果对象，包含成功的状态码
    }

    // 菜单删除
    @Operation(summary = "菜单删除") // Swagger 注解，描述该操作的功能
    @DeleteMapping("/removeById/{id}") // 处理 DELETE 请求，路径为 "/removeById/{id}"
    public Result removeById(@PathVariable() Long id){ // 从路径变量中获取菜单 ID
        sysMenuService.removeById(id); // 调用服务层方法删除菜单
        return Result.build(null, ResultCodeEnum.SUCCESS); // 构建并返回结果对象，包含成功的状态码
    }
}
