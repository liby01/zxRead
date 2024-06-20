package com.by.zx.manager.mapper;

import com.by.zx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper {

    //查询所有菜单
    List<SysMenu> findAll();

    //菜单添加
    void save(SysMenu sysMenu);

    //菜单修改
    void update(SysMenu sysMenu);

    //菜单删除
    void delete(Long id);

    //根据当前菜单id，查询是否包含子菜单
    int selectCountById(Long id);
}
