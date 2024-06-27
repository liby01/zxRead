package com.by.zx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.by.zx.common.exception.DiyException;
import com.by.zx.common.log.annotation.Log;
import com.by.zx.manager.mapper.SysRoleUserMapper;
import com.by.zx.manager.mapper.SysUserMapper;
import com.by.zx.manager.service.SysUserService;
import com.by.zx.model.dto.system.AssignRoleDto;
import com.by.zx.model.dto.system.LoginDto;
import com.by.zx.model.dto.system.SysUserDto;
import com.by.zx.model.entity.system.SysUser;
import com.by.zx.model.vo.common.ResultCodeEnum;
import com.by.zx.model.vo.system.LoginVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service // 标记该类为 Spring 的服务层组件
public class SysUserServiceImpl implements SysUserService {

    @Autowired // 自动注入 SysUserMapper 实例
    SysUserMapper sysUserMapper;

    @Autowired // 自动注入 RedisTemplate 实例，用于操作 Redis
    private RedisTemplate<String, String> redisTemplate;

    @Autowired // 自动注入 SysRoleUserMapper 实例
    private SysRoleUserMapper sysRoleUserMapper;

    // 用户登录
    @Override
    public LoginVo login(LoginDto loginDto) {

        // 1、获取输入的验证码和存储到 Redis 的 key 名称
        String captcha = loginDto.getCaptcha();
        String key = loginDto.getCodeKey();

        // 2、根据获取的 Redis 里面的 key，查询 Redis 里面储存的验证码
        String redisCode = redisTemplate.opsForValue().get("user:validatecode-" + key);

        // 3、比较输入的验证码和 Redis 储存验证码是否一致
        if (StrUtil.isEmpty(redisCode) || !StrUtil.equalsAnyIgnoreCase(redisCode, captcha)) {
            // 4、如果不一致，提示用户校验失败
            throw new DiyException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        // 5、如果一致，删除 Redis 里面的验证码
        redisTemplate.delete("user:validatecode-" + key);

        // 1、获取提交用户名
        String userName = loginDto.getUserName();

        // 2、根据用户名查询数据库表 sys_user 表
        SysUser sysUser = sysUserMapper.selectUserInfoByUserName(userName);

        // 3、如果根据用户名查不到对应信息，用户不存在，返回错误信息
        if (sysUser == null) {
            throw new DiyException(ResultCodeEnum.LOGIN_ERRORIsNull);
        }

        // 4、如果根据用户名查询到用户信息，用户存在
        // 5、获取输入的密码，比较输入的密码跟数据库的密码是否一致
        String sysUser_password = sysUser.getPassword();
        String input_password = loginDto.getPassword();
        // 把输入的密码进行 MD5 加密，再比较数据库密码
        input_password = DigestUtils.md5DigestAsHex(input_password.getBytes());
        // 比较
        if (!input_password.equals(sysUser_password)) {
            throw new DiyException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 6、如果密码一致，登录成功，不一致登录失败
        // 7、登录成功，生成用户唯一标识 token
        String token = UUID.randomUUID().toString().replaceAll("-", ""); // 生成的 UUID 去掉 "-"

        // 8、把登录成功的用户信息放到 Redis 里面
        String userJson = JSON.toJSONString(sysUser); // 将用户信息（对象）转换成 JSON 字符串
        redisTemplate.opsForValue().set("user:login-" + token, userJson, 1, TimeUnit.DAYS); // 设置 key、value、超时时间和单位

        // 9、返回 LoginVo 对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);

        return loginVo;
    }

    // 获取当前登录用户信息
    @Override
    public SysUser getUserInfo(String token) {
        // 获取 Redis 里面用户登录的信息
        String userJson = redisTemplate.opsForValue().get("user:login-" + token); // 返回 JSON 格式的字符串
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class); // 将 JSON 字符串转换成 SysUser 对象
        return sysUser;
    }

    // 用户退出
    @Override
    public void logout(String token) {
        // 根据 key 删除 Redis 里面存储的用户信息
        redisTemplate.delete("user:login-" + token);
    }

    // 用户条件分页查询
    @Override
    public PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize); // 设置分页参数
        List<SysUser> list = sysUserMapper.findByPage(sysUserDto); // 根据条件查询用户列表
        PageInfo<SysUser> sysUserPageInfo = new PageInfo<>(list); // 将结果封装到 PageInfo 对象中
        return sysUserPageInfo;
    }

    // 用户添加
    @Override
    public void saveSysUser(SysUser sysUser) {
        // 1、判断用户名不能重复
        String userName = sysUser.getUserName();
        SysUser dbSysUser = sysUserMapper.selectUserInfoByUserName(userName);
        if (dbSysUser != null) {
            throw new DiyException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        // 2、输入密码进行加密
        String password = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        sysUser.setPassword(password);
        // 设置 status 的值，1 表示可用，0 表示不可用
        sysUser.setStatus(1);
        sysUserMapper.save(sysUser); // 保存用户信息
    }

    // 用户修改
    @Override
    public void updateSysUser(SysUser sysUser) {
        // 判断用户名不能重复
        String userName = sysUser.getUserName();
        SysUser dbSysUser = sysUserMapper.selectUserInfoByUserName(userName);
        if (dbSysUser != null) {
            throw new DiyException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        sysUserMapper.update(sysUser); // 更新用户信息
    }

    // 用户删除
    @Override
    public void deleteById(Long userId) {
        sysUserMapper.delete(userId); // 删除用户信息
    }

    // 用户分配角色
    @Log(title = "用户分配角色",businessType = 0)
    @Transactional//添加事务
    @Override
    public void doAssign(AssignRoleDto assignRoleDto) {
        // 1、根据 userId 删除用户之前分配的角色数据
        sysRoleUserMapper.deleteByUserId(assignRoleDto.getUserId());

        // 2、重新分配角色
        List<Long> roleIdList = assignRoleDto.getRoleIdList(); // 遍历得到的每个角色 ID
        for (Long roleId : roleIdList) {
            sysRoleUserMapper.doAssign(assignRoleDto.getUserId(), roleId); // 分配角色
        }
    }
}
