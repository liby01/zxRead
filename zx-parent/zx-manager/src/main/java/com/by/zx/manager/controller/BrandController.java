package com.by.zx.manager.controller;

import com.by.zx.manager.service.BrandService;
import com.by.zx.model.entity.product.Brand;
import com.by.zx.model.vo.common.Result;
import com.by.zx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "品牌管理")
@RestController
@RequestMapping(value = "/admin/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    //列表-分页
    @Operation(summary = "列表查询") // Swagger注解，提供API文档说明
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Integer page,
                       @PathVariable Integer limit) {
        PageInfo<Brand> pageInfo = brandService.findByPage(page,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //添加品牌
    @Operation(summary = "添加品牌") // Swagger注解，提供API文档说明
    @PostMapping(value = "/save")
    public Result save(@RequestBody Brand brand) {
        brandService.save(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //修改品牌
    @Operation(summary = "修改品牌")
    @PutMapping(value = "/update")
    public Result update(@RequestBody Brand brand) {
        brandService.update(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //删除品牌
    @Operation(summary = "删除品牌")
    @DeleteMapping(value = "/deleteById/{id}")
    public Result deleteById(@PathVariable("id") Long id) {
        brandService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //查询所有品牌
    @Operation(summary = "查询所有品牌") // Swagger注解，提供API文档说明
    @GetMapping("/findAll")
    public Result findAll() {
        List<Brand> list = brandService.findAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

}
