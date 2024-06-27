package com.by.zx.manager;


import com.by.zx.manager.properties.MinioProperties;
import com.by.zx.manager.properties.UserProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootApplication
//springboot默认扫描当前及其子包内容，Knife4jConfig配置类不在该规则下，
@ComponentScan(basePackages = {"com.by.zx"})
// 所以需要在启动类添加@ComponentScan(basePackages = {"com.by.zx"})注解，设置包的扫描路径
@EnableConfigurationProperties(value = {UserProperties.class, MinioProperties.class})//使配置类生效
@EnableScheduling //开启定时任务
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }
}
