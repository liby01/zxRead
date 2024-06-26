package com.by.zx.manager.service.impl;

import com.by.zx.manager.mapper.CategoryBrandMapper;
import com.by.zx.manager.service.CategoryBrandService;
import com.by.zx.model.dto.product.CategoryBrandDto;
import com.by.zx.model.entity.product.Brand;
import com.by.zx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    //分类品牌条件分页查询
    @Override
    public PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto) {
        PageHelper.startPage(page,limit);
        List<CategoryBrand> list = categoryBrandMapper.findByPage(categoryBrandDto);
        PageInfo<CategoryBrand> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    //添加
    @Override
    public void save(CategoryBrand categoryBrand) {
        categoryBrandMapper.save(categoryBrand);
    }

    //修改
    @Override
    public void updateById(CategoryBrand categoryBrand) {
        categoryBrandMapper.updateById(categoryBrand);
    }

    //删除
    @Override
    public void deleteById(Long id) {
        categoryBrandMapper.deleteById(id);
    }

    //根据分类Id查询所有品牌数据
    @Override
    public List<Brand> findBranByCategoryId(Long categoryId) {
        List<Brand> list = categoryBrandMapper.findBranByCategoryId(categoryId);
        return list;
    }
}
