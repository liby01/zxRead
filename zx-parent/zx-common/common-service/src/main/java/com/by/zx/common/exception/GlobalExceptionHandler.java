package com.by.zx.common.exception;

import com.by.zx.model.vo.common.Result;
import com.by.zx.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice//给@Controller增加统一的操作和处理
public class GlobalExceptionHandler {

    //全局异常处理
    @ExceptionHandler(Exception.class)//捕获Controller，抛出指定类型异常
    // 例如在出现Exception异常时就返回error()方法指定的Result格式
    @ResponseBody//返回json数据格式
    public Result error(Exception e){
        e.printStackTrace();
        return Result.build(null , ResultCodeEnum.ERRO) ;
    }

    //自定义异常处理
    @ExceptionHandler(DiyException.class)
    @ResponseBody
    public Result error(DiyException e){
        e.printStackTrace();
        return Result.build(null , e.getResultCodeEnum()) ;
    }
}
