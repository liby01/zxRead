package com.by.zx.manager.service;

import com.by.zx.model.dto.order.OrderStatisticsDto;
import com.by.zx.model.vo.order.OrderStatisticsVo;

public interface OrderInfoService {


    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
