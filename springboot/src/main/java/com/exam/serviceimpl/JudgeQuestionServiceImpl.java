package com.exam.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.FillQuestion;
import com.exam.entity.JudgeQuestion;
import com.exam.entity.MultiQuestion;
import com.exam.mapper.JudgeQuestionMapper;
import com.exam.service.JudgeQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JudgeQuestionServiceImpl implements JudgeQuestionService {

    @Autowired
    private JudgeQuestionMapper judgeQuestionMapper;

    @Override
    public List<JudgeQuestion> findByIdAndType(Integer paperId) {
        return judgeQuestionMapper.findByIdAndType(paperId);
    }

    @Override
    public IPage<JudgeQuestion> findAll(Page<JudgeQuestion> page) {
        return judgeQuestionMapper.findAll(page);
    }

    @Override
    public JudgeQuestion findOnlyQuestionId() {
        return judgeQuestionMapper.findOnlyQuestionId();
    }

    @Override
    public int add(JudgeQuestion judgeQuestion) {
        return judgeQuestionMapper.add(judgeQuestion);
    }

    @Override
    public List<Integer> findBySubjectEsayJudge(String subject, Integer pageNo) {
        return judgeQuestionMapper.findBySubjectEsayJudge(subject, pageNo);
    }

    @Override
    public List<Integer> findBySubjectCommonJudge(String subject, Integer pageNo) {
        return judgeQuestionMapper.findBySubjectCommonJudge(subject, pageNo);
    }

    @Override
    public List<Integer> findBySubjectDifficultyJudge(String subject, Integer pageNo) {
        return judgeQuestionMapper.findBySubjectDifficultyJudge(subject, pageNo);
    }

    @Override
    public int updateJudge(JudgeQuestion judgeQuestion) {
        return judgeQuestionMapper.updateJudge(judgeQuestion);
    }

    @Override
    public JudgeQuestion findJudge(Integer questionId) {
        return judgeQuestionMapper.findJudge(questionId);
    }

}
