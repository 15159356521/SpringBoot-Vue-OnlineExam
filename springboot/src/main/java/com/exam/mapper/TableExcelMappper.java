package com.exam.mapper;

import com.exam.entity.TableExcel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TableExcelMappper {
    @Select("SELECT j.studentName , MAX(s.etScore) as ptScore, s.subject,s.studentId\n" +
            "FROM score AS s \n" +
            "JOIN student AS j ON s.studentId = j.studentId\n" +
            "WHERE s.examCode =#{examCode}" + "\n" +
            "GROUP BY j.studentId, s.subject")
    List<TableExcel> getTableExcel(Integer examCode);

}
