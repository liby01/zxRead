package com.by.zx.manager.service.impl;

import com.by.zx.manager.mapper.ProductSpecMapper;
import com.by.zx.manager.service.ProductSpecService;
import com.by.zx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSpecServiceImpl implements ProductSpecService {

    @Autowired
    private ProductSpecMapper productSpecMapper;

    //商品规格条件分页查询
    @Override
    public PageInfo<ProductSpec> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<ProductSpec> list = productSpecMapper.findByPage();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    //添加
    @Override
    public void save(ProductSpec productSpec) {
        productSpecMapper.save(productSpec);
    }

    //修改
    @Override
    public void updateById(ProductSpec productSpec) {
        productSpecMapper.updateById(productSpec);
    }

    //修改
    @Override
    public void deleteById(long id) {
        productSpecMapper.deleteById(id);
    }

    //查询所有商品规格
    @Override
    public List<ProductSpec> findAll() {
        List<ProductSpec> list = productSpecMapper.findAll();
        return list;
    }
}
