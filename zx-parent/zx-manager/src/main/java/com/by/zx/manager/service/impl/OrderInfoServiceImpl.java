package com.by.zx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.by.zx.manager.mapper.OrderStatisticsMapper;
import com.by.zx.manager.service.OrderInfoService;
import com.by.zx.model.dto.order.OrderStatisticsDto;
import com.by.zx.model.entity.order.OrderStatistics;
import com.by.zx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;


    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {

        //根据dto条件查询统计结果数据，返回list
        List<OrderStatistics> orderStatisticsList = orderStatisticsMapper.selectList(orderStatisticsDto);

        // 使用流的方式遍历集合
        // 遍历 orderStatisticsList 集合，得到所有日期，并把所有日期封装到 dateList 集合中
        List<String> dateList = orderStatisticsList.stream()
                // 使用 map 方法对每个 orderStatistics 对象进行处理
                .map(orderStatistics ->
                        // 使用 DateUtil 的 offsetDay 方法，将订单日期减去一天，并格式化为 "yyyy-MM-dd" 字符串
                        DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd"))
                // 将处理后的结果收集到一个新的 List 集合中
                .collect(Collectors.toList());

        // 遍历 orderStatisticsList 集合，得到所有日期对应的总金额，把总金额封装到 decimalList 集合里面
        List<BigDecimal> decimalList = orderStatisticsList.stream()
                // 使用 map 方法对每个 orderStatistics 对象进行处理，提取总金额
                .map(OrderStatistics::getTotalAmount)
                // 将处理后的结果收集到一个新的 List 集合中
                .collect(Collectors.toList());

        // 将两个 list 封装到 OrderStatisticsVo ，返回 OrderStatisticsVo
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(decimalList);
        return orderStatisticsVo;
    }

}
