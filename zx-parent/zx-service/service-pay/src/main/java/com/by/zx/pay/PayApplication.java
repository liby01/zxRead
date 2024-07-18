package com.by.zx.pay;

import com.by.zx.common.anno.EnableUserLoginAuthInterceptor;
import com.by.zx.pay.utils.AlipayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableConfigurationProperties(value = {AlipayProperties.class})
@EnableUserLoginAuthInterceptor
@EnableFeignClients(basePackages = {"com.by.zx"})
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class , args) ;
    }

}
