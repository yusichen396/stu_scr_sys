package com.example.studentadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.studentadmin.entity.Student;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface StudentService extends IService<Student> {
    // 分页查询学生列表
    IPage<Student> getStudentPage(Page<Student> page, String keyword);
    
    // 根据学号查询学生
    Student getByStudentId(String studentId);
    
    // 检查学号是否已存在
    boolean checkStudentIdExists(String studentId);
    
    void updateStudentWithIdChange(String oldStudentId, String newStudentId);
} 