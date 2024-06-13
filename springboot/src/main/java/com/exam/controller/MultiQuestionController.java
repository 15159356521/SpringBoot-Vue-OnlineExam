package com.exam.controller;

import com.exam.entity.ApiResult;
import com.exam.entity.MultiQuestion;
import com.exam.serviceimpl.MultiQuestionServiceImpl;
import com.exam.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MultiQuestionController {

    @Autowired
    private MultiQuestionServiceImpl multiQuestionService;

    @GetMapping("/multiQuestionId")
    public ApiResult findOnlyQuestion() {
        MultiQuestion res = multiQuestionService.findOnlyQuestionId();
        return ApiResultHandler.buildApiResult(200, "查询成功", res);
    }

    @PostMapping("/MultiQuestion")
    public ApiResult add(@RequestBody MultiQuestion multiQuestion) {
        int res = multiQuestionService.add(multiQuestion);
        if (res != 0) {

            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "添加失败", res);
    }

    //更新选择题
    @PostMapping("/updateChoiceQuestion")
    public ApiResult update(@RequestBody MultiQuestion multiQuestion) {
        System.out.println(multiQuestion);
        int res = multiQuestionService.updateMulti(multiQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "更新成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "更新失败", res);
    }

    //查询选择题信息（用于题目更新 —— 模态框）
    @GetMapping("/findChoiceQuestion/{questionId}")
    public ApiResult findChoiceModel(@PathVariable("questionId") Integer questionId) {
        MultiQuestion res = multiQuestionService.findMulti(questionId);
        return ApiResultHandler.buildApiResult(200, "查询选择题成功", res);
    }

}
