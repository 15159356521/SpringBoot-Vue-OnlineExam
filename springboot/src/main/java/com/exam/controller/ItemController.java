package com.exam.controller;

import com.exam.entity.ApiResult;
import com.exam.entity.PaperManage;
import com.exam.service.PaperService;
import com.exam.serviceimpl.FillQuestionServiceImpl;
import com.exam.serviceimpl.JudgeQuestionServiceImpl;
import com.exam.serviceimpl.MultiQuestionServiceImpl;
import com.exam.serviceimpl.ShortQuestionServiceImpl;
import com.exam.util.ApiResultHandler;
import com.exam.vo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// 随机组卷(简单、一般、困难)
@RestController
public class ItemController {

    @Autowired
    MultiQuestionServiceImpl multiQuestionService;

    @Autowired
    FillQuestionServiceImpl fillQuestionService;

    @Autowired
    JudgeQuestionServiceImpl judgeQuestionService;

    @Autowired
    PaperService paperService;

    @Autowired
    ShortQuestionServiceImpl shortQuestionService;


    @PostMapping("/item")
    public ApiResult ItemController(@RequestBody Item item) {

        // 选择题
        Integer esayChangeNumber = item.getEsayChangeNumber();
        Integer commonChangeNumber = item.getCommonChangeNumber();
        Integer difficultyChangeNumber = item.getDifficultyChangeNumber();
        Integer changeScore = item.getChangeScore();

        // 填空题
        Integer esayFillNumber = item.getEsayFillNumber();
        Integer commonFillNumber = item.getCommonFillNumber();
        Integer difficultyFillNumber = item.getDifficultyFillNumber();
        Integer fillScore = item.getFillScore();

        // 判断题
        Integer esayJudgeNumber = item.getEsayJudgeNumber();
        Integer commonJudgeNumber = item.getCommonJudgeNumber();
        Integer difficultyJudgeNumber = item.getDifficultyJudgeNumber();
        Integer judgeScore = item.getJudgeScore();

        // 简答题
        Integer esayShorNumber = item.getEsayShorNumber();
        Integer commonShorNumber = item.getCommonShorNumber();
        Integer difficultyShorNumber = item.getDifficultyShorNumber();
        Integer shorScore = item.getShorScore();

        //出卷id
        Integer paperId = item.getPaperId();
        // 试卷总分
        Integer totalScore = item.getTotalScore();

        // 分数总和与试卷总分是否一致
        Integer totalChangeScore = (esayChangeNumber + commonChangeNumber + difficultyChangeNumber) * changeScore;
        Integer totalFillScore = (esayFillNumber + commonFillNumber + difficultyFillNumber) * fillScore;
        Integer totalJudgeScore = (esayJudgeNumber + commonJudgeNumber + difficultyJudgeNumber) * judgeScore;
        Integer totalShortScore = (esayShorNumber + commonShorNumber + difficultyShorNumber) * shorScore;
        if (totalScore != (totalChangeScore + totalFillScore + totalJudgeScore + totalShortScore)) {
            return ApiResultHandler.buildApiResult(400, "试卷总分应为" + totalScore + "分!", null);
        }

        // 简单选择题数据库获取
        List<Integer> esayChangeIds = multiQuestionService.findBySubjectEsayChange(item.getSubject(), esayChangeNumber);
        if (esayChangeIds == null) {
            return ApiResultHandler.buildApiResult(400, "简单选择题数据库获取失败", null);
        }
        for (Integer number : esayChangeIds) {
            PaperManage paperManage = new PaperManage(paperId, 1, number);
            int index = paperService.add(paperManage);
            if (index == 0)
                return ApiResultHandler.buildApiResult(400, "简单选择题组卷保存失败", null);
        }

        // 一般选择题数据库获取
        List<Integer> commonChangeIds = multiQuestionService.findBySubjectCommonChange(item.getSubject(), commonChangeNumber);
        if (commonChangeIds == null) {
            return ApiResultHandler.buildApiResult(400, "一般选择题数据库获取失败", null);
        }
        for (Integer number : commonChangeIds) {
            PaperManage paperManage = new PaperManage(paperId, 1, number);
            int index = paperService.add(paperManage);
            if (index == 0)
                return ApiResultHandler.buildApiResult(400, "一般选择题组卷保存失败", null);
        }

        // 困难选择题数据库获取
        List<Integer> difficultyChangeIds = multiQuestionService.findBySubjectDifficultyChange(item.getSubject(), difficultyChangeNumber);
        if (difficultyChangeIds == null) {
            return ApiResultHandler.buildApiResult(400, "困难选择题数据库获取失败", null);
        }
        for (Integer number : difficultyChangeIds) {
            PaperManage paperManage = new PaperManage(paperId, 1, number);
            int index = paperService.add(paperManage);
            if (index == 0)
                return ApiResultHandler.buildApiResult(400, "困难选择题组卷保存失败", null);
        }

        // 简单填空题数据库获取
        List<Integer> esayFillIds = fillQuestionService.findBySubjectEsayFill(item.getSubject(), esayFillNumber);
        if (esayFillIds == null)
            return ApiResultHandler.buildApiResult(400, "简单填空题数据库获取失败", null);
        for (Integer fillNum : esayFillIds) {
            PaperManage paperManage = new PaperManage(paperId, 2, fillNum);
            int index = paperService.add(paperManage);
            if (index == 0)
                return ApiResultHandler.buildApiResult(400, "简单填空题组卷保存失败", null);
        }

        // 一般填空题数据库获取
        List<Integer> commonFillIds = fillQuestionService.findBySubjectCommonFill(item.getSubject(), commonFillNumber);
        if (commonFillIds == null)
            return ApiResultHandler.buildApiResult(400, "一般填空题数据库获取失败", null);
        for (Integer fillNum : commonFillIds) {
            PaperManage paperManage = new PaperManage(paperId, 2, fillNum);
            int index = paperService.add(paperManage);
            if (index == 0)
                return ApiResultHandler.buildApiResult(400, "一般填空题组卷保存失败", null);
        }

        // 困难填空题数据库获取
        List<Integer> difficultyFillIds = fillQuestionService.findBySubjectDifficultyFill(item.getSubject(), difficultyFillNumber);
        if (difficultyFillIds == null)
            return ApiResultHandler.buildApiResult(400, "困难填空题数据库获取失败", null);
        for (Integer fillNum : difficultyFillIds) {
            PaperManage paperManage = new PaperManage(paperId, 2, fillNum);
            int index = paperService.add(paperManage);
            if (index == 0)
                return ApiResultHandler.buildApiResult(400, "困难填空题组卷保存失败", null);
        }

        // 简单判断题数据库获取
        List<Integer> esayJudgeIds = judgeQuestionService.findBySubjectEsayJudge(item.getSubject(), esayJudgeNumber);
        if (esayJudgeIds == null)
            return ApiResultHandler.buildApiResult(400, "简单判断题数据库获取失败", null);
        for (Integer judge : esayJudgeIds) {
            PaperManage paperManage = new PaperManage(paperId, 3, judge);
            int index = paperService.add(paperManage);
            if (index == 0)
                return ApiResultHandler.buildApiResult(400, "简单判断题组卷保存失败", null);
        }

        // 一般判断题数据库获取
        List<Integer> commonJudgeIds = judgeQuestionService.findBySubjectCommonJudge(item.getSubject(), commonJudgeNumber);
        if (commonJudgeIds == null)
            return ApiResultHandler.buildApiResult(400, "一般判断题数据库获取失败", null);
        for (Integer judge : commonJudgeIds) {
            PaperManage paperManage = new PaperManage(paperId, 3, judge);
            int index = paperService.add(paperManage);
            if (index == 0)
                return ApiResultHandler.buildApiResult(400, "一般判断题组卷保存失败", null);
        }

        // 困难判断题数据库获取
        List<Integer> difficultyJudgeIds = judgeQuestionService.findBySubjectDifficultyJudge(item.getSubject(), difficultyJudgeNumber);
        if (difficultyJudgeIds == null)
            return ApiResultHandler.buildApiResult(400, "困难判断题数据库获取失败", null);
        for (Integer judge : difficultyJudgeIds) {
            PaperManage paperManage = new PaperManage(paperId, 3, judge);
            int index = paperService.add(paperManage);
            if (index == 0)
                return ApiResultHandler.buildApiResult(400, "困难判断题组卷保存失败", null);
        }

        // 简单简答题数据库获取
        List<Integer> esayShortIds = shortQuestionService.findBySubjectEsayShort(item.getSubject(), esayShorNumber);
        if (esayShortIds == null)
            return ApiResultHandler.buildApiResult(400, "简单简答题数据库获取失败", null);
        for (Integer shor : esayShortIds) {
            PaperManage paperManage = new PaperManage(paperId, 4, shor);
            int index = paperService.add(paperManage);
            if (index == 0)
                return ApiResultHandler.buildApiResult(400, "简单简答题组卷保存失败", null);
        }

        // 一般简答题数据库获取
        List<Integer> commonShortIds = shortQuestionService.findBySubjectCommonShort(item.getSubject(), commonShorNumber);
        if (commonShortIds == null)
            return ApiResultHandler.buildApiResult(400, "一般简答题数据库获取失败", null);
        for (Integer shor : commonShortIds) {
            PaperManage paperManage = new PaperManage(paperId, 4, shor);
            int index = paperService.add(paperManage);
            if (index == 0)
                return ApiResultHandler.buildApiResult(400, "一般简答题组卷保存失败", null);
        }

        // 困难简答题数据库获取
        List<Integer> difficultyShortIds = shortQuestionService.findBySubjectDifficultyShort(item.getSubject(), difficultyShorNumber);
        if (difficultyShortIds == null)
            return ApiResultHandler.buildApiResult(400, "困难简答题数据库获取失败", null);
        for (Integer shor : difficultyShortIds) {
            PaperManage paperManage = new PaperManage(paperId, 4, shor);
            int index = paperService.add(paperManage);
            if (index == 0)
                return ApiResultHandler.buildApiResult(400, "困难简答题组卷保存失败", null);
        }

        return ApiResultHandler.buildApiResult(200, "试卷随机组卷成功", null);
    }
}
