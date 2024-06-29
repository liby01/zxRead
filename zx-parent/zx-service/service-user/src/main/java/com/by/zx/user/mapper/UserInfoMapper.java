package com.by.zx.user.mapper;

import com.by.zx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {

    //保存新增用户
    void save(UserInfo userInfo);

    //校验用户名不能重复
    UserInfo selectByUsername(String username);
}
