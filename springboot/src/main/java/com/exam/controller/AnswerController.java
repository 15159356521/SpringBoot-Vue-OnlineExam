package com.exam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ApiResult;
import com.exam.serviceimpl.AnswerServiceImpl;
import com.exam.util.ApiResultHandler;
import com.exam.vo.AnswerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;  //这个是get请求
import org.springframework.web.bind.annotation.PathVariable; //这个是获取路径参数
import org.springframework.web.bind.annotation.RestController; //这个是返回json数据


@RestController
public class AnswerController {

    @Autowired
    private AnswerServiceImpl answerService;

    //分页查询按科目实现 —— 题型归档信息
    @GetMapping("/answers/{page}/{size}/{subjects}/{type}")
    public ApiResult findAllQuestion(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @PathVariable("subjects") String subjects, @PathVariable("type") String type) {
        Page<AnswerVO> answerVOPage = new Page<>(page, size);
        if (subjects.equals("全部")) {
            subjects = "%";
        }
        IPage<AnswerVO> answerVOIPage = answerService.findAll(answerVOPage, subjects, type);
        return ApiResultHandler.buildApiResult(200, "按科目查询题库", answerVOIPage);

    }

    @DeleteMapping("/answers/{questionId}/{type}")
    public ApiResult deleteById(@PathVariable("questionId") Integer questionId, @PathVariable("type") String type) {
        int res = answerService.delete(questionId, type);
        return ApiResultHandler.buildApiResult(200, "删除成功", res);
    }

}
