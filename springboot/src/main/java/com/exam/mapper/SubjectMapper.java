package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.Subject;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubjectMapper {

    @Options(useGeneratedKeys = true, keyProperty = "subjectId")
    @Insert("insert into subject(subjectName,isExists) values (#{subjectName},#{isExists})")
    int add(Subject subject);

    /**
     * 分页查询所有科目（存在的）
     *
     * @param page
     * @return List<Subject>
     */
    @Select("select * from subject where isExists = 1")
    IPage<Subject> findAll(Page page);

    @Select("select * from subject where subjectId = #{subjectId}")
    Subject findById(Integer subjectId);

    /**
     * 确认更新科目名称信息
     *
     * @param subject 传递一个对象
     * @return 受影响的记录条数
     */
    @Update("update subject set subjectName = #{subjectName} where subjectId = #{subjectId}")
    int update(Subject subject);

    // 删除科目名称信息（假删除，数据库仍然存在）
    @Update("update subject set isExists = 0 where subjectId = #{subjectId}")
    int deleteById(Integer subjectId);

    // 查询所有科目名称（用于下拉框信息获取）
    @Select("select * from subject")
    List<Subject> findBySubjectName();
    @Select("select * from subject where subjectName like concat('%',#{subName},'%')")
    List<Subject> findBySubName(String subName);

}
