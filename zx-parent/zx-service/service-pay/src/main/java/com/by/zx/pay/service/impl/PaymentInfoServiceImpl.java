package com.by.zx.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.by.zx.feign.order.OrderFeignClient;
import com.by.zx.feign.product.ProductFeignClient;
import com.by.zx.model.dto.product.SkuSaleDto;
import com.by.zx.model.entity.order.OrderInfo;
import com.by.zx.model.entity.order.OrderItem;
import com.by.zx.model.entity.pay.PaymentInfo;
import com.by.zx.pay.mapper.PaymentInfoMapper;
import com.by.zx.pay.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    //保存支付记录
    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {
        //1 根据订单编号查询支付记录
        PaymentInfo paymentInfo = paymentInfoMapper.getByOrderNo(orderNo);

        //2 判断支付记录是否存在
        if(paymentInfo == null) {
            //远程调用获取订单信息
            OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo);
            //把orderInfo获取数据封装到paymentInfo里面
            paymentInfo = new PaymentInfo();
            paymentInfo.setUserId(orderInfo.getUserId());
            paymentInfo.setPayType(orderInfo.getPayType());
            String content = "";
            for(OrderItem item : orderInfo.getOrderItemList()) {
                content += item.getSkuName() + " ";
            }
            paymentInfo.setContent(content);
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setOrderNo(orderNo);
            paymentInfo.setPaymentStatus(0);
            //添加
            paymentInfoMapper.save(paymentInfo);
        }
        return paymentInfo;
    }

    //支付完成，更新状态
    @Override
    public void updatePaymentStatus(Map<String, String> map) {
        // map.get("out_trade_no")
        //1 根据订单编号查询支付记录信息
        PaymentInfo paymentInfo = paymentInfoMapper.getByOrderNo(map.get("out_trade_no"));

        //2 判断如果支付记录已经完成，不需要更新
        if(paymentInfo.getPaymentStatus()==1) {
            return;
        }

        //3 没有完成才更新
        paymentInfo.setPaymentStatus(1);
        paymentInfo.setOutTradeNo(map.get("trade_no"));
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(JSON.toJSONString(map));
        paymentInfoMapper.updatePaymentInfo(paymentInfo);

        //更新订单状态
        orderFeignClient.updateOrderStatus(paymentInfo.getOrderNo());

        //更新sku商品销量......
        // 1、查询PaymentInfo
        // 2、更新支付信息
        // 3、更新订单的支付状态
        // 4、更新商品销量
        OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(paymentInfo.getOrderNo());
        List<SkuSaleDto> skuSaleDtoList = orderInfo.getOrderItemList().stream().map(item -> {
            SkuSaleDto skuSaleDto = new SkuSaleDto();
            skuSaleDto.setSkuId(item.getSkuId());
            skuSaleDto.setNum(item.getSkuNum());
            return skuSaleDto;
        }).collect(Collectors.toList());
        productFeignClient.updateSkuSaleNum(skuSaleDtoList) ;

    }
}
