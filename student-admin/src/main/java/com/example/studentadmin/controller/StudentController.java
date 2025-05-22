package com.example.studentadmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.studentadmin.common.Result;
import com.example.studentadmin.entity.Student;
import com.example.studentadmin.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @GetMapping("/page")
    public Result<IPage<Student>> getStudentPage(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {
        Page<Student> page = new Page<>(current, size);
        return Result.success(studentService.getStudentPage(page, keyword));
    }
    
    @GetMapping("/{studentId}")
    public Result<Student> getStudent(@PathVariable String studentId) {
        return Result.success(studentService.getByStudentId(studentId));
    }
    
    @PostMapping
    public Result<Void> addStudent(@RequestBody Student student) {
        if (studentService.checkStudentIdExists(student.getStudentId())) {
            return Result.error("学号已存在");
        }
        studentService.save(student);
        return Result.success();
    }
    
    @PutMapping
    public Result<Void> updateStudent(@RequestBody Student student) {
        Student oldStudent = studentService.getById(student.getId());
        if (oldStudent == null) {
            return Result.error("学生不存在");
        }
        
        // 如果学号发生变化，检查新学号是否已存在
        if (!oldStudent.getStudentId().equals(student.getStudentId())) {
            if (studentService.checkStudentIdExists(student.getStudentId())) {
                return Result.error("新学号已存在");
            }
            // 更新相关表中的学号
            studentService.updateStudentWithIdChange(oldStudent.getStudentId(), student.getStudentId());
        }
        
        studentService.updateById(student);
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    public Result<Void> deleteStudent(@PathVariable Long id) {
        studentService.removeById(id);
        return Result.success();
    }
} 