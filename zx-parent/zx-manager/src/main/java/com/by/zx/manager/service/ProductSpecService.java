package com.by.zx.manager.service;

import com.by.zx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageInfo;

public interface ProductSpecService {

    //商品规格条件分页查询
    PageInfo<ProductSpec> findByPage(Integer page, Integer limit);


    //添加商品规格
    void save(ProductSpec productSpec);

    //修改
    void updateById(ProductSpec productSpec);

    //删除
    void deleteById(long id);
}
