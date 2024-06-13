package com.exam.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

// 导出excel实体类

@Data
public class TableExcel {
    @ExcelProperty("学号")
    @ColumnWidth(20)
    private String StudentId;
    @ExcelProperty("姓名")
    @ColumnWidth(20)
    private String StudentName;
    @ExcelProperty("专业")
    @ColumnWidth(20)
    private String Subject;
    @ExcelProperty("成绩")
    @ColumnWidth(20)
    private String PtScore;
}
