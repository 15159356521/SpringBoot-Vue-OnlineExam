package com.exam.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.MultiQuestion;
import com.exam.mapper.MultiQuestionMapper;
import com.exam.service.MultiQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MultiQuestionServiceImpl implements MultiQuestionService {

    @Autowired
    private MultiQuestionMapper multiQuestionMapper;

    @Override
    public List<MultiQuestion> findByIdAndType(Integer PaperId) {
        return multiQuestionMapper.findByIdAndType(PaperId);
    }

    @Override
    public IPage<MultiQuestion> findAll(Page<MultiQuestion> page) {
        return multiQuestionMapper.findAll(page);
    }

    @Override
    public MultiQuestion findOnlyQuestionId() {
        return multiQuestionMapper.findOnlyQuestionId();
    }

    @Override
    public int add(MultiQuestion multiQuestion) {
        return multiQuestionMapper.add(multiQuestion);
    }

    @Override
    public List<Integer> findBySubjectEsayChange(String subject, Integer pageNo) {
        return multiQuestionMapper.findBySubjectEsayChange(subject, pageNo);
    }

    @Override
    public List<Integer> findBySubjectCommonChange(String subject, Integer pageNo) {
        return multiQuestionMapper.findBySubjectCommonChange(subject, pageNo);
    }

    @Override
    public List<Integer> findBySubjectDifficultyChange(String subject, Integer pageNo) {
        return multiQuestionMapper.findBySubjectDifficultyChange(subject, pageNo);
    }
    //更新选择题
    @Override
    public int updateMulti(MultiQuestion multiQuestion) {
        System.out.println("现在是在impl位置");
        return multiQuestionMapper.updateMulti(multiQuestion);
    }

}
