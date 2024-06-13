package com.exam.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ShortQuestion;
import com.exam.mapper.ShortQuestionMapper;
import com.exam.service.ShortQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortQuestionServiceImpl implements ShortQuestionService {

    @Autowired
    private ShortQuestionMapper shortQuestionMapper;

    @Override
    public List<ShortQuestion> findByIdAndType(Integer paperId) {
        return shortQuestionMapper.findByIdAndType(paperId);
    }

    @Override
    public IPage<ShortQuestion> findAll(Page<ShortQuestion> page) {
        return shortQuestionMapper.findAll(page);
    }

    @Override
    public ShortQuestion findOnlyQuestionId() {
        return shortQuestionMapper.findOnlyQuestionId();
    }

    @Override
    public int add(ShortQuestion shortQuestion) {
        return shortQuestionMapper.add(shortQuestion);
    }

    @Override
    public List<Integer> findBySubjectEsayShort(String subject, Integer pageNo) {
        return shortQuestionMapper.findBySubjectEsayShort(subject, pageNo);
    }

    @Override
    public List<Integer> findBySubjectCommonShort(String subject, Integer pageNo) {
        return shortQuestionMapper.findBySubjectCommonShort(subject, pageNo);
    }

    @Override
    public List<Integer> findBySubjectDifficultyShort(String subject, Integer pageNo) {
        return shortQuestionMapper.findBySubjectDifficultyShort(subject, pageNo);
    }
}
