package com.by.zx.product.mapper;

import com.by.zx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

    //当前商品信息
    Product getById(Long productId);
}
