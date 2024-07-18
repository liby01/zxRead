package com.by.zx.product.mapper;

import com.by.zx.model.dto.h5.ProductSkuDto;
import com.by.zx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {

    //根据销量排序，获取前10条记录
    List<ProductSku> selectProductSkuBySale();

    //分页查询
    List<ProductSku> findByPage(ProductSkuDto productSkuDto);

    //当前sku信息
    ProductSku getById(Long skuId);

    //同一个商品下面的sku信息列表
    List<ProductSku> findByProductId(Long productId);

    //远程调用：更新商品sku销量
    void updateSale(Long skuId, Integer num);
}
