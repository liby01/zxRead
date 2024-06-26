package com.by.zx.manager.mapper;

import com.by.zx.model.dto.product.CategoryBrandDto;
import com.by.zx.model.entity.product.Brand;
import com.by.zx.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryBrandMapper {

    //分类品牌条件分页查询
    List<CategoryBrand> findByPage(CategoryBrandDto categoryBrandDto);

    //添加
    void save(CategoryBrand categoryBrand);

    //修改
    void updateById(CategoryBrand categoryBrand);

    //删除
    void deleteById(Long id);

    //根据分类Id查询所有品牌数据
    List<Brand> findBranByCategoryId(Long categoryId);
}
