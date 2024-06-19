package com.by.zx.manager.mapper;

import com.by.zx.model.dto.system.SysUserDto;
import com.by.zx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {

    //根据用户名查询数据库表sys_user表
    SysUser selectUserInfoByUserName(String userName);

    //用户条件分页查询
    List<SysUser> findByPage(SysUserDto sysUserDto);

    //用户添加
    void save(SysUser sysUser);

    //用户修改
    void update(SysUser sysUser);

    //用户删除
    void delete(Long userId);
}
