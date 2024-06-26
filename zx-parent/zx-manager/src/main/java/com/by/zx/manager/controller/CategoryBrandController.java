package com.by.zx.manager.controller;

import com.by.zx.manager.service.CategoryBrandService;
import com.by.zx.model.dto.product.CategoryBrandDto;
import com.by.zx.model.entity.product.Brand;
import com.by.zx.model.entity.product.CategoryBrand;
import com.by.zx.model.vo.common.Result;
import com.by.zx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "品牌分类")
@RestController
@RequestMapping(value = "/admin/product/categoryBrand")
public class CategoryBrandController {

    @Autowired
    private CategoryBrandService categoryBrandService;

    //根据分类Id查询所有品牌数据
    @Operation(summary = "根据分类Id查询所有品牌数据")
    @GetMapping(value = "findBrandByCategoryId/{categoryId}")
    public Result findBranByCategoryId(@PathVariable(value = "categoryId") Long categoryId){
        List<Brand> list = categoryBrandService.findBranByCategoryId(categoryId);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }


    //分类品牌条件分页查询
    @Operation(summary = "分类品牌条件分页查询")
    @GetMapping(value = "/{page}/{limit}")
    public Result findByPage(@PathVariable Integer page,
                             @PathVariable Integer limit,
                             CategoryBrandDto categoryBrandDto) {
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.findByPage(page,limit,categoryBrandDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //添加
    @Operation(summary = "添加")
    @PostMapping(value = "/save")
    public Result save(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.save(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //修改
    @Operation(summary = "修改")
    @PutMapping(value = "/updateById")
    public Result updateById(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.updateById(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //删除
    @Operation(summary = "删除")
    @DeleteMapping(value = "/deleteById/{id}")
    public Result deleteById(@PathVariable("id") Long id) {
        categoryBrandService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
