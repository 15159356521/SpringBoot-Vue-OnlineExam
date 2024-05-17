package com.exam.mapper;

import com.exam.entity.Speciality;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SpecialityMapper {
    @Select("select * from speciality")
    List<Speciality> findAll();
}
