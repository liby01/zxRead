package com.by.zx.manager.controller;

import com.by.zx.manager.service.ProductSpecService;
import com.by.zx.model.entity.product.ProductSpec;
import com.by.zx.model.vo.common.Result;
import com.by.zx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "规格管理")
@RestController
@RequestMapping(value = "/admin/product/productSpec")
public class ProductSpecController {

    private final ProductSpecService productSpecService;

    @Autowired
    private ProductSpecController(ProductSpecService productSpecService) {
        this.productSpecService = productSpecService;
    }

    //查询所有商品规格
    @Operation(summary = "查询所有商品规格")
    @GetMapping(value = "/findAll")
    public Result<List<ProductSpec>> findAll() {
        List<ProductSpec> list = productSpecService.findAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    //商品规格条件分页查询
    @Operation(summary = "商品规格条件分页查询")
    @GetMapping(value = "/{page}/{limit}")
    public Result<PageInfo<ProductSpec>> findByPage(@PathVariable Integer page, @PathVariable Integer limit) {
        PageInfo<ProductSpec> pageInfo = productSpecService.findByPage(page,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //添加
    @Operation(summary = "添加商品规格")
    @PostMapping("/save")
    public Result<ProductSpec> save(@RequestBody ProductSpec productSpec) {
        productSpecService.save(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //修改
    @Operation(summary = "修改商品规格")
    @PutMapping("/updateById")
    public Result<ProductSpec> updateById(@RequestBody ProductSpec productSpec) {
        productSpecService.updateById(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //删除
    @Operation(summary = "删除商品规格")
    @DeleteMapping("/deleteById/{id}")
    public Result<ProductSpec> deleteById(@PathVariable("id")long id ) {
        productSpecService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
