package com.example.studentadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.studentadmin.entity.Course;
import com.example.studentadmin.mapper.CourseMapper;
import com.example.studentadmin.service.CourseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public IPage<Course> getCoursePage(Page<Course> page, String keyword) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Course::getName, keyword)
                  .or()
                  .like(Course::getCourseId, keyword);
        }
        wrapper.orderByDesc(Course::getCreatedTime);
        return this.page(page, wrapper);
    }
    
    @Override
    public Course getByCourseId(String courseId) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getCourseId, courseId);
        return this.getOne(wrapper);
    }
    
    @Override
    public boolean checkCourseIdExists(String courseId) {
        return this.getByCourseId(courseId) != null;
    }

    @Override
    public List<Course> searchCourses(String keyword) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Course::getName, keyword)
                  .or()
                  .like(Course::getCourseId, keyword);
        }
        return this.list(wrapper);
    }
    
    @Override
    @Transactional
    public void updateCourseWithIdChange(String oldCourseId, String newCourseId) {
        // 更新成绩表中的课程编号
        jdbcTemplate.update(
            "UPDATE grade SET course_id = ? WHERE course_id = ?",
            newCourseId, oldCourseId
        );
    }
} 