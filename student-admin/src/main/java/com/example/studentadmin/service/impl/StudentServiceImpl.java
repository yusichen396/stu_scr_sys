package com.example.studentadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.studentadmin.entity.Student;
import com.example.studentadmin.mapper.StudentMapper;
import com.example.studentadmin.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public IPage<Student> getStudentPage(Page<Student> page, String keyword) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Student::getName, keyword)
                  .or()
                  .like(Student::getStudentId, keyword);
        }
        wrapper.orderByDesc(Student::getCreatedTime);
        return this.page(page, wrapper);
    }
    
    @Override
    public Student getByStudentId(String studentId) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getStudentId, studentId);
        return this.getOne(wrapper);
    }
    
    @Override
    public boolean checkStudentIdExists(String studentId) {
        return this.getByStudentId(studentId) != null;
    }
    
    @Override
    @Transactional
    public void updateStudentWithIdChange(String oldStudentId, String newStudentId) {
        // 更新成绩表中的学号
        jdbcTemplate.update(
            "UPDATE grade SET student_id = ? WHERE student_id = ?",
            newStudentId, oldStudentId
        );
    }
} 