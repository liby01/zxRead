package com.by.zx.manager.service;

import com.by.zx.model.dto.product.ProductDto;
import com.by.zx.model.entity.product.Product;
import com.github.pagehelper.PageInfo;

public interface ProductService {

    //列表（条件分页查询接口）
    PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto);

    //保存
    void save(Product product);

    //根据商品id查看商品信息
    Product getById(Long id);

    //修改保存
    void update(Product product);

    //删除商品
    void deleteById(Long id);
}
