package com.by.zx.manager.mapper;

import com.by.zx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {

    //列表-分页
    List<Brand> findByPage();

    //添加品牌
    void save(Brand brand);

    //修改品牌
    void update(Brand brand);

    //删除品牌
    void deleteById(Long id);

    //查询所有品牌
    List<Brand> findAll();
}
