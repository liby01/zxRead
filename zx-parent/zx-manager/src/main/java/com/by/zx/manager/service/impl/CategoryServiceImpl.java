package com.by.zx.manager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.by.zx.common.exception.DiyException;
import com.by.zx.manager.listener.ExcelListener;
import com.by.zx.manager.mapper.CategoryMapper;
import com.by.zx.manager.service.CategoryService;
import com.by.zx.model.entity.product.Category;
import com.by.zx.model.vo.common.ResultCodeEnum;
import com.by.zx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    // 注入CategoryMapper实例，用于数据库操作
    @Autowired
    private CategoryMapper categoryMapper;

    // 分类列表，每次查询一层数据
    @Override
    public List<Category> findCategoryList(Long parentId) {
        // 1 根据parentId条件值进行查询，返回list集合
        // SELECT * FROM category WHERE parent_id=id
        List<Category> categoryList = categoryMapper.selectCategoryByParentId(parentId); // 查询指定父分类ID的子分类列表

        // 2 遍历返回的list集合
        // 判断每个分类是否有下一层分类，如果有设置hasChildren = true
        if (!CollectionUtils.isEmpty(categoryList)) {
            categoryList.forEach(category -> {
                // 判断每个分类是否有下一层分类
                // SELECT count(*) FROM category WHERE parent_id=category.getId()
                int count = categoryMapper.selectCountByParentId(category.getId()); // 查询当前分类是否有子分类
                if (count > 0) { // 如果有子分类
                    category.setHasChildren(true); // 设置hasChildren为true
                } else {
                    category.setHasChildren(false); // 如果没有子分类，设置hasChildren为false
                }
            });
        }
        return categoryList; // 返回分类列表
    }



    // 导出数据
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            // 1 设置响应头信息和其他信息
            response.setContentType("application/vnd.ms-excel"); // 设置响应内容类型为Excel文件
            response.setCharacterEncoding("utf-8"); // 设置响应编码格式为UTF-8

            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("分类数据", "UTF-8"); // 对文件名进行URL编码，防止中文乱码

            // 1 设置响应的头信息
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx"); // 设置响应头，指定文件下载名
            // response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); // （可选）设置CORS响应头，允许前端获取Content-Disposition头

            // 2 调用mapper方法查询所有分类数据，返回list集合
            List<Category> categoryList = categoryMapper.selectAll(); // 从数据库中查询所有分类数据
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>(categoryList.size()); // 创建一个与分类数据等长的List用于存储转换后的数据

            // 3 遍历分类数据，将其转换为CategoryExcelVo对象并添加到列表中
            for (Category category : categoryList) {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                BeanUtils.copyProperties(category, categoryExcelVo, CategoryExcelVo.class); // 将Category对象的属性复制到CategoryExcelVo对象中
                categoryExcelVoList.add(categoryExcelVo); // 将转换后的对象添加到列表中
            }

            // 4 调用EasyExcel的write方法完成写的操作
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class) // 创建EasyExcel写实例，指定输出流和数据类型
                    .sheet("分类数据") // 指定Sheet名称
                    .doWrite(categoryExcelVoList); // 写入数据到Excel文件
        } catch (Exception e) {
            e.printStackTrace(); // 打印异常堆栈信息
            throw new DiyException(ResultCodeEnum.DATA_ERROR); // 抛出自定义异常
        }
    }

    // 导入分类数据的方法
    @Override
    public void importData(MultipartFile file) {
        // 创建Excel监听器实例，并传递categoryMapper用于数据库操作
        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener<>(categoryMapper);
        try {
            // 使用EasyExcel读取文件内容
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class, excelListener) // 读取文件输入流，指定数据类型和监听器
                    .sheet() // 读取默认的第一个Sheet
                    .doRead(); // 执行读取操作
        } catch (Exception e) {
            e.printStackTrace(); // 打印异常堆栈信息
            throw new DiyException(ResultCodeEnum.DATA_ERROR); // 抛出自定义异常
        }
    }


}
