package com.example.studentadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.studentadmin.entity.Grade;
import com.example.studentadmin.mapper.GradeMapper;
import com.example.studentadmin.service.GradeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    
    @Override
    public IPage<Grade> getGradePage(Page<Grade> page, Long studentId, String courseId, String semester) {
        return baseMapper.getGradePage(page, studentId, courseId, semester);
    }
    
    @Override
    public Grade getByStudentIdAndCourseId(Long studentId, String courseId, String semester) {
        return baseMapper.getByStudentIdAndCourseId(studentId, courseId, semester);
    }
    
    @Override
    public boolean checkGradeExists(Long studentId, String courseId, String semester) {
        return this.getByStudentIdAndCourseId(studentId, courseId, semester) != null;
    }
} 