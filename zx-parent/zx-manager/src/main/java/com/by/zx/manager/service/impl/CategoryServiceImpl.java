package com.by.zx.manager.service.impl;

import com.by.zx.manager.mapper.CategoryMapper;
import com.by.zx.manager.service.CategoryService;
import com.by.zx.model.entity.product.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    //分类列表，每次查询一层数据
    @Override
    public List<Category> findCategoryList(Long parentId) {
        //1 根据id条件值进行查询，返回list集合
        // SELECT * FROM category WHERE parent_id=id
        List<Category> categoryList = categoryMapper.selectCategoryByParentId(parentId);

        //2 遍历返回list集合，
        // 判断每个分类是否有下一层分类，如果有设置 hasChildren = true
        if(!CollectionUtils.isEmpty(categoryList)) {
            categoryList.forEach(category -> {
                // 判断每个分类是否有下一层分类
                //SELECT count(*) FROM category WHERE parent_id=1
                int count = categoryMapper.selectCountByParentId(category.getId());
                if(count > 0) {//下一层分类
                    category.setHasChildren(true);
                } else {
                    category.setHasChildren(false);
                }
            });
        }
        return categoryList;
    }
}
