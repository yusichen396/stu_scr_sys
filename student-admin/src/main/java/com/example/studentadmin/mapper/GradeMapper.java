package com.example.studentadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.studentadmin.entity.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GradeMapper extends BaseMapper<Grade> {
    IPage<Grade> getGradePage(Page<Grade> page, 
                            @Param("studentId") Long studentId,
                            @Param("courseId") String courseId,
                            @Param("semester") String semester);
                            
    Grade getByStudentIdAndCourseId(@Param("studentId") Long studentId,
                                   @Param("courseId") String courseId,
                                   @Param("semester") String semester);
} 