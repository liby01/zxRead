package com.by.zx.feign.cart;

import com.by.zx.model.entity.h5.CartInfo;
import com.by.zx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "service-cart")
public interface CartFeignClient {

    //远程调用：订单结算使用，获取购物车选中商品列表
    @GetMapping(value = "/api/order/cart/auth/getAllCkecked")
    public List<CartInfo> getAllCkecked();


    //远程调用：把生成订单商品，从购物车删除
    @GetMapping("/api/order/cart/auth/deleteChecked")
    public Result deleteChecked();
}
