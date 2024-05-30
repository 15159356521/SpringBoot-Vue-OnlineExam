package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ShortQuestion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface ShortQuestionMapper {

    @Select("select * from short_question where questionId in (select questionId from paper_manage where questionType = 2 and paperId = #{paperId})")
    List<ShortQuestion> findByIdAndType(Integer paperId);

    @Select("select * from short_question")
    IPage<ShortQuestion> findAll(Page page);

    /**
     * 查询最后一条questionId
     * @return shortQuestion
     */
    @Select("select questionId from short_question order by questionId desc limit 1")
    ShortQuestion findOnlyQuestionId();

    @Options(useGeneratedKeys = true,keyProperty ="questionId" )
    @Insert("insert into short_question(subject,question,answer,analysis,level,section) values " +
            "(#{subject,},#{question},#{answer},#{analysis},#{level},#{section})")
    int add(ShortQuestion shortQuestion);

    @Select("select questionId from short_question where subject = #{subject} order by rand() desc limit #{pageNo}")
    List<Integer> findBySubject(String subject,Integer pageNo);
}
