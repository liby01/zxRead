package com.by.zx.manager.service;

import com.by.zx.model.entity.system.SysMenu;
import com.by.zx.model.vo.system.SysMenuVo;

import java.util.List;

public interface SysMenuService {

    //查询所有菜单数据
    List<SysMenu> findNodes();

    //菜单添加
    void save(SysMenu sysMenu);

    //菜单修改
    void update(SysMenu sysMenu);

    //菜单删除
    void removeById(Long id);

    //查询用户可以操作的菜单
    List<SysMenuVo> findMenusByUserId();
}
