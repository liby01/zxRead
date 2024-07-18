package com.by.zx.user.service.impl;

import com.by.zx.model.entity.user.UserAddress;
import com.by.zx.user.mapper.UserAddressMapper;
import com.by.zx.user.service.UserAddressService;
import com.by.zx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    //获取用户地址列表
    @Override
    public List<UserAddress> findUserAddressList() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        return userAddressMapper.findUserAddressList(userId);
    }

    //根据id获取收货地址信息
    @Override
    public UserAddress getUserAddress(Long id) {
        return userAddressMapper.getUserAddress(id);
    }
}
