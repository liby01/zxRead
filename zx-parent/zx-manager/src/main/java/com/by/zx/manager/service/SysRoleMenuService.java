package com.by.zx.manager.service;

import com.by.zx.model.dto.system.AssginMenuDto;

import java.util.Map;

public interface SysRoleMenuService {

    //查询所有菜单和角色分配过的菜单列表
    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);


    //保存角色分配的菜单数据
    void doAssign(AssginMenuDto assginMenuDto);
}
