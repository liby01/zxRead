package com.by.zx.cart.service.impl;

import com.alibaba.fastjson2.JSON;
import com.by.zx.cart.service.CartService;
import com.by.zx.feign.product.ProductFeignClient;
import com.by.zx.model.entity.h5.CartInfo;
import com.by.zx.model.entity.product.ProductSku;
import com.by.zx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartServiceImpl implements CartService {

    @Autowired // 自动注入 RedisTemplate，用于与 Redis 交互。
    private RedisTemplate<String,String> redisTemplate;

    @Autowired // 自动注入 ProductFeignClient，用于远程调用商品服务获取商品信息。
    private ProductFeignClient productFeignClient;

    // 根据用户 ID 生成购物车的 key
    private String getCartKey(Long userId) {
        //定义key的名称
        return "user:cart:" + userId;
    }

    //添加购物车
    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        // 1. 必须登录状态，获取当前登录用户 ID 作为 Redis 的 hash 类型的 key 值
        //1.1从ThreadLocal获取用户信息
        Long userId = AuthContextUtil.getUserInfo().getId();
        //1.2构建hash类型key的名称
        String cartKey = getCartKey(userId);

        //2、从redis里面获取购物车数据，根据用户id+skuId获取（hash类型key+field）
        //hash类型 key:userId     field:skuId     value:sku信息
        Object cartInfoObj =
                redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));

        CartInfo cartInfo = null;
        //3、如果购物车已存在该商品，就把商品数量增加
        if (cartInfoObj != null) {
            System.out.println("cartInfoObj不为空: "+ cartInfoObj);
            //将cartInfoObj转为CartInfo类型
            cartInfo = JSON.parseObject(cartInfoObj.toString(),CartInfo.class);
            //数量相加
            cartInfo.setSkuNum(cartInfo.getSkuNum()+skuNum);
            //设置属性，购物车商品选中状态
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        }else{
            //4、如果购物车没有该商品，就直接添加到redis
            //远程调用实现：通过nacos + openFeign实现 根据skuId获取商品sku信息
            ProductSku productSku = productFeignClient.getBySkuId(skuId);
            cartInfo = new CartInfo();
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
            //将数据添加到redis
            redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), JSON.toJSONString(cartInfo));
        }

    }
}
