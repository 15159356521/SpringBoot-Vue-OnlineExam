package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.JudgeQuestion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//判断题

@Mapper
public interface JudgeQuestionMapper {

    // 获取学生判断题信息
    @Select("select * from judge_question where questionId in (select questionId from paper_manage where questionType = 3 and paperId = #{paperId})")
    List<JudgeQuestion> findByIdAndType(Integer paperId);

    @Select("select * from judge_question")
    IPage<JudgeQuestion> findAll(Page page);

    /**
     * 查询最后一条记录的questionId
     *
     * @return JudgeQuestion
     */
    @Select("select questionId from judge_question order by questionId desc limit 1")
    JudgeQuestion findOnlyQuestionId();

    @Insert("insert into judge_question(subject,question,answer,analysis,level,section) values " +
            "(#{subject},#{question},#{answer},#{analysis},#{level},#{section})")
    int add(JudgeQuestion judgeQuestion);

    // 简单判断题 —— 随机生成对应科目数量
    @Select("select questionId from judge_question where subject in (select source from exam_manage where subject = #{subject}) and level in ('1','2') order by rand() desc limit #{pageNo}")
    List<Integer> findBySubjectEsayJudge(String subject, Integer pageNo);

    // 一般判断题 —— 随机生成对应科目数量
    @Select("select questionId from judge_question where subject in (select source from exam_manage where subject = #{subject}) and level in ('3','4') order by rand() desc limit #{pageNo}")
    List<Integer> findBySubjectCommonJudge(String subject, Integer pageNo);

    // 困难判断题 —— 随机生成对应科目数量
    @Select("select questionId from judge_question where subject in (select source from exam_manage where subject = #{subject}) and level in ('5') order by rand() desc limit #{pageNo}")
    List<Integer> findBySubjectDifficultyJudge(String subject, Integer pageNo);
}
