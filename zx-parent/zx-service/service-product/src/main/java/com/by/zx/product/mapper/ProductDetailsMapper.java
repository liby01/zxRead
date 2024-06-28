package com.by.zx.product.mapper;

import com.by.zx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsMapper {

    //商品详情信息
    ProductDetails getByProductId(Long productId);
}
