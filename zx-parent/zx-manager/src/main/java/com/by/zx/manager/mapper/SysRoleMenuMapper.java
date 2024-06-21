package com.by.zx.manager.mapper;

import com.by.zx.model.dto.system.AssginMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {

    //查询角色分配过的菜单列表
    List findSysRoleMenuByRoleId(Long roleId);

    //删除角色之前分配过的菜单数据
    void deleteByRoleId(Long roleId);

    //保存分配数据
    void doAssign(AssginMenuDto assginMenuDto);
}
