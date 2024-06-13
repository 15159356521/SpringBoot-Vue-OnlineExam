package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.FillQuestion;

import java.util.List;

public interface FillQuestionService {

    List<FillQuestion> findByIdAndType(Integer paperId);

    IPage<FillQuestion> findAll(Page<FillQuestion> page);

    FillQuestion findOnlyQuestionId();

    int add(FillQuestion fillQuestion);

    // 简单填空题 —— 随机生成对应科目数量
    List<Integer> findBySubjectEsayFill(String subject, Integer pageNo);

    // 一般填空题 —— 随机生成对应科目数量
    List<Integer> findBySubjectCommonFill(String subject, Integer pageNo);

    // 困难填空题 —— 随机生成对应科目数量
    List<Integer> findBySubjectDifficultyFill(String subject, Integer pageNo);

}
