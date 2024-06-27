package com.by.zx.manager.service.impl;

import com.by.zx.common.log.service.AsyncOperLogService;
import com.by.zx.manager.mapper.SysOperLogMapper;
import com.by.zx.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncOperLogServiceImpl implements AsyncOperLogService {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    //保存日志数据
    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {

        //保存日志数据
        sysOperLogMapper.insert(sysOperLog);
    }
}
