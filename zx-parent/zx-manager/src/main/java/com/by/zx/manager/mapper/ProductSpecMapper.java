package com.by.zx.manager.mapper;

import com.by.zx.model.entity.product.ProductSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSpecMapper {

    //商品规格条件分页查询
    List<ProductSpec> findByPage();

    //添加
    void save(ProductSpec productSpec);

    //修改
    void updateById(ProductSpec productSpec);

    //删除
    void deleteById(Long id);

    //查询所有商品规格
    List<ProductSpec> findAll();
}
