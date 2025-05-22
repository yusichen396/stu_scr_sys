package com.example.studentadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.studentadmin.entity.Grade;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface GradeService extends IService<Grade> {
    // 分页查询成绩列表
    IPage<Grade> getGradePage(Page<Grade> page, Long studentId, String courseId, String semester);
    
    // 根据学号和课程编号查询成绩
    Grade getByStudentIdAndCourseId(Long studentId, String courseId, String semester);
    
    // 检查成绩是否已存在
    boolean checkGradeExists(Long studentId, String courseId, String semester);
} 