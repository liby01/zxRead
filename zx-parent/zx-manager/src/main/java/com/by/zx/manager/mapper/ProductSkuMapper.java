package com.by.zx.manager.mapper;

import com.by.zx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {

    //保存sku信息到
    void save(ProductSku productSku);

    //根据id查询商品sku信息列表 produck_sku表
    List<ProductSku> findProductSkuByProductById(Long id);

    //修改商品sku信息
    void updateById(ProductSku productSku);

    //根据商品id删除
    void deleteByProductId(Long id);
}
