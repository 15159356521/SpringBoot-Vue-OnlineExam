package com.exam.controller;

import com.exam.entity.ApiResult;
import com.exam.entity.ShortQuestion;
import com.exam.serviceimpl.ShortQuestionServerImpl;
import com.exam.util.ApiResultHandler;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class shortQuestionController {
    private static final Logger log = LoggerFactory.getLogger(shortQuestionController.class);
    private ShortQuestionServerImpl shortQuestionServer;

    @PostMapping("/shortQuestion")
    public ApiResult add(@RequestBody ShortQuestion shortQuestion) {
        int res = shortQuestionServer.add(shortQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200,"添加成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"添加失败",res);
    }

    @GetMapping("/shortQuestionId")
    public ApiResult findOnlyQuestionId() {
        ShortQuestion res = shortQuestionServer.findOnlyQuestionId();
        return ApiResultHandler.buildApiResult(200,"查询成功",res);
    }

    @PostMapping("/autoScore")
    public ApiResult autoScore(@RequestBody String successAnswer, String userAnswer ) {
        try {
            JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
            // 计算Jaccard相似度
            // Jaccard相似度：用于计算两个集合之间的相似度，可以将字符串视为字符的集合。计算它们的交集和并集的比值。Jaccard相似度的取值范围是0到1，值越接近1表示相似度越高
            Double similarity = jaccardSimilarity.apply(userAnswer, successAnswer);
            // 将相似度转换为分数，这里我们假设0.5以上的相似度可以获得部分分数
            double score = similarity * 100;
            if (score <= 50) {
                score = 0;
            }
               return ApiResultHandler.buildApiResult(200,"自动评分成功", score);

        } catch (Exception e) {
            log.error("自动评分失败：", e);

        }
        return ApiResultHandler.buildApiResult(400,"添加失败", null);
    }
}
