package com.by.zx.pay.service;

import com.by.zx.model.entity.pay.PaymentInfo;

import java.util.Map;

public interface PaymentInfoService {

    //保存支付记录
    PaymentInfo savePaymentInfo(String orderNo);

    //支付完成，更新状态
    void updatePaymentStatus(Map<String, String> paramMap);
}
