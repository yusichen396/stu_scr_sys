package com.example.studentadmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.studentadmin.entity.Course;
import java.util.List;

public interface CourseService extends IService<Course> {
    // 分页查询课程列表
    IPage<Course> getCoursePage(Page<Course> page, String keyword);
    
    // 根据课程ID查询课程
    Course getByCourseId(String courseId);
    
    // 检查课程ID是否已存在
    boolean checkCourseIdExists(String courseId);
    
    // 搜索课程
    List<Course> searchCourses(String keyword);
    
    void updateCourseWithIdChange(String oldCourseId, String newCourseId);
} 