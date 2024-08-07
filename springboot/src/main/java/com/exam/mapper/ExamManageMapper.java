package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ExamManage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ExamManageMapper {

    // 不分页查询所有考试信息
    @Select("select * from exam_manage order by examCode DESC")
    List<ExamManage> findAll();

    // 分页查询所有试卷
    @Select("select * from exam_manage  order by examCode DESC")
    IPage<ExamManage> findAll(Page page);

    // 分页查询按科目试卷归档信息
    @Select("select * from exam_manage where subject like #{subjects} order by examCode DESC")
    IPage<ExamManage> findBySubject(Page page, String subjects);

    @Select("select * from exam_manage where examCode = #{examCode} ")
    ExamManage findById(Integer examCode);

    @Delete("delete from exam_manage where examCode = #{examCode}")
    int delete(Integer examCode);

    // 修改试卷信息
    @Update("update exam_manage set description = #{description},subject = #{subject},source = #{source},paperId = #{paperId}," +
            "examDate = #{examDate},totalTime = #{totalTime},grade = #{grade},term = #{term}," +
            "major = #{major},institute = #{institute},totalScore = #{totalScore}," +
            "type = #{type},tips = #{tips} where examCode = #{examCode}")
    int update(ExamManage exammanage);

    // 添加考试信息
    @Options(useGeneratedKeys = true, keyProperty = "examCode")
    @Insert("insert into exam_manage(description,subject,source,paperId,examDate,totalTime,grade,term,major,institute,totalScore,type,tips)" +
            " values(#{description},#{subject},#{source},#{paperId},#{examDate},#{totalTime},#{grade},#{term},#{major},#{institute},#{totalScore},#{type},#{tips})")
    int add(ExamManage exammanage);

    /**
     * 查询最后一条记录的paperId,返回给前端达到自增效果
     *
     * @return paperId
     */
    @Select("select paperId from exam_manage order by paperId desc limit 1")
    ExamManage findOnlyPaperId();


}
