package com.by.zx.pay.mapper;

import com.by.zx.model.entity.pay.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentInfoMapper {

    //1 根据订单编号查询支付记录
    PaymentInfo getByOrderNo(String orderNo);

    //添加
    void save(PaymentInfo paymentInfo);

    void updatePaymentInfo(PaymentInfo paymentInfo);
}
