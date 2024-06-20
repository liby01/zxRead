package com.by.zx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "zx.auth")//通过前缀获取application-dev.yml文件的值
public class UserProperties {
    private List<String> noAuthUrls ;
}
