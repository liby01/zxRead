package com.by.zx.manager.controller;

import com.by.zx.manager.service.ProductService;
import com.by.zx.model.dto.product.ProductDto;
import com.by.zx.model.entity.product.Product;
import com.by.zx.model.vo.common.Result;
import com.by.zx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/admin/product/product")
public class ProductController {

    @Autowired
    private ProductService productService ;

    //列表（条件分页查询）
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable("page") Integer page,
                       @PathVariable("limit") Integer limit,
                       ProductDto productDto) {
        PageInfo<Product> pageInfo = productService.findByPage(page,limit,productDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //添加
    @PostMapping(value = "/save")
    public Result<Product> save(@RequestBody Product product) {
        productService.save(product);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    //根据商品id查询商品信息
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return Result.build(product, ResultCodeEnum.SUCCESS);
    }

    //修改保存
    @PutMapping(value = "/updateById")
    public Result<Product> update(@RequestBody Product product) {
        productService.update(product);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    //删除商品
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@Parameter(name = "id", description = "商品id", required = true) @PathVariable Long id) {
        productService.deleteById(id);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

}
