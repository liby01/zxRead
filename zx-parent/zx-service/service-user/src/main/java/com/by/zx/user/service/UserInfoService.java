package com.by.zx.user.service;

import com.by.zx.model.dto.h5.UserLoginDto;
import com.by.zx.model.dto.h5.UserRegisterDto;
import com.by.zx.model.vo.h5.UserInfoVo;

public interface UserInfoService {

    //会员注册
    void register(UserRegisterDto userRegisterDto);

    //用户登录
    String login(UserLoginDto userLoginDto);

    //获取当前登录用户信息
    UserInfoVo getCurrentUserInfo(String token);
}
