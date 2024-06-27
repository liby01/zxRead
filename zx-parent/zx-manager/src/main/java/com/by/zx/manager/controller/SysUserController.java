package com.by.zx.manager.controller;

import com.by.zx.common.log.annotation.Log;
import com.by.zx.manager.service.SysUserService;
import com.by.zx.model.dto.system.AssignRoleDto;
import com.by.zx.model.dto.system.SysUserDto;
import com.by.zx.model.entity.system.SysUser;
import com.by.zx.model.vo.common.Result;
import com.by.zx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/sysUser")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;

    //1 用户条件分页查询
    @GetMapping(value = "/findByPage/{pageNum}/{pageSize}")
    @Operation(summary = "用户条件分页查询")
    public Result<PageInfo> findByPage(SysUserDto sysUserDto ,
                                                @PathVariable(value = "pageNum") Integer pageNum ,
                                                @PathVariable(value = "pageSize") Integer pageSize) {
        PageInfo<SysUser> pageInfo = sysUserService.findByPage(sysUserDto , pageNum , pageSize) ;
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

    //2 用户添加
    @Log(title = "用户管理:添加",businessType = 1)
    @PostMapping(value = "/saveSysUser")
    @Operation(summary = "用户添加")
    public Result saveSysUser(@RequestBody SysUser sysUser) {
        sysUserService.saveSysUser(sysUser);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    //3 用户修改
    @PutMapping(value = "/updateSysUser")
    @Operation(summary = "用户修改")
    public Result updateSysUser(@RequestBody SysUser sysUser) {
        sysUserService.updateSysUser(sysUser);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    //4 用户删除
    @DeleteMapping(value = "/deleteById/{userId}")
    @Operation(summary = "用户删除")
    public Result deleteById(@PathVariable Long userId) {
        sysUserService.deleteById(userId);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    //用户分配角色
    //保存分配数据
    @Log(title = "用户管理:为用户分配角色",businessType = 1)
    @Operation(summary = "用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssignRoleDto assignRoleDto) {
        sysUserService.doAssign(assignRoleDto);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
}
