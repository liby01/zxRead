package com.by.zx.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.by.zx.common.exception.DiyException;
import com.by.zx.model.dto.h5.UserLoginDto;
import com.by.zx.model.dto.h5.UserRegisterDto;
import com.by.zx.model.entity.user.UserInfo;
import com.by.zx.model.vo.common.ResultCodeEnum;
import com.by.zx.model.vo.h5.UserInfoVo;
import com.by.zx.user.mapper.UserInfoMapper;
import com.by.zx.user.service.UserInfoService;
import com.by.zx.utils.AuthContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //会员注册
    @Override
    public void register(UserRegisterDto userRegisterDto) {
        
        //1 userRegisterDto获取数据
        String username = userRegisterDto.getUsername();//获取手机号
        String password = userRegisterDto.getPassword();//获取用户输入的密码
        String nickName = userRegisterDto.getNickName();//获取用户输入的验证码
        String code = userRegisterDto.getCode();

        //2 验证码校验
        //2.1 从redis获取发送验证码
        String redisCode = redisTemplate.opsForValue().get(username);
        //2.2 获取输入的验证码，进行比对
        if(!redisCode.equals(code)) {
            throw new DiyException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //3 校验用户名不能重复
        UserInfo userInfo = userInfoMapper.selectByUsername(username);
        if(userInfo != null) { //存在相同用户名
            throw new DiyException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //4 封装添加数据，调用方法添加数据库
        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("https://b0.bdstatic.com/37564ecdec89a45ec5ab97ee60bb55d6.jpg@h_1280");

        //保存新增用户
        userInfoMapper.save(userInfo);

        //5 从redis删除发送的验证码
        redisTemplate.delete(username);
    }

    //登录
    @Override
    public String login(UserLoginDto userLoginDto) {
        //1 dto获取用户名和密码
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        //2 根据用户名查询数据库，得到用户信息
        UserInfo userInfo = userInfoMapper.selectByUsername(username);
        if(userInfo == null) {
            throw new DiyException(ResultCodeEnum.LOGIN_ERROR);
        }

        //3 比较密码是否一致
        String database_password = userInfo.getPassword();
        String md5_password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!database_password.equals(md5_password)) {
            throw new DiyException(ResultCodeEnum.LOGIN_ERROR);
        }

        //4 校验用户是否禁用
        if(userInfo.getStatus() == 0) {
            throw new DiyException(ResultCodeEnum.ACCOUNT_STOP);
        }

        //5 生成token
        String token = UUID.randomUUID().toString().replaceAll("-", "");

        //6 把用户信息放到redis里面
        redisTemplate.opsForValue().set("user:zx:"+token,
                JSON.toJSONString(userInfo),
                30, TimeUnit.DAYS);

        //7 返回token
        return token;
    }

    //获取当前登录用户信息
    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
        //从redis根据token获取用户信息
//        String userJson = redisTemplate.opsForValue().get("user:zx:" + token);
//        if(!StringUtils.hasText(userJson)) {
//            throw new DiyException(ResultCodeEnum.LOGIN_AUTH);
//        }
//        UserInfo userInfo = JSON.parseObject(userJson, UserInfo.class);

        //从ThreadLocal获取用户信息
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        //userInfo  ---- UserInfoVo
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo,userInfoVo);
        return userInfoVo;
    }
}
