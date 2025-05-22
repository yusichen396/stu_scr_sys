package com.example.studentadmin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // 初始化数据库结构
        initDatabaseStructure();
        // 初始化测试数据
        initTestData();
    }

    private void initDatabaseStructure() throws Exception {
        ClassPathResource resource = new ClassPathResource("db/init.sql");
        String sql = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        executeSql(sql);
    }

    private void initTestData() throws Exception {
        ClassPathResource resource = new ClassPathResource("db/test-data.sql");
        String sql = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        executeSql(sql);
    }

    private void executeSql(String sql) {
        String[] statements = sql.split(";");
        for (String statement : statements) {
            if (statement.trim().length() > 0) {
                try {
                    jdbcTemplate.execute(statement);
                } catch (Exception e) {
                    // 忽略已存在的表等错误
                    System.out.println("执行SQL语句时出错: " + e.getMessage());
                }
            }
        }
    }
} 