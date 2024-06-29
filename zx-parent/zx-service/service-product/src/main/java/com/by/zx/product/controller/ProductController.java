package com.by.zx.product.controller;
import com.by.zx.model.vo.h5.ProductItemVo;
import com.by.zx.model.dto.h5.ProductSkuDto;
import com.by.zx.model.entity.product.ProductSku;
import com.by.zx.model.vo.common.Result;
import com.by.zx.model.vo.common.ResultCodeEnum;
import com.by.zx.product.service.ProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "商品列表管理")
@RestController
@RequestMapping(value="/api/product")
@SuppressWarnings({"unchecked", "rawtypes"})
public class ProductController {

    @Autowired // 自动注入 ProductService 实例
    private ProductService productService;

    @Operation(summary = "分页查询") // 使用 OpenAPI 注解为该方法提供描述信息
    @GetMapping(value = "/{page}/{limit}") // 映射 HTTP GET 请求到 /{page}/{limit} 路径
    public Result<PageInfo<ProductSku>> findByPage(
            @Parameter(name = "page", description = "当前页码", required = true) @PathVariable Integer page,//required = true 表示该参数是必需的
            @Parameter(name = "limit", description = "每页记录数", required = true) @PathVariable Integer limit,
            @Parameter(name = "productSkuDto", description = "搜索条件对象", required = false) ProductSkuDto productSkuDto) {

        // 调用 ProductService 的 findByPage 方法进行分页查询
        PageInfo<ProductSku> pageInfo = productService.findByPage(page, limit, productSkuDto);

        // 返回查询结果，并封装到 Result 对象中，设置返回码为成功
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }


    // 商品详情
    @Operation(summary = "商品详情") // 使用 OpenAPI 注解为该方法提供描述信息
    @GetMapping("item/{skuId}") // 映射 HTTP GET 请求到 /item/{skuId} 路径
    public Result<ProductItemVo> item(
            @Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable Long skuId) {

        // 调用 ProductService 的 item 方法获取商品详情
        ProductItemVo productItemVo = productService.item(skuId);

        // 返回商品详情，并封装到 Result 对象中，设置返回码为成功
        return Result.build(productItemVo, ResultCodeEnum.SUCCESS);
    }

    //远程调用：根据skuId返回sku信息
    @GetMapping("getBySkuId/{skuId}")
    public ProductSku getBySkuId(@PathVariable Long skuId){
        ProductSku productSku = productService.getBySkuId(skuId);
        return productSku;
    }
}
