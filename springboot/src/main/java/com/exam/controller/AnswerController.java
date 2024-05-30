package com.exam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ApiResult;
import com.exam.serviceimpl.AnswerServiceImpl;
import com.exam.util.ApiResultHandler;
import com.exam.vo.AnswerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;  //这个是get请求
import org.springframework.web.bind.annotation.PathVariable; //这个是获取路径参数
import org.springframework.web.bind.annotation.RestController; //这个是返回json数据


@RestController
public class AnswerController {

    @Autowired
    private AnswerServiceImpl answerService;

    @GetMapping("/answers/{page}/{size}/{subject}/{type}")
    public ApiResult findAllQuestion(@PathVariable("page") Integer page, @PathVariable("size") Integer size ,@PathVariable("subject") String subject,@PathVariable("type") String type){
       Page<AnswerVO> answerVOPage = new Page<>(page,size);
       if(("null").equals(subject)){
           subject = null;
       }
       if(("null").equals(type)){
           type = "全部";
       }
       IPage<AnswerVO> answerVOIPage = answerService.findAll(answerVOPage,subject,type);
       return ApiResultHandler.buildApiResult(200,"查询所有题库",answerVOIPage);

    }
}
