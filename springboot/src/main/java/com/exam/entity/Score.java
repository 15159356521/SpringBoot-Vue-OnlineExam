package com.exam.entity;

import lombok.Data;

// 考试成绩实体类

@Data
public class Score {
    private Integer examCode;

    private Integer studentId;

    private String subject;

    private Integer ptScore;

    private Integer etScore;

    private Integer score;

    private Integer scoreId;

    private String answerDate;

    @Data
    public static class StudentScore {
        private String StudentName;
        private Integer Score;
        private String Subject;

    }
}