package com.by.zx.manager.service.impl;

import com.by.zx.common.exception.DiyException;
import com.by.zx.manager.mapper.SysMenuMapper;
import com.by.zx.manager.mapper.SysRoleMenuMapper;
import com.by.zx.manager.mapper.SysRoleUserMapper;
import com.by.zx.manager.service.SysMenuService;
import com.by.zx.manager.utils.MenuHelper;
import com.by.zx.model.entity.system.SysMenu;
import com.by.zx.model.entity.system.SysUser;
import com.by.zx.model.vo.common.ResultCodeEnum;
import com.by.zx.model.vo.system.SysMenuVo;
import com.by.zx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    //查询所有菜单数据
    @Override
    public List<SysMenu> findNodes() {
        //1 查询所有菜单，返回list集合
        List<SysMenu> sysMenuList = sysMenuMapper.findAll();
        if(CollectionUtils.isEmpty(sysMenuList)){
            return null;
        }
        //2 调用工具类，将返回的list集合封装前端需要的数据格式
        List<SysMenu> treeList = MenuHelper.buildMenuTree(sysMenuList);

        //返回数据
        return treeList;
    }

    //菜单添加
    @Override
    public void save(SysMenu sysMenu) {
        sysMenuMapper.save(sysMenu);

        //新添加子菜单，把子菜单的父菜单ifHalf半开状态
        updateSysRoleMenu(sysMenu);
    }
    //新添加子菜单，把子菜单的父菜单ifHalf半开状态 1
    private void updateSysRoleMenu(SysMenu sysMenu) {
        //获取当前添加菜单的父菜单
        SysMenu parentMenu = sysMenuMapper.selectParentMenu(sysMenu.getParentId());
        if(parentMenu != null){
            //把父菜单isHalf半开状态 1
            sysRoleMenuMapper.updateSysRoleMenuIsHalf(parentMenu.getId());
            //递归调用
            updateSysRoleMenu(parentMenu);
        }
    }

    //菜单修改
    @Override
    public void update(SysMenu sysMenu) {
        sysMenuMapper.update(sysMenu);
    }

    //菜单删除
    @Override
    public void removeById(Long id) {
        //根据当前菜单id，查询是否包含子菜单
        int count = sysMenuMapper.selectCountById(id);

        //判断，count大于0，包含子菜单
        if (count > 0) {
            throw new DiyException(ResultCodeEnum.NODE_ERROR);
        }

        //count等于0，直接删除
        sysMenuMapper.delete(id);
    }

    // 查询用户可以操作的菜单
    @Override
    public List<SysMenuVo> findMenusByUserId() {
        // 获取当前登录的用户id
        SysUser sysUser = AuthContextUtil.get(); // 获取当前登录用户信息
        Long userId = sysUser.getId(); // 获取用户ID

        // 根据userId查询可以操作的菜单
        List<SysMenu> sysMenuList = sysMenuMapper.findMenusByUserId(userId); // 从数据库中查询用户可以操作的菜单列表

        // 封装要求的数据格式
        List<SysMenu> sysMenuTreeList = MenuHelper.buildMenuTree(sysMenuList); // 构建菜单树结构
        List<SysMenuVo> sysMenuVos = this.buildMenus(sysMenuTreeList); // 将菜单树结构转换为前端需要的格式
        return sysMenuVos; // 返回封装后的菜单列表
    }

    // 将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {
        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>(); // 创建一个空的SysMenuVo列表

        for (SysMenu sysMenu : menus) { // 遍历菜单列表
            SysMenuVo sysMenuVo = new SysMenuVo(); // 创建一个新的SysMenuVo对象
            sysMenuVo.setTitle(sysMenu.getTitle()); // 设置菜单标题
            sysMenuVo.setName(sysMenu.getComponent()); // 设置菜单组件名称

            List<SysMenu> children = sysMenu.getChildren(); // 获取子菜单列表
            if (!CollectionUtils.isEmpty(children)) { // 如果子菜单列表不为空
                sysMenuVo.setChildren(buildMenus(children)); // 递归调用，将子菜单列表转换成SysMenuVo对象列表
            }
            sysMenuVoList.add(sysMenuVo); // 将转换后的SysMenuVo对象添加到列表中
        }
        return sysMenuVoList; // 返回转换后的SysMenuVo列表
    }

}
