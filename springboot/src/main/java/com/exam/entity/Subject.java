package com.exam.entity;

import lombok.Data;

@Data
public class Subject {
    private Integer subjectId;

    private String subjectName;

    // 数据是否存在（0 —— 不存在，1 —— 存在）
    private Integer isExists;
}
