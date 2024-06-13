package com.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 题型分类实体类

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperManage {
    private Integer paperId;

    private Integer questionType;

    private Integer questionId;


}