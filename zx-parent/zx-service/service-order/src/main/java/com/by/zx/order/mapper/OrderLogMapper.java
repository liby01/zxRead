package com.by.zx.order.mapper;

import com.by.zx.model.entity.order.OrderLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderLogMapper {

    //6 添加数据到order_log表
    void save(OrderLog orderLog);
}
