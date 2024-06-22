package com.by.zx.manager.controller;

import com.by.zx.manager.service.CategoryService;
import com.by.zx.model.entity.product.Category;
import com.by.zx.model.vo.common.Result;
import com.by.zx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "分类管理接口")
@RestController
@RequestMapping(value="/admin/product/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    //分类列表，每次查询一层数据
    // SELECT * FROM category WHERE parent_id=parentId
    @GetMapping("/findCategoryList/{parentId}")
    public Result findCategoryList(@PathVariable Long parentId) {
        List<Category> list = categoryService.findCategoryList(parentId);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
