package com.by.zx.manager.mapper;

import com.by.zx.model.dto.system.SysRoleDto;
import com.by.zx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    //按角色查询
    List<SysRole> findByPage(SysRoleDto sysRoleDto);
}
