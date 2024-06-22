package com.by.zx.manager.service;

import com.by.zx.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    //分类列表，每次查询一层数据
    List<Category> findCategoryList(Long parentId);

    //导出
    void exportData(HttpServletResponse response);

    //导入
    void importData(MultipartFile file);
}
