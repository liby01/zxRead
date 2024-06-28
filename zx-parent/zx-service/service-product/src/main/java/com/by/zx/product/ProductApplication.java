package com.by.zx.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication // 标记这是一个Spring Boot应用程序
@EnableCaching // 启用Spring的缓存支持
//springboot默认扫描当前及其子包内容，Knife4jConfig配置类不在该规则下，
@ComponentScan(basePackages = {"com.by.zx"})
public class ProductApplication {

    public static void main(String[] args) {
        // 启动Spring Boot应用程序
        SpringApplication.run(ProductApplication.class, args);
    }
}

