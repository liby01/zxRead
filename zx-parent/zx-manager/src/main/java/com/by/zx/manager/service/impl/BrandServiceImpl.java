package com.by.zx.manager.service.impl;

import com.by.zx.manager.mapper.BrandMapper;
import com.by.zx.manager.service.BrandService;
import com.by.zx.model.entity.product.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    //列表-分页
    @Override
    public PageInfo<Brand> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Brand> list = brandMapper.findByPage();
        PageInfo<Brand> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //添加品牌
    @Override
    public void save(Brand brand) {
        brandMapper.save(brand);
    }

    //修改品牌
    @Override
    public void update(Brand brand) {
        brandMapper.update(brand);
    }

    //删除品牌
    @Override
    public void deleteById(Long id) {
        brandMapper.deleteById(id);
    }

    //查询所有品牌
    @Override
    public List<Brand> findAll() {
        List<Brand> list = brandMapper.findAll();
        return list;
    }
}
