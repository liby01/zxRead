package com.by.zx.common.log.service;

import com.by.zx.model.entity.system.SysOperLog;

public interface AsyncOperLogService {

    // 保存日志数据
    public abstract void saveSysOperLog(SysOperLog sysOperLog) ;

}