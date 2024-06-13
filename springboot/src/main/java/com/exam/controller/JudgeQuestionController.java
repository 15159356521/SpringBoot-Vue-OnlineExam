package com.exam.controller;

import com.exam.entity.ApiResult;
import com.exam.entity.JudgeQuestion;
import com.exam.entity.MultiQuestion;
import com.exam.serviceimpl.JudgeQuestionServiceImpl;
import com.exam.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class JudgeQuestionController {

    @Autowired
    private JudgeQuestionServiceImpl judgeQuestionService;

    @PostMapping("/judgeQuestion")
    public ApiResult add(@RequestBody JudgeQuestion judgeQuestion) {
        int res = judgeQuestionService.add(judgeQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "添加失败", res);
    }

    @GetMapping("/judgeQuestionId")
    public ApiResult findOnlyQuestionId() {
        JudgeQuestion res = judgeQuestionService.findOnlyQuestionId();
        return ApiResultHandler.buildApiResult(200, "查询成功", res);
    }

    //更新判断题
    @PostMapping("/updateJudge")
    public ApiResult updateJudge(@RequestBody JudgeQuestion judgeQuestion) {
        int res = judgeQuestionService.updateJudge(judgeQuestion);
        return ApiResultHandler.buildApiResult(200, "更新成功", res);
    }

    //查询判断题信息（用于题目更新 —— 模态框）
    @GetMapping("/findJudgeQuestion/{questionId}")
    public ApiResult findJudgeModel(@PathVariable("questionId") Integer questionId) {
        JudgeQuestion res = judgeQuestionService.findJudge(questionId);
        return ApiResultHandler.buildApiResult(200, "查询判断题成功", res);
    }

}
