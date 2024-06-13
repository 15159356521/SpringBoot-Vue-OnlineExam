package com.exam.controller;

import com.exam.entity.ApiResult;
import com.exam.entity.Speciality;
import com.exam.serviceimpl.SpecialityServiceImpl;
import com.exam.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SpecialityController {

    @Autowired
    private SpecialityServiceImpl specialityService;

    // 查询所有专业（用于下拉框信息获取）
    @GetMapping("/specialities")
    public ApiResult findAll() {
        List<Speciality> res = specialityService.findAll();
        return ApiResultHandler.buildApiResult(200, "查询所有专业", res);
    }

}
