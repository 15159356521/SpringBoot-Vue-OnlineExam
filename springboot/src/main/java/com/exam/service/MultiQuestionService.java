package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.MultiQuestion;

import java.util.List;

public interface MultiQuestionService {

    List<MultiQuestion> findByIdAndType(Integer PaperId);

    IPage<MultiQuestion> findAll(Page<MultiQuestion> page);

    MultiQuestion findOnlyQuestionId();

    int add(MultiQuestion multiQuestion);

    // 简单选择题 —— 随机生成对应数量
    List<Integer> findBySubjectEsayChange(String subject, Integer pageNo);

    // 一般选择题 —— 随机生成对应数量
    List<Integer> findBySubjectCommonChange(String subject, Integer pageNo);

    // 困难选择题 —— 随机生成对应数量
    List<Integer> findBySubjectDifficultyChange(String subject, Integer pageNo);
}
