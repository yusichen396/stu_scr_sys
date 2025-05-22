package com.example.studentadmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.studentadmin.common.Result;
import com.example.studentadmin.entity.Grade;
import com.example.studentadmin.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grades")
public class GradeController {
    
    @Autowired
    private GradeService gradeService;
    
    @GetMapping("/page")
    public Result<IPage<Grade>> getGradePage(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String courseId,
            @RequestParam(required = false) String semester) {
        Page<Grade> page = new Page<>(current, size);
        return Result.success(gradeService.getGradePage(page, studentId, courseId, semester));
    }
    
    @PostMapping
    public Result<Void> addGrade(@RequestBody Grade grade) {
        if (gradeService.checkGradeExists(grade.getStudentId(), grade.getCourseId(), grade.getSemester())) {
            return Result.error("该学生在此学期的该课程成绩已存在");
        }
        gradeService.save(grade);
        return Result.success();
    }
    
    @PutMapping
    public Result<Void> updateGrade(@RequestBody Grade grade) {
        gradeService.updateById(grade);
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    public Result<Void> deleteGrade(@PathVariable Long id) {
        gradeService.removeById(id);
        return Result.success();
    }
} 