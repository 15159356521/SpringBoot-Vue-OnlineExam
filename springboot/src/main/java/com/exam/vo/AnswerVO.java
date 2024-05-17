package com.exam.vo;

import lombok.Data;

@Data
public class AnswerVO { //这个类是用来接收前端传来的数据的
    private String question;
    private String subject;
    private String score;
    private String section;
    private String level;
    private String type;
}
