package com.by.zx.product.service;

import com.by.zx.model.dto.h5.ProductSkuDto;
import com.by.zx.model.dto.product.SkuSaleDto;
import com.by.zx.model.entity.product.ProductSku;
import com.by.zx.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductService {

    //根据销量排序，获取前10条记录
    List<ProductSku> selectProductSkuBySale();

    //分页查询
    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    // 商品详情
    ProductItemVo item(Long skuId);

    //远程调用：根据skuId返回sku信息
    ProductSku getBySkuId(Long skuId);

    //远程调用：更新商品sku销量
    Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList);
}
