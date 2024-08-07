package com.by.zx.feign.user;

import com.by.zx.model.entity.user.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-user")
public interface UserFeignClient {

    @GetMapping("/api/user/userAddress/getUserAddress/{id}")
    public UserAddress getUserAddress(@PathVariable("id") Long id);
}
