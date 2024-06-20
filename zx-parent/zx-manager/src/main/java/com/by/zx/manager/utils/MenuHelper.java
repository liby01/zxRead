package com.by.zx.manager.utils;

import com.by.zx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {

    //封装树形菜单数据
    //递归实现封装过程
    public static List<SysMenu> buildMenuTree(List<SysMenu> sysMenuList) {
        //创建list集合，用于封装最终的数据
        List<SysMenu> menuTree = new ArrayList<SysMenu>();
        //遍历所有菜单集合
        for (SysMenu sysMenu : sysMenuList) {//sysMenu接收参数传过来的所有数据
            //找到递归操作的入口
            //条件:parent_id = 0
            if (sysMenu.getParentId() == 0) {//menu.getParentId() == 0 表示第一层菜单
                //根据第一层，找下层数据，使用递归完成
                menuTree.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return menuTree;
    }

    //递归查找下层菜单
    public static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        // SysMenu有属性 private List<SysMenu> children;封装下一层数据
        sysMenu.setChildren(new ArrayList<>());
        //递归查询
        // sysMenu的id值 和  sysMenuList中parentId相同
        for(SysMenu it:sysMenuList) {
            //判断id 和  parentid是否相同
            if(sysMenu.getId().longValue() == it.getParentId().longValue()) {
                // it就是下层数据，进行封装
                sysMenu.getChildren().add(findChildren(it,sysMenuList));
            }
        }
        return sysMenu;
    }
}
