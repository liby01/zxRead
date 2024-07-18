package com.by.zx.common.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserTokenOpenFeignInterceptor implements RequestInterceptor {

    //order远程调用cart未传递请求头信息，导致AuthContextUtil.getUserInfo()" is null
    //用于解决远程调用时，不能传递请求头的问题
    //向请求头传递token的字符串
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader("token");
        requestTemplate.header("token",token);
    }
}
