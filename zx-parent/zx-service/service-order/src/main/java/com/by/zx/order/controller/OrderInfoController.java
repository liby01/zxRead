package com.by.zx.order.controller;

import com.by.zx.model.dto.h5.OrderInfoDto;
import com.by.zx.model.entity.order.OrderInfo;
import com.by.zx.model.vo.common.Result;
import com.by.zx.model.vo.common.ResultCodeEnum;
import com.by.zx.model.vo.h5.TradeVo;
import com.by.zx.order.service.OrderInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单管理")
@RestController
@RequestMapping(value="/api/order/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    //确认下单
    @Operation(summary = "确认下单")
    @GetMapping("auth/trade")
    public Result<TradeVo> trade() {
        TradeVo tradeVo = orderInfoService.getTrade();
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    //生成订单
    @Operation(summary = "提交订单")
    @PostMapping("auth/submitOrder")
    public Result submitOrder(@RequestBody OrderInfoDto orderInfoDto) {
        Long orderId = orderInfoService.submitOrder(orderInfoDto);
        return Result.build(orderId,ResultCodeEnum.SUCCESS);
    }

    //获取订单信息
    @Operation(summary = "获取订单信息")
    @GetMapping("auth/{orderId}")
    public Result getOrderInfo(@PathVariable Long orderId) {
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderId);
        return Result.build(orderInfo,ResultCodeEnum.SUCCESS);
    }

    //立即购买
    @Operation(summary = "立即购买")
    @GetMapping("auth/buy/{skuId}")
    public Result buy(@PathVariable Long skuId) {
        TradeVo tradeVo = orderInfoService.buy(skuId);
        return Result.build(tradeVo,ResultCodeEnum.SUCCESS);
    }

    //获取订单分页列表
    @Operation(summary = "获取订单分页列表")
    @GetMapping("auth/{page}/{limit}")
    public Result<PageInfo<OrderInfo>> list(@PathVariable Integer page,
                                            @PathVariable Integer limit,
                                            @RequestParam(required = false, defaultValue = "") Integer orderStatus) {
        PageInfo<OrderInfo> pageInfo =
                orderInfoService.findOrderPage(page,limit,orderStatus);
        return Result.build(pageInfo,ResultCodeEnum.SUCCESS);
    }

    //远程调用：根据订单编号获取订单信息
    @Operation(summary = "获取订单信息")
    @GetMapping("auth/getOrderInfoByOrderNo/{orderNo}")
    public OrderInfo getOrderInfoByOrderNo(@Parameter(name = "orderId", description = "订单id", required = true) @PathVariable String orderNo) {
        OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderNo(orderNo);
        return orderInfo;
    }

    //更新订单状态
    @GetMapping("auth/updateOrderStatusPayed/{orderNo}")
    public Result updateOrderStatus(@PathVariable(value = "orderNo") String orderNo) {
        orderInfoService.updateOrderStatus(orderNo);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

}
