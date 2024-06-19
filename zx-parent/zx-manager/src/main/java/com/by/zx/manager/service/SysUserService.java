package com.by.zx.manager.service;


import com.by.zx.model.dto.system.LoginDto;
import com.by.zx.model.dto.system.SysUserDto;
import com.by.zx.model.entity.system.SysUser;
import com.by.zx.model.vo.system.LoginVo;
import com.github.pagehelper.PageInfo;

public interface SysUserService {

    //用户登录
    LoginVo login(LoginDto loginDto);

    //获取当前登录用户信息
    SysUser getUserInfo(String token);

    //用户退出
    void logout(String token);

    //用户条件分页查询
    PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize);

    //用户添加
    void saveSysUser(SysUser sysUser);

    //用户修改
    void updateSysUser(SysUser sysUser);

    //用户删除
    void deleteById(Long userId);
}
