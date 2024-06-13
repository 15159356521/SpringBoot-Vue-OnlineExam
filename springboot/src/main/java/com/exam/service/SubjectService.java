package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.Subject;

import java.util.List;

public interface SubjectService {

    // 添加科目名称
    int add(Subject subject);

    // 查询所有科目（存在的）
    IPage<Subject> findAll(Page<Subject> page);

    // 查询需要修改的科目编号
    Subject findById(Integer subjectId);

    // 确认更新科目名称信息
    int update(Subject subject);

    // 删除科目名称信息（假删除，数据库仍然存在）
    int deleteById(Integer subjectId);

    // 查询所有科目名称（用于下拉框信息获取）
    List<Subject> findBySubjectName();
}
