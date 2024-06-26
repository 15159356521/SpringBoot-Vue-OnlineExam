package com.exam.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.FillQuestion;
import com.exam.entity.MultiQuestion;
import com.exam.mapper.FillQuestionMapper;
import com.exam.service.FillQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FillQuestionServiceImpl implements FillQuestionService {

    @Autowired
    private FillQuestionMapper fillQuestionMapper;

    @Override
    public List<FillQuestion> findByIdAndType(Integer paperId) {
        return fillQuestionMapper.findByIdAndType(paperId);
    }

    @Override
    public IPage<FillQuestion> findAll(Page<FillQuestion> page) {
        return fillQuestionMapper.findAll(page);
    }

    @Override
    public FillQuestion findOnlyQuestionId() {
        return fillQuestionMapper.findOnlyQuestionId();
    }

    @Override
    public int add(FillQuestion fillQuestion) {
        return fillQuestionMapper.add(fillQuestion);
    }

    @Override
    public List<Integer> findBySubjectEsayFill(String subject, Integer pageNo) {
        return fillQuestionMapper.findBySubjectEsayFill(subject, pageNo);
    }

    @Override
    public List<Integer> findBySubjectCommonFill(String subject, Integer pageNo) {
        return fillQuestionMapper.findBySubjectCommonFill(subject, pageNo);
    }

    @Override
    public List<Integer> findBySubjectDifficultyFill(String subject, Integer pageNo) {
        return fillQuestionMapper.findBySubjectDifficultyFill(subject, pageNo);
    }

    @Override
    public int updateFill(FillQuestion fillQuestion) {
        return fillQuestionMapper.updateFill(fillQuestion);
    }

    @Override
    public FillQuestion findFill(Integer questionId) {
        return fillQuestionMapper.findFill(questionId);
    }
}
