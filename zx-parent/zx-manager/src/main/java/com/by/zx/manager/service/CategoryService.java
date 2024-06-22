package com.by.zx.manager.service;

import com.by.zx.model.entity.product.Category;

import java.util.List;

public interface CategoryService {

    //分类列表，每次查询一层数据
    List<Category> findCategoryList(Long parentId);

}
