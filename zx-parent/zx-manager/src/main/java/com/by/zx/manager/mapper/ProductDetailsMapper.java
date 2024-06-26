package com.by.zx.manager.mapper;

import com.by.zx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsMapper {

    //保存商品详情数据
    void save(ProductDetails productDetails);

    //根据id查询商品详情数据 product_details表
    ProductDetails findProductDetailsById(Long id);

    //修改商品详细信息
    void updateById(ProductDetails productDetails);

    //根据商品id删除
    void deleteByProductId(Long id);
}
