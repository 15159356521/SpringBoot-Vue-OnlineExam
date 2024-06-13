package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ShortQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

//简答题
@Mapper
public interface ShortQuestionMapper {

    // 获取学生简答题信息
    @Select("select * from short_question where questionId in (select questionId from paper_manage where questionType = 4 and paperId = #{paperId})")
    List<ShortQuestion> findByIdAndType(Integer paperId);

    @Select("select * from short_question")
    IPage<ShortQuestion> findAll(Page page);

    /**
     * 查询最后一条questionId
     *
     * @return shortQuestion
     */
    @Select("select questionId from short_question order by questionId desc limit 1")
    ShortQuestion findOnlyQuestionId();

    @Options(useGeneratedKeys = true, keyProperty = "questionId")
    @Insert("insert into short_question(subject,question,answer,analysis,level,section) values " +
            "(#{subject,},#{question},#{answer},#{analysis},#{level},#{section})")
    int add(ShortQuestion shortQuestion);

    // 简单简答题 —— 随机生成对应科目数量
    //@Select("select questionId from short_question where subject in (select source from exam_manage where subject = #{subject}) and level in ('1','2') order by rand() desc limit #{pageNo}")
    @Select("select questionId from short_question where subject = #{subject} and level in ('1','2') order by rand() desc limit #{pageNo}")
    List<Integer> findBySubjectEsayShort(String subject, Integer pageNo);

    // 一般简答题 —— 随机生成对应科目数量
    //@Select("select questionId from short_question where subject in (select source from exam_manage where subject = #{subject}) and level in ('3','4') order by rand() desc limit #{pageNo}")
    @Select("select questionId from short_question where subject = #{subject} and level in ('3','4') order by rand() desc limit #{pageNo}")
    List<Integer> findBySubjectCommonShort(String subject, Integer pageNo);

    // 困难简答题 —— 随机生成对应科目数量
    //@Select("select questionId from short_question where subject in (select source from exam_manage where subject = #{subject}) and level in ('5') order by rand() desc limit #{pageNo}")
    @Select("select questionId from short_question where subject = #{subject} and level in ('5') order by rand() desc limit #{pageNo}")
    List<Integer> findBySubjectDifficultyShort(String subject, Integer pageNo);
    //更新简答题
    @Update("update short_question set subject = #{subject},question = #{question},answer = #{answer},analysis = #{analysis},level = #{level},section = #{section} where questionId = #{questionId}")
    int updateShort(ShortQuestion shortQuestion);
}
