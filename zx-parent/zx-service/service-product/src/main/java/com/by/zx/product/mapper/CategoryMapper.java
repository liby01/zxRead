package com.by.zx.product.mapper;

import com.by.zx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //查询所有1级分类
    List<Category> selectOneCategory();

    //获取所有分类数据
    List<Category> findAll();
}
