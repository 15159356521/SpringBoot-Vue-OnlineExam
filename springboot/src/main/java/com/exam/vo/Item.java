package com.exam.vo;

import lombok.Data;

// 随机组卷实现的实体类
@Data
public class Item {

    private String subject; //科目

    private Integer paperId; //试卷id

    private Integer totalScore; //试卷总分

    // 随机组卷 —— 选择题
    private Integer esayChangeNumber; //简单选择题数量
    private Integer commonChangeNumber; //一般选择题题数量
    private Integer difficultyChangeNumber; //困难选择题题数量
    private Integer changeScore; // 选择题分数

    // 随机组卷 —— 填空题
    private Integer esayFillNumber;
    private Integer commonFillNumber;
    private Integer difficultyFillNumber;
    private Integer fillScore;

    // 随机组卷 —— 判断题
    private Integer esayJudgeNumber;
    private Integer commonJudgeNumber;
    private Integer difficultyJudgeNumber;
    private Integer judgeScore;

    // 随机组卷 —— 简答题
    private Integer esayShorNumber;
    private Integer commonShorNumber;
    private Integer difficultyShorNumber;
    private Integer shorScore;
    
}
