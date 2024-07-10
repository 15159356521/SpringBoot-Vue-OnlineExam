package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ExamManage;

import java.util.List;

public interface ExamManageService {

    /**
     * 不分页查询所有考试信息
     */
    List<ExamManage> findAll();

    // 分页查询所有试卷
    IPage<ExamManage> findAll(Page<ExamManage> page);

    //分页查询按科目试卷归档信息
    IPage<ExamManage> findBySubject(Page<ExamManage> page, String subjects);

    ExamManage findById(Integer examCode);

    int delete(Integer examCode);

    // 修改试卷信息
    int update(ExamManage exammanage);

    int add(ExamManage exammanage);

    ExamManage findOnlyPaperId();


}
