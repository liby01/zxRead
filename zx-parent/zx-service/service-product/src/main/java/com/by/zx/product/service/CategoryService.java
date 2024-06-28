package com.by.zx.product.service;

import com.by.zx.model.entity.product.Category;

import java.util.List;

public interface CategoryService {

    //查询所有1级分类
    List<Category> selectOneCategory();

    //获取分类树形数据
    List<Category> findCategoryTree();
}
