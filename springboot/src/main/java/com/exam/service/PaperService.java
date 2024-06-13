package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.PaperManage;

import java.util.List;

public interface PaperService {

    List<PaperManage> findAll();

    List<PaperManage> findById(Integer paperId);

    // 生成试卷中题目类型和题型对应的编号
    int add(PaperManage paperManage);
    //删除试卷中题目类型和题型对应的编号
    int deleteQuest(Integer paperId,Integer questionId);
}
