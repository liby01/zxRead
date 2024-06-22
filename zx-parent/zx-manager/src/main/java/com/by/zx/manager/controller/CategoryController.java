package com.by.zx.manager.controller;

import com.by.zx.manager.service.CategoryService;
import com.by.zx.model.entity.product.Category;
import com.by.zx.model.vo.common.Result;
import com.by.zx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// 标记此类作为分类管理接口
@Tag(name = "分类管理接口")
// 标记此类为一个REST控制器
@RestController
// 为该控制器定义请求映射路径
@RequestMapping(value="/admin/product/category")
public class CategoryController {

    // 自动注入CategoryService实例
    @Autowired
    CategoryService categoryService;

    // 分类列表，每次查询一层数据
    @Operation(summary = "查询分类数据") // Swagger注解，提供API文档说明
    @GetMapping("/findCategoryList/{parentId}") // 定义GET请求路径，并接受路径参数
    public Result findCategoryList(@PathVariable Long parentId) {
        // 调用服务层方法查询分类数据
        List<Category> list = categoryService.findCategoryList(parentId);
        // 返回封装结果，包含分类数据和成功状态码
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    // 导出分类数据
    @Operation(summary = "导出分类数据") // Swagger注解，提供API文档说明
    @GetMapping("/exportData") // 定义GET请求路径
    public void exportData(HttpServletResponse response) {
        // 调用服务层方法导出数据
        categoryService.exportData(response);
    }

    // 导入分类数据
    @Operation(summary = "导入分类数据") // Swagger注解，提供API文档说明
    @PostMapping("/importData") // 定义POST请求路径
    public Result importData(MultipartFile file) {
        // 调用服务层方法导入数据
        categoryService.importData(file);
        // 返回封装结果，包含成功状态码
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}

