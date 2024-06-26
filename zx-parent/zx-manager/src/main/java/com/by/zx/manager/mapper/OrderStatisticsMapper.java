package com.by.zx.manager.mapper;

import com.by.zx.model.dto.order.OrderStatisticsDto;
import com.by.zx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderStatisticsMapper {

    //3 把统计之后的数据，添加统计结果表里面
    void insert(OrderStatistics orderStatistics);


    List<OrderStatistics> selectList(OrderStatisticsDto orderStatisticsDto);
}
