package com.exam.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.exam.entity.ApiResult;
import com.exam.entity.MultiQuestion;
import com.exam.serviceimpl.MultiQuestionServiceImpl;
import com.exam.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class MultiQuestionController {

    @Autowired
    private MultiQuestionServiceImpl multiQuestionService;

    @GetMapping("/multiQuestionId")
    public ApiResult findOnlyQuestion() {
        MultiQuestion res = multiQuestionService.findOnlyQuestionId();
        return ApiResultHandler.buildApiResult(200, "查询成功", res);
    }

    // 添加选择题
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

    //表格导入选择题
    @PostMapping("/importMultiQuestion")
    public ApiResult importExcel(MultipartFile file) {
        try {
            //读取文件
            EasyExcel.read(file.getInputStream(), MultiQuestion.class, new ReadListener() {
                @Override
                public void onException(Exception exception, AnalysisContext context) throws Exception {
                    exception.printStackTrace();
                }

                @Override
                public void invoke(Object o, AnalysisContext analysisContext) { //o就是读取到的一行数据
                    System.out.println(o);
                    MultiQuestion multiQuestion = (MultiQuestion) o;
                    multiQuestionService.add(multiQuestion);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                }
            }).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return ApiResultHandler.buildApiResult(200, "导入成功", null);
    }

    // 添加选择题进相应科目的题库及考试中（调用之前写好的两个接口）
    @PostMapping("/addMultiQuestionEnterExam")
    public ApiResult addMultiQuestionEnterExam(@RequestBody MultiQuestion multiQuestion) {
        int count = multiQuestionService.add(multiQuestion);
        MultiQuestion res = multiQuestionService.findOnlyQuestionId();
        if (count != 0) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "添加失败", res);
    }

}
