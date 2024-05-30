package com.exam.entity;

import lombok.Data;

@Data
public class ShortQuestion {
    private Integer questionId;

    private String subject;

    private String question;

    private String answer;

    private Integer score;

    private String level;

    private String section;

    private String analysis; //题目解析
}
