package com.exam.mapper;

import com.exam.entity.PaperManage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PaperMapper {
    @Select("select paperId, questionType,questionId from paper_manage")
    List<PaperManage> findAll();

    @Select("select paperId, questionType,questionId from paper_manage where paperId = #{paperId}")
    List<PaperManage> findById(Integer paperId);

    // 生成试卷中题目类型和题型对应的编号
    @Insert("insert into paper_manage(paperId,questionType,questionId) values " +
            "(#{paperId},#{questionType},#{questionId})")
    int add(PaperManage paperManage);
    //删除试卷中题目类型和题型对应的编号
    @Delete("delete from paper_manage where paperId = #{paperId} and questionId = #{questionId} ")
    int deleteQuest( Integer paperId, Integer questionId);
    //删除所有试卷对应的id题目
    @Delete("delete from paper_manage where paperId = #{paperId}")
    int deleteAll(Integer paperId);

}
