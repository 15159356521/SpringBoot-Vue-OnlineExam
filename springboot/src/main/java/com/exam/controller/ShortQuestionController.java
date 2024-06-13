package com.exam.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.exam.entity.ApiResult;
import com.exam.entity.ShortQuestion;
import com.exam.serviceimpl.ShortQuestionServiceImpl;
import com.exam.util.ApiResultHandler;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ShortQuestionController {
    private static final Logger log = LoggerFactory.getLogger(ShortQuestionController.class);

    @Autowired
    private ShortQuestionServiceImpl shortQuestionService;

    @PostMapping("/shortQuestion")
    public ApiResult add(@RequestBody ShortQuestion shortQuestion) {
        int res = shortQuestionService.add(shortQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "添加失败", res);
    }

    @GetMapping("/shortQuestionId")
    public ApiResult findOnlyQuestionId() {
        ShortQuestion res = shortQuestionService.findOnlyQuestionId();
        return ApiResultHandler.buildApiResult(200, "查询成功", res);
    }

    // 简答题相似度匹配
    @PostMapping("/autoShortQuestionScore")
    public ApiResult autoShortQuestionScore(@RequestBody String data) {
        try {
            JSONObject jsonObject = JSON.parseObject(data);
            String successAnswer = jsonObject.getObject("successAnswer", String.class);
            String userAnswer = jsonObject.getObject("userAnswer", String.class);
            JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
            // 计算Jaccard相似度
            // Jaccard相似度：用于计算两个集合之间的相似度，可以将字符串视为字符的集合。计算它们的交集和并集的比值。Jaccard相似度的取值范围是0到1，值越接近1表示相似度越高
            Double similarity = jaccardSimilarity.apply(userAnswer, successAnswer);
            // 将相似度转换为分数
            int score = (int) Math.round(similarity * 5);
            return ApiResultHandler.buildApiResult(200, "自动评分成功", score);

        } catch (Exception e) {
            log.error("自动评分失败：", e);

        }
        return ApiResultHandler.buildApiResult(400, "添加失败", null);
    }
    //更新简答题
    @PostMapping("/updateShort")
    public ApiResult updateShort(@RequestBody ShortQuestion shortQuestion) {
        int res = shortQuestionService.updateShort(shortQuestion);
        return ApiResultHandler.buildApiResult(200, "更新成功", res);
    }
}
