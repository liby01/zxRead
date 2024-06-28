package com.by.zx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.by.zx.model.dto.h5.ProductSkuDto;
import com.by.zx.model.entity.product.Product;
import com.by.zx.model.entity.product.ProductDetails;
import com.by.zx.model.entity.product.ProductSku;
import com.by.zx.model.vo.h5.ProductItemVo;
import com.by.zx.product.mapper.ProductDetailsMapper;
import com.by.zx.product.mapper.ProductMapper;
import com.by.zx.product.mapper.ProductSkuMapper;
import com.by.zx.product.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service // 标记这是一个服务类，使其成为 Spring 容器中的一个 Bean
public class ProductServiceImpl implements ProductService {

    @Autowired // 自动注入 ProductSkuMapper 实例
    private ProductSkuMapper productSkuMapper;

    @Autowired // 自动注入 ProductMapper 实例
    private ProductMapper productMapper;

    @Autowired // 自动注入 ProductDetailsMapper 实例
    private ProductDetailsMapper productDetailsMapper;

    // 根据销量排序，获取前10条记录
    @Override
    public List<ProductSku> selectProductSkuBySale() {
        // 从数据库中获取按销量排序的前10条 ProductSku 记录
        List<ProductSku> list = productSkuMapper.selectProductSkuBySale();
        // 返回查询结果
        return list;
    }

    // 分页查询
    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        // 设置分页参数
        PageHelper.startPage(page, limit);
        // 查询符合条件的 ProductSku 列表
        List<ProductSku> productSkuList = productSkuMapper.findByPage(productSkuDto);
        // 将查询结果封装到 PageInfo 对象中
        return new PageInfo<>(productSkuList);
    }

    // 商品详情
    @Override
    public ProductItemVo item(Long skuId) {
        // 获取当前 SKU 信息
        ProductSku productSku = productSkuMapper.getById(skuId);

        // 获取当前商品信息
        Product product = productMapper.getById(productSku.getProductId());

        // 获取同一个商品下的所有 SKU 信息列表
        List<ProductSku> productSkuList = productSkuMapper.findByProductId(productSku.getProductId());

        // 建立 SKU 规格与 SKU ID 对应关系的 Map
        Map<String, Object> skuSpecValueMap = new HashMap<>();
        productSkuList.forEach(item -> {
            skuSpecValueMap.put(item.getSkuSpec(), item.getId());
        });

        // 获取商品详情信息
        ProductDetails productDetails = productDetailsMapper.getByProductId(productSku.getProductId());

        // 创建并设置 ProductItemVo 对象
        ProductItemVo productItemVo = new ProductItemVo();
        productItemVo.setProductSku(productSku); // 设置当前 SKU 信息
        productItemVo.setProduct(product); // 设置当前商品信息
        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails.getImageUrls().split(","))); // 设置商品详情图片列表
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(","))); // 设置商品轮播图列表
        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue())); // 设置商品规格值列表
        productItemVo.setSkuSpecValueMap(skuSpecValueMap); // 设置 SKU 规格与 SKU ID 对应关系

        // 返回 ProductItemVo 对象
        return productItemVo;
    }
}
