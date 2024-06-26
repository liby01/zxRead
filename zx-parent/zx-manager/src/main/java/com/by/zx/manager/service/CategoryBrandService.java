package com.by.zx.manager.service;

import com.by.zx.model.dto.product.CategoryBrandDto;
import com.by.zx.model.entity.product.Brand;
import com.by.zx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryBrandService {
    //分类品牌条件分页查询
    PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

    //添加
    void save(CategoryBrand categoryBrand);

    //修改
    void updateById(CategoryBrand categoryBrand);

    //删除
    void deleteById(Long id);

    //根据分类Id查询所有品牌数据
    List<Brand> findBranByCategoryId(Long categoryId);
}
