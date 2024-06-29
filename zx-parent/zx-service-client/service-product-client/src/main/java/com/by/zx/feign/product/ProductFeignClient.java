package com.by.zx.feign.product;

import com.by.zx.model.entity.product.ProductSku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 使用 @FeignClient 注解声明一个 Feign 客户端，用于调用 service-product 服务
@FeignClient(value = "service-product")
public interface ProductFeignClient {

    // 声明一个 GET 请求，访问 service-product 服务的 /api/product/getBySkuId/{skuId} 接口
    @GetMapping("/api/product/getBySkuId/{skuId}")
    public abstract ProductSku getBySkuId(@PathVariable("skuId") Long skuId);

    /*
     * @FeignClient(value = "service-product") 注解声明这个接口是一个 Feign 客户端，
     * 其中 value 属性指定要调用的微服务名称，即 service-product。
     *
     * @GetMapping("/api/product/getBySkuId/{skuId}") 注解声明了一个 GET 请求，路径为
     * /api/product/getBySkuId/{skuId}，其中 {skuId} 是路径变量，将被实际调用时的参数替换。
     *
     * getBySkuId(@PathVariable Long skuId) 方法是对外提供的接口，用于根据 SKU ID 获取商品信息。
     * @PathVariable 注解用于绑定方法参数 skuId 到 URL 路径变量 {skuId}。
     *
     * 返回值类型为 ProductSku，这是一个自定义的类，封装了商品 SKU 的相关信息。
     */
}
