package com.by.zx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.by.zx.common.exception.DiyException;
import com.by.zx.manager.mapper.SysUserMapper;
import com.by.zx.manager.service.SysUserService;
import com.by.zx.model.dto.system.LoginDto;
import com.by.zx.model.entity.system.SysUser;
import com.by.zx.model.vo.common.ResultCodeEnum;
import com.by.zx.model.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {


    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    //用户登录
    @Override
    public LoginVo login(LoginDto loginDto) {

        //1、获取输入的验证码和存储到redis的key名称
        String captcha = loginDto.getCaptcha();
        String key = loginDto.getCodeKey();

        //2、根据获取的redis里面的key，查询redis里面储存的验证码
        //set("user:validatecode"+key)
        String redisCode = redisTemplate.opsForValue().get("user:validatecode-"+key);

        //3、比较输入的验证码和redis储存验证码是否一致
        if (StrUtil.isEmpty(redisCode) || !StrUtil.equalsAnyIgnoreCase(redisCode,captcha)){//equalsAnyIgnoreCase:不区分大小写
            //4、如果不一致，提示用户校验失败
          throw new DiyException(ResultCodeEnum.VALIDATECODE_ERROR);
        };

        //5、如果一致，删除redis里面验证码
        redisTemplate.delete("user:validatecode-"+key);

        //1、获取提交用户名，loginDto获取到
        String userName = loginDto.getUserName();

        //2、根据用户名查询数据库表sys_user表
        SysUser sysUser = sysUserMapper.selectUserInfoByUserName(userName);

        //3、如果根据用户名查不到对应信息，用户不存在，返回错误信息
        if (sysUser == null){
            throw new DiyException(ResultCodeEnum.LOGIN_ERRORIsNull);
        }

        //System.err.println("\n"+"查询数据库成功"+sysUser.getUserName());

        //4、如果根据用户名查询到用户信息，用户存在
        //5、获取输入的密码，比较输入的密码跟数据库的密码是否一致
        String sysUser_password = sysUser.getPassword();
        String input_password = loginDto.getPassword();
        //把输入的密码进行md5加密，再比较数据库密码
        input_password =  DigestUtils.md5DigestAsHex(input_password.getBytes());
        //比较
        if (!input_password.equals(sysUser_password)){
            throw new DiyException(ResultCodeEnum.LOGIN_ERROR);
        }

        //6、如果密码一致，登录成功，不一致登录失败
        //7、登录成功，生成用户唯一标识token
        String token = UUID.randomUUID().toString().replaceAll("-","");//生成的uuid去掉"-"

        //8、把登录成功的用户信息放到redis里面
        //key:token 、 value:用户信息
        String userJson = JSON.toJSONString(sysUser);//将用户信息（对象）转换成JSON的字符串
        redisTemplate.opsForValue().set("user:login-"+token,userJson,10,TimeUnit.MINUTES);//key、value、超时时间、单位

        //9、返回LoginVo对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);

        return loginVo;
    }

    //获取当前登录用户信息
    @Override
    public SysUser getUserInfo(String token) {
        //获取redis里面用户登录的信息
        String userJson = redisTemplate.opsForValue().get("user:login-"+token);//返回的json格式的字符串
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    //用户退出
    @Override
    public void logout(String token) {
        //根据key将redis里面的存储的用户
        redisTemplate.delete("user:login-"+token);
    }

}