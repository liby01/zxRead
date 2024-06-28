package com.by.zx.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//springboot默认扫描当前及其子包内容，Knife4jConfig配置类不在该规则下，
@ComponentScan(basePackages = {"com.by.zx"})
@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
