package com.by.zx.product.service.impl;

import com.alibaba.fastjson2.JSON;
import com.by.zx.model.entity.product.Category;
import com.by.zx.product.mapper.CategoryMapper;
import com.by.zx.product.service.CategoryService;
import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //查询所有1级分类
    @Override
    public List<Category> selectOneCategory() {

        // 1. 查询 Redis，是否已经有一级分类
        String categoryOneJson = redisTemplate.opsForValue().get("category:one");

        // 2. 如果 Redis 中包含一级分类，直接返回
        if (categoryOneJson != null && !categoryOneJson.isEmpty()) {
            // 将 JSON 字符串解析为 Category 对象列表
            List<Category> categoryList = JSON.parseArray(categoryOneJson, Category.class);
            return categoryList;
        }

        // 3. 如果 Redis 中没有一级分类，查询数据库
        List<Category> list = categoryMapper.selectOneCategory();

        // 将数据库查询的内容添加到 Redis 中，并设置过期时间为 7 天
        redisTemplate.opsForValue().set("category:one", JSON.toJSONString(list), 7, TimeUnit.DAYS);

        // 返回数据库查询的一级分类列表
        return list;
    }

    //获取分类树形数据
    // key的名称： category::all
    //查询所有分类的方法，并将结果缓存到名为 "category" 的缓存中，缓存键为 "all"
    @Cacheable(value = "category",key = "'all'")
    @Override
    public List<Category> findCategoryTree() {
        //获取所有分类数据
        List<Category> categoryList = categoryMapper.findAll();
        //获取全部一级分类
        List<Category> oneCategoryList = categoryList.stream().filter(item -> item.getParentId().longValue() ==  0).collect(Collectors.toList());
        //获取全部二级分类
        if(!CollectionUtils.isEmpty(oneCategoryList)) {
            oneCategoryList.forEach(oneCategory -> {
                List<Category> twoCategoryList = categoryList.stream().filter(item -> item.getParentId().longValue() == oneCategory.getId().longValue()).collect(Collectors.toList());
                oneCategory.setChildren(twoCategoryList);
                //获取全部三级分类
                if(!CollectionUtils.isEmpty(twoCategoryList)) {
                    twoCategoryList.forEach(twoCategory -> {
                        List<Category> threeCategoryList = categoryList.stream().filter(item -> item.getParentId().longValue() == twoCategory.getId().longValue()).collect(Collectors.toList());
                        twoCategory.setChildren(threeCategoryList);
                    });
                }
            });
        }
        return oneCategoryList;
    }
}
