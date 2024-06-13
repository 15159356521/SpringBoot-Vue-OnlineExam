package com.exam.service;

import com.exam.entity.Speciality;

import java.util.List;

public interface SpecialityService {

    // 查询所有专业（用于下拉框信息获取）
    List<Speciality> findAll();

}
