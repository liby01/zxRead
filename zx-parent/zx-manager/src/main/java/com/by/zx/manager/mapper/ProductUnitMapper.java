package com.by.zx.manager.mapper;

import com.by.zx.model.entity.base.ProductUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductUnitMapper {

    //查询所有单元数据
    List<ProductUnit> findAll();
}
