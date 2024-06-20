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

@Tag(name = "菜单管理接口")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    //菜单列表
    @Operation(summary = "获取所有菜单")
    @GetMapping("/findNodes")
    public Result findNodes(){
        //查询所有数据
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
    
    //菜单添加
    @Operation(summary = "菜单添加")
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    
    //菜单修改
    @Operation(summary = "菜单修改")
    @PutMapping("/update")
    public Result update(@RequestBody SysMenu sysMenu){
        sysMenuService.update(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    
    //菜单删除
    @Operation(summary = "菜单删除")
    @DeleteMapping("/removeById/{id}")
    public Result removeById(@PathVariable() Long id){
        sysMenuService.removeById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
