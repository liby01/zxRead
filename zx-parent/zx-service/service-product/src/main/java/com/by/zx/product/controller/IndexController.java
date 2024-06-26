package com.by.zx.product.controller;

import com.by.zx.model.entity.product.Category;
import com.by.zx.model.entity.product.ProductSku;
import com.by.zx.model.vo.common.Result;
import com.by.zx.model.vo.common.ResultCodeEnum;
import com.by.zx.model.vo.h5.IndexVo;
import com.by.zx.product.service.CategoryService;
import com.by.zx.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "首页接口管理")
@RestController
@RequestMapping(value="/api/product/index")
//@CrossOrigin//跨域
@SuppressWarnings({"unchecked", "rawtypes"})
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Operation(summary = "获取首页数据")
    @GetMapping
    public Result index() {
        //查询所有1级分类
        List<Category> categorylist = categoryService.selectOneCategory();
        //根据销量排序，获取前10条记录
        List<ProductSku> productSkuList = productService.selectProductSkuBySale();
        //封装数据
        IndexVo indexVo = new IndexVo();
        indexVo.setCategoryList(categorylist);
        indexVo.setProductSkuList(productSkuList);
        return Result.build(indexVo, ResultCodeEnum.SUCCESS);
    }

    //
}
