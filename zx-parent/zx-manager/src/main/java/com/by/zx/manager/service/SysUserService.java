package com.by.zx.manager.service;


import com.by.zx.model.dto.system.LoginDto;
import com.by.zx.model.entity.system.SysUser;
import com.by.zx.model.vo.system.LoginVo;

public interface SysUserService {

    //用户登录
    LoginVo login(LoginDto loginDto);

    //获取当前登录用户信息
    SysUser getUserInfo(String token);

    //用户退出
    void logout(String token);

}
