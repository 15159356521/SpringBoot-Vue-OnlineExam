package com.exam.entity;

import lombok.Data;

//填空题实体类
@Data
public class FillQuestion {
    private Integer questionId;

    private String subject;

    private String question;

    private String answer;// 答案

    private Integer score;

    private String level;

    private String section; //章节

    private String analysis; //题目解析
}
