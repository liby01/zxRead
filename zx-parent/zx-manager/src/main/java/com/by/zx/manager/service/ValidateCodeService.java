package com.by.zx.manager.service;

import com.by.zx.model.vo.system.ValidateCodeVo;

public interface ValidateCodeService {

    // 获取验证码图片
    public abstract ValidateCodeVo generateValidateCode();

}
