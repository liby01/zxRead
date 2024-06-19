package com.by.zx.common.exception;

import com.by.zx.model.vo.common.ResultCodeEnum;
import lombok.Data;

@Data
public class DiyException extends RuntimeException{//RuntimeException:运行时异常{

    private Integer code;
    private String massafe;
    private ResultCodeEnum resultCodeEnum;

    public DiyException(ResultCodeEnum resultCodeEnum){
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.massafe = resultCodeEnum.getMessage();
    }
}
