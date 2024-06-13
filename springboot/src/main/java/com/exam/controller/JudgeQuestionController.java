package com.exam.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.exam.entity.ApiResult;
import com.exam.entity.JudgeQuestion;
import com.exam.serviceimpl.JudgeQuestionServiceImpl;
import com.exam.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class JudgeQuestionController {

    @Autowired
    private JudgeQuestionServiceImpl judgeQuestionService;

    @PostMapping("/judgeQuestion")
    public ApiResult add(@RequestBody JudgeQuestion judgeQuestion) {
        int res = judgeQuestionService.add(judgeQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200,"添加成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"添加失败",res);
    }

    @GetMapping("/judgeQuestionId")
    public ApiResult findOnlyQuestionId() {
        JudgeQuestion res = judgeQuestionService.findOnlyQuestionId();
        return  ApiResultHandler.buildApiResult(200,"查询成功",res);
    }
    //更新判断题
    @PostMapping("/updateJudge")
    public ApiResult updateJudge(@RequestBody JudgeQuestion judgeQuestion) {
        int res = judgeQuestionService.updateJudge(judgeQuestion);
        return ApiResultHandler.buildApiResult(200,"更新成功",res);
    }
    //导入判断题
    @PostMapping("/importJudgeQuestion")
    public ApiResult importExcel(MultipartFile file){
        try{
            //读取文件
            EasyExcel.read(file.getInputStream(), JudgeQuestion.class, new ReadListener() {
                @Override
                public void onException(Exception exception, AnalysisContext context) throws Exception {
                    exception.printStackTrace();
                }
                @Override
                public void invoke(Object data, AnalysisContext context) {
                    JudgeQuestion judgeQuestion = (JudgeQuestion) data;
                    judgeQuestionService.add(judgeQuestion);
                }
                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    System.out.println("读取完成");
                }
            }).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ApiResultHandler.buildApiResult(200,"导入成功",null);
    }
}
