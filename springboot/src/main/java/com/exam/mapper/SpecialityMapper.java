package com.exam.mapper;

import com.exam.entity.Speciality;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SpecialityMapper {

    // 查询所有专业（用于下拉框信息获取）
    @Select("select * from speciality")
    List<Speciality> findAll();

}
