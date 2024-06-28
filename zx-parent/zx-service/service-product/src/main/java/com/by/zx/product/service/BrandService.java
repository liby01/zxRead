package com.by.zx.product.service;

import com.by.zx.model.entity.product.Brand;

import java.util.List;

public interface BrandService {

    //获取全部品牌
    List<Brand> findAll();
}
