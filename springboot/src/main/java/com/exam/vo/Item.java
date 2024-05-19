package com.exam.vo;

import lombok.Data;

//题目模型
@Data
public class Item {

    private String subject; //科目

    private Integer paperId; //试卷id

    private Integer changeNumber; //变更题号

    private Integer fillNumber; //填空题号

    private Integer judgeNumber; //判断题号
}
