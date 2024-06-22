package com.by.zx.manager.mapper;

import com.by.zx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //根据id条件值进行查询，返回list集合
    List<Category> selectCategoryByParentId(Long parentId);

    //判断每个分类是否有下一层分类
    int selectCountByParentId(Long parentId);
}
