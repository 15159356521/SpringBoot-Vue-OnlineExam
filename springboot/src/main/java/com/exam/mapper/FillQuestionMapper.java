package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.FillQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

//填空题
@Mapper
public interface FillQuestionMapper {

    // 获取学生填空题信息
    @Select("select * from fill_question where questionId in (select questionId from paper_manage where questionType = 2 and paperId = #{paperId})")
    List<FillQuestion> findByIdAndType(Integer paperId);

    @Select("select * from fill_question")
    IPage<FillQuestion> findAll(Page page);

    /**
     * 查询最后一条questionId
     *
     * @return FillQuestion
     */
    @Select("select questionId from fill_question order by questionId desc limit 1")
    FillQuestion findOnlyQuestionId();

    @Options(useGeneratedKeys = true, keyProperty = "questionId")
    @Insert("insert into fill_question(subject,question,answer,analysis,level,section) values " +
            "(#{subject,},#{question},#{answer},#{analysis},#{level},#{section})")
    int add(FillQuestion fillQuestion);

    // 简单填空题 —— 随机生成对应科目数量
    @Select("select questionId from fill_question where subject in (select source from exam_manage where subject = #{subject}) and level in ('1','2') order by rand() desc limit #{pageNo}")
    List<Integer> findBySubjectEsayFill(String subject, Integer pageNo);

    // 一般填空题 —— 随机生成对应科目数量
    @Select("select questionId from fill_question where subject in (select source from exam_manage where subject = #{subject}) and level in ('3','4') order by rand() desc limit #{pageNo}")
    List<Integer> findBySubjectCommonFill(String subject, Integer pageNo);

    // 困难填空题 —— 随机生成对应科目数量
    @Select("select questionId from fill_question where subject in (select source from exam_manage where subject = #{subject}) and level in ('5') order by rand() desc limit #{pageNo}")
    List<Integer> findBySubjectDifficultyFill(String subject, Integer pageNo);
    //更新填空题
    @Update("update fill_question set subject = #{subject},question = #{question},answer = #{answer},analysis = #{analysis},level = #{level},section = #{section} where questionId = #{questionId}")
    int updateFill(FillQuestion fillQuestion);
}
