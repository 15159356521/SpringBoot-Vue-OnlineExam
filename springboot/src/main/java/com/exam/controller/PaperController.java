package com.exam.controller;

import com.exam.entity.*;
import com.exam.serviceimpl.*;
import com.exam.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 题库分类管理
@RestController
public class PaperController {

    @Autowired
    private PaperServiceImpl paperService;

    @Autowired
    private JudgeQuestionServiceImpl judgeQuestionService;

    @Autowired
    private MultiQuestionServiceImpl multiQuestionService;

    @Autowired
    private FillQuestionServiceImpl fillQuestionService;

    @Autowired
    private ShortQuestionServiceImpl shortQuestionService;


    @GetMapping("/papers")
    public ApiResult<PaperManage> findAll() {
        ApiResult res = ApiResultHandler.buildApiResult(200, "请求成功", paperService.findAll());
        return res;
    }

    // 获取试卷题目对于的题型
    @GetMapping("/paper/{paperId}/{questionType}")
    public ApiResult<PaperManage> findByIdFindQuestionType(@PathVariable Integer paperId, @PathVariable String questionType) {
        ApiResult res = ApiResultHandler.buildApiResult(200, "请求成功", paperService.findById(paperId));
        return res;
    }

    // 获取学生考卷题目信息
    @GetMapping("/paper/{paperId}")
    public Map<Integer, List<?>> findById(@PathVariable("paperId") Integer paperId) {
        List<MultiQuestion> multiQuestionRes = multiQuestionService.findByIdAndType(paperId);   //选择题题库 1
        List<FillQuestion> fillQuestionsRes = fillQuestionService.findByIdAndType(paperId);     //填空题题库 2
        List<JudgeQuestion> judgeQuestionRes = judgeQuestionService.findByIdAndType(paperId);   //判断题题库 3
        List<ShortQuestion> shortQuestionRes = shortQuestionService.findByIdAndType(paperId);   //简答题题库 4
        Map<Integer, List<?>> map = new HashMap<>();
        map.put(1, multiQuestionRes);
        map.put(2, fillQuestionsRes);
        map.put(3, judgeQuestionRes);
        map.put(4, shortQuestionRes);
        return map;
    }

    //添加题目进试卷
    @PostMapping("/paperManage")
    public ApiResult add(@RequestBody PaperManage paperManage) {
        int res = paperService.add(paperManage);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "添加失败", res);
    }

    //删除试卷题目
    @DeleteMapping("/paperManage/{paperId}/{questionId}/{questionType}")
    public ApiResult delete(@PathVariable("paperId") Integer paperId, @PathVariable("questionId") Integer questionId, @PathVariable("questionType") Integer questionType) {
        System.out.println(paperId + questionId);
        int res = paperService.deleteQuest(paperId, questionId, questionType);
        return ApiResultHandler.buildApiResult(200, "删除成功", res);
    }
}
