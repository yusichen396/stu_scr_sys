package com.example.studentadmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.studentadmin.common.Result;
import com.example.studentadmin.entity.Course;
import com.example.studentadmin.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    @GetMapping("/page")
    public Result<IPage<Course>> getCoursePage(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {
        Page<Course> page = new Page<>(current, size);
        return Result.success(courseService.getCoursePage(page, keyword));
    }
    
    @GetMapping("/{courseId}")
    public Result<Course> getCourse(@PathVariable String courseId) {
        return Result.success(courseService.getByCourseId(courseId));
    }
    
    @PostMapping
    public Result<Void> addCourse(@RequestBody Course course) {
        if (courseService.checkCourseIdExists(course.getCourseId())) {
            return Result.error("课程编号已存在");
        }
        courseService.save(course);
        return Result.success();
    }
    
    @PutMapping
    public Result<Void> updateCourse(@RequestBody Course course) {
        Course oldCourse = courseService.getById(course.getId());
        if (oldCourse == null) {
            return Result.error("课程不存在");
        }
        
        // 如果课程编号发生变化，检查新课程编号是否已存在
        if (!oldCourse.getCourseId().equals(course.getCourseId())) {
            if (courseService.checkCourseIdExists(course.getCourseId())) {
                return Result.error("新课程编号已存在");
            }
            // 更新相关表中的课程编号
            courseService.updateCourseWithIdChange(oldCourse.getCourseId(), course.getCourseId());
        }
        
        courseService.updateById(course);
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    public Result<Void> deleteCourse(@PathVariable Long id) {
        courseService.removeById(id);
        return Result.success();
    }
} 