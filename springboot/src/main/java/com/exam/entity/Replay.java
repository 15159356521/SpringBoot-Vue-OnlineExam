package com.exam.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

// 讨论回复实体类

@Data
public class Replay {
    private Integer messageId;

    private Integer replayId;

    private String replay;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date replayTime;
}