package com.example.studentadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.studentadmin.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
} 