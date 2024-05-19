package com.exam.vo;

import lombok.Data;

@Data
public class AnswerVO { //这个类是用来接收前端传来的数据的
    private String question; //问题
    private String subject; //科目
    private String score; //分数
    private String section; //章节
    private String level; //难度
    private String type; //类型
}
