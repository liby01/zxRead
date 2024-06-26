package com.by.zx.manager.mapper;

import com.by.zx.model.dto.product.ProductDto;
import com.by.zx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    //列表（条件分页查询接口）
    List<Product> findByPage(ProductDto productDto);

    //添加
    void save(Product product);

    //根据id查询商品基本信息 product表
    Product findProductById(Long id);

    //修改商品基本信息
    void updateById(Product product);

    //根据商品id删除
    void deleteById(Long id);
}
