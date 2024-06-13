package com.exam.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.exam.entity.ApiResult;
import com.exam.entity.FillQuestion;
import com.exam.entity.MultiQuestion;
import com.exam.serviceimpl.FillQuestionServiceImpl;
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
public class FillQuestionController {

    @Autowired
    private FillQuestionServiceImpl fillQuestionService;

    @PostMapping("/fillQuestion")
    public ApiResult add(@RequestBody FillQuestion fillQuestion) {
        int res = fillQuestionService.add(fillQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200,"添加成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"添加失败",res);
    }

    @GetMapping("/fillQuestionId")
    public ApiResult findOnlyQuestionId() {
        FillQuestion res = fillQuestionService.findOnlyQuestionId();
        return ApiResultHandler.buildApiResult(200,"查询成功",res);
}
//更新填空题
    @PostMapping("/updateFill")
    public ApiResult updateFill(@RequestBody FillQuestion fillQuestion) {
        int res = fillQuestionService.updateFill(fillQuestion);
        return ApiResultHandler.buildApiResult(200,"更新成功",res);
    }
    //导入填空题
    @PostMapping("/importFillQuestion")
    public ApiResult importExcel(MultipartFile file){
        try{
            //读取文件
            EasyExcel.read(file.getInputStream(), FillQuestion.class, new ReadListener() {
                @Override
                public void onException(Exception exception, AnalysisContext context) throws Exception {
                    exception.printStackTrace();
                }
                @Override
                public void invoke(Object data, AnalysisContext context) {
                    System.out.println(data);
                    FillQuestion fillQuestion = (FillQuestion) data;
                    fillQuestionService.add(fillQuestion);
                }
                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    System.out.println("填空题导入成功");
                }
            }).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ApiResultHandler.buildApiResult(200,"导入成功",null);
    }

    //查询填空题信息（用于题目更新 —— 模态框）
    @GetMapping("/findFillQuestion/{questionId}")
    public ApiResult findFillModel(@PathVariable("questionId") Integer questionId) {
        FillQuestion res = fillQuestionService.findFill(questionId);
        return ApiResultHandler.buildApiResult(200, "查询填空题成功", res);
    }

}
