package com.example.studentadmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String home() {
        return "服务正在运行！";
    }

    @GetMapping("/db")
    public String testDb() {
        try {
            // 测试数据库连接
            List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT 1");
            return "数据库连接成功！";
        } catch (Exception e) {
            return "数据库连接失败：" + e.getMessage();
        }
    }

    @GetMapping("/tables")
    public List<Map<String, Object>> testTables() {
        try {
            // 查询所有表结构
            return jdbcTemplate.queryForList(
                "SELECT TABLE_NAME, TABLE_COMMENT " +
                "FROM information_schema.TABLES " +
                "WHERE TABLE_SCHEMA = 'student_management'"
            );
        } catch (Exception e) {
            throw new RuntimeException("查询表结构失败：" + e.getMessage());
        }
    }

    @GetMapping("/table/student")
    public List<Map<String, Object>> testStudentTable() {
        try {
            // 查询学生表结构
            return jdbcTemplate.queryForList(
                "SELECT COLUMN_NAME, COLUMN_TYPE, COLUMN_COMMENT, IS_NULLABLE " +
                "FROM information_schema.COLUMNS " +
                "WHERE TABLE_SCHEMA = 'student_management' AND TABLE_NAME = 'student'"
            );
        } catch (Exception e) {
            throw new RuntimeException("查询学生表结构失败：" + e.getMessage());
        }
    }

    @GetMapping("/table/course")
    public List<Map<String, Object>> testCourseTable() {
        try {
            // 查询课程表结构
            return jdbcTemplate.queryForList(
                "SELECT COLUMN_NAME, COLUMN_TYPE, COLUMN_COMMENT, IS_NULLABLE " +
                "FROM information_schema.COLUMNS " +
                "WHERE TABLE_SCHEMA = 'student_management' AND TABLE_NAME = 'course'"
            );
        } catch (Exception e) {
            throw new RuntimeException("查询课程表结构失败：" + e.getMessage());
        }
    }

    @GetMapping("/table/grade")
    public List<Map<String, Object>> testGradeTable() {
        try {
            // 查询成绩表结构
            return jdbcTemplate.queryForList(
                "SELECT COLUMN_NAME, COLUMN_TYPE, COLUMN_COMMENT, IS_NULLABLE " +
                "FROM information_schema.COLUMNS " +
                "WHERE TABLE_SCHEMA = 'student_management' AND TABLE_NAME = 'grade'"
            );
        } catch (Exception e) {
            throw new RuntimeException("查询成绩表结构失败：" + e.getMessage());
        }
    }

    @GetMapping("/api/test/connection")
    public Map<String, Object> testConnection() {
        return Map.of(
            "status", "success",
            "message", "前后端连接成功",
            "timestamp", System.currentTimeMillis()
        );
    }
} 