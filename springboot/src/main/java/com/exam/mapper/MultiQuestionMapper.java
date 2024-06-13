package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.MultiQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

//选择题
@Mapper
public interface MultiQuestionMapper {
    /**
     * select * from multiquestions where questionId in (
     * select questionId from papermanage where questionType = 1 and paperId = 1001
     * )
     */
    // 获取学生选择题信息
    @Select("select * from multi_question where questionId in (select questionId from paper_manage where questionType = 1 and paperId = #{paperId})")
    List<MultiQuestion> findByIdAndType(Integer PaperId);

    @Select("select * from multi_question")
    IPage<MultiQuestion> findAll(Page page);

    /**
     * 查询最后一条记录的questionId
     *
     * @return MultiQuestion
     */
    @Select("select questionId from multi_question order by questionId desc limit 1")
    MultiQuestion findOnlyQuestionId();

    @Options(useGeneratedKeys = true, keyProperty = "questionId")
    @Insert("insert into multi_question(subject,question,answerA,answerB,answerC,answerD,rightAnswer,analysis,section,level) " +
            "values(#{subject},#{question},#{answerA},#{answerB},#{answerC},#{answerD},#{rightAnswer},#{analysis},#{section},#{level})")
    int add(MultiQuestion multiQuestion);

    // 简单选择题 —— 随机生成对应科目数量
    @Select("select questionId from multi_question where subject in (select source from exam_manage where subject = #{subject}) and level in ('1','2') order by rand() desc limit #{pageNo}")
    List<Integer> findBySubjectEsayChange(String subject, Integer pageNo);

    // 一般选择题 —— 随机生成对应科目数量
    @Select("select questionId from multi_question where subject in (select source from exam_manage where subject = #{subject}) and level in ('3','4') order by rand() desc limit #{pageNo}")
    List<Integer> findBySubjectCommonChange(String subject, Integer pageNo);

    // 困难选择题 —— 随机生成对应科目数量
    @Select("select questionId from multi_question where subject in (select source from exam_manage where subject = #{subject}) and level in ('5') order by rand() desc limit #{pageNo}")
    List<Integer> findBySubjectDifficultyChange(String subject, Integer pageNo);

    //更新选择题
    @Update("update multi_question set subject = #{subject},question = #{question},answerA = #{answerA},answerB = #{answerB},answerC = #{answerC},answerD = #{answerD},rightAnswer = #{rightAnswer},analysis = #{analysis},section = #{section},level = #{level} where questionId = #{questionId}")
    int updateMulti(MultiQuestion multiQuestion);

    // 查询单道选择题的信息
    @Select("select * from multi_question where questionId = #{questionId}")
    MultiQuestion findMulti(Integer questionId);

}
