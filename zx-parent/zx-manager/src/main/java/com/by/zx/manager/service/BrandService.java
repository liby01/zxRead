package com.by.zx.manager.service;

import com.by.zx.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {

    //列表-分页
    PageInfo<Brand> findByPage(Integer page, Integer limit);

    //添加品牌
    void save(Brand brand);

    //修改品牌
    void update(Brand brand);

    //删除品牌
    void deleteById(Long id);

    //查询所有品牌
    List<Brand> findAll();
}
