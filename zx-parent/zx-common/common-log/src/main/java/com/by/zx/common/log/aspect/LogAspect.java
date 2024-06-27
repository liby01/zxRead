package com.by.zx.common.log.aspect;

import com.by.zx.common.log.annotation.Log;
import com.by.zx.common.log.service.AsyncOperLogService;
import com.by.zx.common.log.utils.LogUtil;
import com.by.zx.model.entity.system.SysOperLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private AsyncOperLogService operLogService;

    //环绕通知
    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog) {

//        String title = sysLog.title();
//        int businessType = sysLog.businessType();
//        System.out.println("title: "+title+" ::businessType: "+businessType);

        //业务方法调用之前，封装数据
        SysOperLog sysOperLog = new SysOperLog();
        LogUtil.beforeHandleLog(sysLog,joinPoint,sysOperLog);

        //业务方法
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();

//            System.out.println("在业务方法之后执行....");
            //调用业务方法之后，封装数据
            LogUtil.afterHandlLog(sysLog,proceed,sysOperLog,0,null);
        } catch (Throwable e) {
            e.printStackTrace();
            LogUtil.afterHandlLog(sysLog,proceed,sysOperLog,1,e.getMessage());
            //抛出运行时异常
            throw new RuntimeException();
        }

        //调用service方法把日志信息添加数据库里面
        operLogService.saveSysOperLog(sysOperLog);
        return proceed;
    }
}