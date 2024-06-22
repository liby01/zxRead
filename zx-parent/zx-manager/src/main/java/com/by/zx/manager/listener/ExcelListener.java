package com.by.zx.manager.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.by.zx.manager.mapper.CategoryMapper;
import com.by.zx.model.vo.product.CategoryExcelVo;

import java.util.List;

// 监听器类，用于处理Excel文件读取
public class ExcelListener<T> implements ReadListener<T> {

    /**
     * 每隔100条数据存储到数据库，实际使用中可以设置为更大的值，如1000条，然后清理list，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    /**
     * 缓存的数据列表
     */
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    // 用于操作数据库的CategoryMapper实例
    private CategoryMapper categoryMapper;

    // 构造方法，传递CategoryMapper实例用于数据库操作
    public ExcelListener(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    // 读取Excel内容，
    // 从第二行开始读取，把每行读取内容封装到T对象里面
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        // 把每行数据对象T放到cachedDataList集合里面
        cachedDataList.add(t);
        // 达到BATCH_COUNT时，需要存储一次数据库，防止内存中数据量过大导致OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData(); // 调用保存数据方法
            // 清理cachedDataList，释放内存
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    //保存的方法
    private void saveData() {
        categoryMapper.batchInsert((List<CategoryExcelVo>)cachedDataList);
    }

    // 所有数据解析完后的操作
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 当所有数据都解析完后，确保剩余未保存的数据也被保存到数据库中
        saveData();
    }
}

