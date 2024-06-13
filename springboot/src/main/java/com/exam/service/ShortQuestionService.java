package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ShortQuestion;

import java.util.List;

public interface ShortQuestionService {

    List<ShortQuestion> findByIdAndType(Integer paperId);

    IPage<ShortQuestion> findAll(Page<ShortQuestion> page);

    ShortQuestion findOnlyQuestionId();

    int add(ShortQuestion ShortQuestion);

    // 简单简答题 —— 随机生成对应科目数量
    List<Integer> findBySubjectEsayShort(String subject, Integer pageNo);

    // 一般简答题 —— 随机生成对应科目数量
    List<Integer> findBySubjectCommonShort(String subject, Integer pageNo);

    // 困难简答题 —— 随机生成对应科目数量
    List<Integer> findBySubjectDifficultyShort(String subject, Integer pageNo);
}
