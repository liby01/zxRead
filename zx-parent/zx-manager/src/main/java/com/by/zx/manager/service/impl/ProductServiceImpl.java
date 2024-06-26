package com.by.zx.manager.service.impl;

import com.by.zx.manager.mapper.ProductDetailsMapper;
import com.by.zx.manager.mapper.ProductMapper;
import com.by.zx.manager.mapper.ProductSkuMapper;
import com.by.zx.manager.service.ProductService;
import com.by.zx.model.dto.product.ProductDto;
import com.by.zx.model.entity.product.Product;
import com.by.zx.model.entity.product.ProductDetails;
import com.by.zx.model.entity.product.ProductSku;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 标记此类为一个Spring服务
public class ProductServiceImpl implements ProductService {

    @Autowired // 自动注入ProductMapper依赖
    private ProductMapper productMapper;

    @Autowired // 自动注入ProductSkuMapper依赖
    private ProductSkuMapper productSkuMapper;

    @Autowired // 自动注入ProductDetailsMapper依赖
    private ProductDetailsMapper productDetailsMapper;


    // 列表（条件分页查询接口）
    @Override
    public PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto) {
        PageHelper.startPage(page, limit); // 设置分页参数
        List<Product> list = productMapper.findByPage(productDto); // 查询商品列表
        PageInfo<Product> pageInfo = new PageInfo<>(list); // 封装分页结果
        return pageInfo; // 返回分页信息
    }

    // 添加商品
    @Override
    public void save(Product product) {
        // 1. 保存商品基本信息到product表
        product.setStatus(0); // 初始状态为未上架
        product.setAuditStatus(0); // 初始审核状态为未审核
        productMapper.save(product); // 保存商品基本信息到数据库

        // 2. 获取商品SKU列表集合，保存SKU信息到product_sku表
        List<ProductSku> list = product.getProductSkuList(); // 获取商品的SKU列表
        for (int i = 0; i < list.size(); i++) { // 遍历SKU列表
            ProductSku productSku = list.get(i); // 获取当前SKU
            productSku.setSkuCode(product.getId() + "_" + i); // 设置SKU编号
            productSku.setProductId(product.getId()); // 设置商品ID
            productSku.setSkuName(product.getName() + productSku.getSkuSpec()); // 设置SKU名称
            productSku.setSaleNum(0); // 初始销量为0
            productSku.setStatus(0); // 初始状态为未上架
            productSkuMapper.save(productSku); // 保存SKU信息到数据库
        }

        // 3. 保存商品详情数据到product_details表
        ProductDetails productDetails = new ProductDetails(); // 创建商品详情对象
        productDetails.setProductId(product.getId()); // 设置商品ID
        productDetails.setImageUrls(product.getDetailsImageUrls()); // 设置商品详情图片URL
        productDetailsMapper.save(productDetails); // 保存商品详情信息到数据库
    }

    // 根据ID查询商品信息
    @Override
    public Product getById(Long id) {
        // 1. 根据ID查询商品基本信息（product表）
        Product product = productMapper.findProductById(id); // 查询商品基本信息

        // 2. 根据ID查询商品SKU信息列表（product_sku表）
        List<ProductSku> list = productSkuMapper.findProductSkuByProductById(id); // 查询商品SKU列表

        // 3. 根据ID查询商品详情数据（product_details表）
        ProductDetails productDetails = productDetailsMapper.findProductDetailsById(id); // 查询商品详情数据

        product.setProductSkuList(list); // 设置商品的SKU列表
        product.setDetailsImageUrls(productDetails.getImageUrls()); // 设置商品详情图片URL

        return product; // 返回商品对象
    }

    // 修改保存
    @Override
    public void update(Product product) {
        // 1. 修改商品基本信息
        productMapper.updateById(product); // 更新product表中的商品信息

        // 2. 修改商品SKU信息
        List<ProductSku> list = product.getProductSkuList(); // 获取商品SKU列表
        list.forEach(productSku -> {
            productSkuMapper.updateById(productSku); // 更新product_sku表中的SKU信息
        });

        // 3. 修改商品详细信息
        String detailsImageUrls = product.getDetailsImageUrls(); // 获取商品详情图片URL
        ProductDetails productDetails = productDetailsMapper.findProductDetailsById(product.getId()); // 查询商品详情
        productDetails.setImageUrls(detailsImageUrls); // 设置新的商品详情图片URL
        productDetailsMapper.updateById(productDetails); // 更新product_details表中的商品详情信息
    }


    //删除商品
    @Override
    public void deleteById(Long id) {
        //根据商品id删除 product表
        productMapper.deleteById(id);
        //根据商品id删除 product_sku表
        productSkuMapper.deleteByProductId(id);
        //根据商品id删除 product_details表
        productDetailsMapper.deleteByProductId(id);
    }

}


