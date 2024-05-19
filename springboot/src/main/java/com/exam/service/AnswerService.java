package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.vo.AnswerVO;

public interface AnswerService {

    IPage<AnswerVO> findAll(Page<AnswerVO> page); //查询所有答题信息
}
