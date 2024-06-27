package com.by.zx.manager.task;

import cn.hutool.core.date.DateUtil;
import com.by.zx.manager.mapper.OrderInfoMapper;
import com.by.zx.manager.mapper.OrderStatisticsMapper;
import com.by.zx.model.entity.order.OrderStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class OrderStatisticsTask {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;


    //每天凌晨2点，查询前一天统计数据，把统计之后数据添加统计结果表里面
    @Scheduled(cron = "0 0 2 * * ?")
    //@Scheduled(cron = "0/5 * * * * ?")
    public void orderTotalAmountStatistics(){
        //1、获取前一天的日期,并格式化
        String createDate = DateUtil.offsetDay(new Date(), -1).toString("yyyy-MM-dd");
        //2、根据前一天的日期进行统计
        //  统计前一天的交易金额
        OrderStatistics orderStatistics = orderInfoMapper.selectStatisticsByDate(createDate);
        //3、把统计的数据添加到统计表 order_statistics
        if(orderStatistics != null){
            orderStatisticsMapper.insert(orderStatistics);
            System.out.println(createDate);
        }
        //
    }


//    // 定义一个时间格式化工具
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//
//    // 使用 @Scheduled 注解来定义一个定时任务
//    // cron = "0/5 * * * * ?" 表示每隔 5 秒执行一次任务
//    @Scheduled(cron = "0/5 * * * * ?")
//    // 定义一个名为 testHello 的方法，作为定时任务的执行内容
//    public void testHello() {
//        // 获取当前时间并格式化
//        String formattedDate = dateFormat.format(new Date());
//        // 打印格式化后的当前时间，格式为 "hello : [当前时间]"
//        System.out.println("hello : " + formattedDate);
//    }


}
