package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.JudgeQuestion;

import java.util.List;

public interface JudgeQuestionService {

    List<JudgeQuestion> findByIdAndType(Integer paperId);

    IPage<JudgeQuestion> findAll(Page<JudgeQuestion> page);

    JudgeQuestion findOnlyQuestionId();

    int add(JudgeQuestion judgeQuestion);

    // 简单判断题 —— 随机生成对应科目数量
    List<Integer> findBySubjectEsayJudge(String subject, Integer pageNo);

    // 一般判断题 —— 随机生成对应科目数量
    List<Integer> findBySubjectCommonJudge(String subject, Integer pageNo);

    // 困难判断题 —— 随机生成对应科目数量
    List<Integer> findBySubjectDifficultyJudge(String subject, Integer pageNo);
}
