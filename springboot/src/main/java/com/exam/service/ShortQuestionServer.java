package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ShortQuestion;

import java.util.List;

public interface ShortQuestionServer {

    List<ShortQuestion> findByIdAndType(Integer paperId);

    IPage<ShortQuestion> findAll(Page<ShortQuestion> page);

    ShortQuestion findOnlyQuestionId();

    int add(ShortQuestion ShortQuestion);

    List<Integer> findBySubject(String subject,Integer pageNo);
}
