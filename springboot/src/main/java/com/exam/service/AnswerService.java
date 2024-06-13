package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.vo.AnswerVO;

public interface AnswerService {

    //分页查询按科目实现 —— 题型归档信息
    IPage<AnswerVO> findAll(Page<AnswerVO> page, String subjects, String type);

    int delete(Integer questionId, String type);
}
