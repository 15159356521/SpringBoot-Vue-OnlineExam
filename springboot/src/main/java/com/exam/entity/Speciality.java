package com.exam.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

// 学院及所属专业实体类

@Data
public class Speciality {
    private String collegeId;
    private String speciality;
    private String collegeName;
}
