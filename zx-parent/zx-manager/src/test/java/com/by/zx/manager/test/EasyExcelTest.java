package com.by.zx.manager.test;

import com.alibaba.excel.EasyExcel;
import com.by.zx.model.vo.product.CategoryExcelVo;

import java.util.ArrayList;
import java.util.List;

public class EasyExcelTest {

    public static void main(String[] args) {

        readExcel();
        //writeExcel();
    }

    //读操作
    public static void readExcel() {
        //1、定义读取Excel文件的位置
        String fileName = "F://git//excel//test.xlsx";
        //2、调用方法
        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener();
        EasyExcel.read(fileName, CategoryExcelVo.class,excelListener).sheet().doRead();
        List<CategoryExcelVo> data = excelListener.getData();
        System.out.println(data);
    }

    //写操作
    public static void writeExcel() {
        List<CategoryExcelVo> data = new ArrayList<>();
        data.add(new CategoryExcelVo(1L , "数码办公" , "",0L, 1, 1)) ;
        data.add(new CategoryExcelVo(11L , "华为手机" , "",1L, 1, 2)) ;
        EasyExcel.write("F://git//excel//out.xlsx", CategoryExcelVo.class).sheet("分类数据").doWrite(data);
    }
}
