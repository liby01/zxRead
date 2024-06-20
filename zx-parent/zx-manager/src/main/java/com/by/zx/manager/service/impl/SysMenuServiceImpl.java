package com.by.zx.manager.service.impl;

import com.by.zx.common.exception.DiyException;
import com.by.zx.manager.mapper.SysMenuMapper;
import com.by.zx.manager.service.SysMenuService;
import com.by.zx.manager.utils.MenuHelper;
import com.by.zx.model.entity.system.SysMenu;
import com.by.zx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    SysMenuMapper sysMenuMapper;

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
}
