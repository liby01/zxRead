package com.by.zx.cart;

import com.by.zx.common.anno.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

// 主要应用程序类的注解，标记为 Spring Boot 应用，排除数据库自动配置，因为 Cart 微服务不需要访问数据库。
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
// 启用 Feign 客户端，并指定 Feign 客户端所在的包，这里指定为 "com.by.zx" 包中的 Feign 客户端。
@EnableFeignClients(basePackages = {"com.by.zx"})
// 启用用户登录认证拦截器，这个注解应该是自定义的，用于在应用中全局启用登录认证拦截功能。
@EnableUserLoginAuthInterceptor
public class CartApplication {
    // 应用程序的主方法，启动 Spring Boot 应用。
    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }
}
