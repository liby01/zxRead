package com.by.zx.manager.service;

import com.by.zx.model.dto.system.SysRoleDto;
import com.by.zx.model.entity.system.SysRole;
import com.github.pagehelper.PageInfo;

public interface SysRoleService {

    //角色列表的方法
    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit);
}
