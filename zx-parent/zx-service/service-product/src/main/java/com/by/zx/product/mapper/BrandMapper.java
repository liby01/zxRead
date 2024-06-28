package com.by.zx.product.mapper;

import com.by.zx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {

    //获取全部品牌
    List<Brand> findAll();
}
