package com.exam.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.mapper.AnswerMapper;
import com.exam.service.AnswerService;
import com.exam.vo.AnswerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public IPage<AnswerVO> findAll(Page<AnswerVO> page, String subjects, String type, String level) {
        return answerMapper.findAll(page, subjects, type, level);
    }

    @Override
    public int delete(Integer questionId, String type) {
        return answerMapper.delete(questionId, type);
    }

}
