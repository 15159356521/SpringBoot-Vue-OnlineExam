package com.exam.entity;

import lombok.Data;

@Data
public class Teacher {
    private Integer teacherId;

    private String teacherName;

    // 所属学院
    private String institute;

    private String sex;

    private String tel;

    private String email;

    private String pwd;

    private String cardId;

    private String type;

    private String role;
}