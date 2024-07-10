package com.exam.vo;

import lombok.Data;

// 随机组卷实现的实体类
@Data
public class Item {

    private String subject; //科目

    private Integer paperId; //试卷id

    private Integer totalScore; //试卷总分

    // 手动组卷 —— 选择题
    private Integer esayChangeNumber; //简单选择题数量
    private Integer commonChangeNumber; //一般选择题题数量
    private Integer difficultyChangeNumber; //困难选择题题数量
    private Integer changeScore; // 选择题分数

    // 手动组卷 —— 填空题
    private Integer esayFillNumber;
    private Integer commonFillNumber;
    private Integer difficultyFillNumber;
    private Integer fillScore;

    // 手动组卷 —— 判断题
    private Integer esayJudgeNumber;
    private Integer commonJudgeNumber;
    private Integer difficultyJudgeNumber;
    private Integer judgeScore;

    // 手动组卷 —— 简答题
    private Integer esayShorNumber;
    private Integer commonShorNumber;
    private Integer difficultyShorNumber;
    private Integer shorScore;

    // 随机组卷部分（题数）
    private Integer changeNumber;
    private Integer fillNumber;
    private Integer judgeNumber;
    private Integer shortNumber;

    // 随机组卷部分（单题分数）
    private Integer suiJiChangeScore;
    private Integer suiJiFillScore;
    private Integer suiJiJudgeScore;
    private Integer suiJiShortScore;
}
