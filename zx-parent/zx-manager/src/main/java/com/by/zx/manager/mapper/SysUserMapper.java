package com.by.zx.manager.mapper;

import com.by.zx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {

    //根据用户名查询数据库表sys_user表
    SysUser selectUserInfoByUserName(String userName);
}
