package com.by.zx.manager.service.impl;

import com.by.zx.manager.mapper.SysRoleMenuMapper;
import com.by.zx.manager.service.SysMenuService;
import com.by.zx.manager.service.SysRoleMenuService;
import com.by.zx.model.dto.system.AssginMenuDto;
import com.by.zx.model.entity.system.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysMenuService sysMenuService;

    //查询所有菜单 和 角色分配过的菜单列表
    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {
        Map<String, Object> result = new HashMap<String, Object>();
        //查询所有菜单
        List<SysMenu> sysMenuList = sysMenuService.findNodes();
        //查询角色分配过的菜单列表
        List roleMenuIds = sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId);
        result.put("sysMenuList", sysMenuList);
        result.put("roleMenuIds", roleMenuIds);
        return result;
    }

    //保存角色分配的菜单数据
    @Override
    public void doAssign(AssginMenuDto assginMenuDto) {
        //删除角色之前分配过的菜单数据
        sysRoleMenuMapper.deleteByRoleId(assginMenuDto.getRoleId());

        //保存分配数据
        List<Map<String, Number>> menuInfo = assginMenuDto.getMenuIdList();
        if (menuInfo!=null&&menuInfo.size()>0){
            sysRoleMenuMapper.doAssign(assginMenuDto);
        }
    }
}
