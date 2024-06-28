package com.by.zx.product.service.impl;

import com.by.zx.model.entity.product.Brand;
import com.by.zx.product.mapper.BrandMapper;
import com.by.zx.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    // 获取全部品牌，并将结果缓存到名为 "brandList" 的缓存中
    // 如果返回的结果为空，则不会将结果缓存
    @Cacheable(value = "brandList", unless="#result.size() == 0")
    @Override
    public List<Brand> findAll() {
        // 查询数据库，获取所有品牌
        List<Brand> list = brandMapper.findAll();
        return list;
    }
}
