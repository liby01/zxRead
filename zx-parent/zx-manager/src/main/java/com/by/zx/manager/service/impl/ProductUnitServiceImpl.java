package com.by.zx.manager.service.impl;

import com.by.zx.manager.mapper.ProductUnitMapper;
import com.by.zx.manager.service.ProductUnitService;
import com.by.zx.model.entity.base.ProductUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUnitServiceImpl implements ProductUnitService {

    @Autowired
    private ProductUnitMapper productUnitMapper;

    //查询所有单元数据
    @Override
    public List<ProductUnit> findAll() {
        List<ProductUnit> list = productUnitMapper.findAll();
        return list;
    }
}
