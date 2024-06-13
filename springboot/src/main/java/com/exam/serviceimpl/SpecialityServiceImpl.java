package com.exam.serviceimpl;

import com.exam.entity.Speciality;
import com.exam.mapper.SpecialityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialityServiceImpl implements SpecialityMapper {

    @Autowired
    private SpecialityMapper specialityMapper;

    @Override
    public List<Speciality> findAll() {
        return specialityMapper.findAll();
    }

}
