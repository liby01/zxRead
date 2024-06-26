package com.by.zx.manager.service.impl;

import com.by.zx.manager.mapper.SysRoleMapper;
import com.by.zx.manager.mapper.SysRoleUserMapper;
import com.by.zx.manager.service.SysRoleService;
import com.by.zx.model.dto.system.SysRoleDto;
import com.by.zx.model.entity.system.SysRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    //角色列表的方法
    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
        //设置分页参数
        PageHelper.startPage(current,limit);
        //根据条件查询所有数据
        List<SysRole> list = sysRoleMapper.findByPage(sysRoleDto);
        //封装pageInfo对象
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //新增角色
    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.save(sysRole);
    }

    //修改角色
    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
    }

    //删除角色
    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.delete(roleId);
    }

    //查询所有角色
    @Override
    public Map<String, Object> findAll(Long userId) {
        //1、查询所有角色
        List roleList = sysRoleMapper.findAllRoles();

        //2、查询用户的所有角色
        List<Long> userAllRoleId = sysRoleUserMapper.selectRoleIdsByUserId(userId);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("allRolesList", roleList);
        map.put("sysUserRoles", userAllRoleId);
        return map;
    }


}
