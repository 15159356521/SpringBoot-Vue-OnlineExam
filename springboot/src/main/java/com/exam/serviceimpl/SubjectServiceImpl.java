package com.exam.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.Subject;
import com.exam.mapper.SubjectMapper;
import com.exam.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Override
    public List<Subject> findBySubName(String subName) {
        return subjectMapper.findBySubName(subName);
    }

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public int add(Subject subject) {
        return subjectMapper.add(subject);
    }

    @Override
    public IPage<Subject> findAll(Page<Subject> page) {
        return subjectMapper.findAll(page);
    }

    @Override
    public Subject findById(Integer subjectId) {
        return subjectMapper.findById(subjectId);
    }

    @Override
    public int update(Subject subject) {
        return subjectMapper.update(subject);
    }

    @Override
    public int deleteById(Integer subjectId) {
        return subjectMapper.deleteById(subjectId);
    }

    @Override
    public List<Subject> findBySubjectName() {
        return subjectMapper.findBySubjectName();
    }
}
