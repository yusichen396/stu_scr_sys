package com.example.studentadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = {"com.example.studentadmin.mapper"})
@EnableTransactionManagement
public class StudentAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentAdminApplication.class, args);
    }
}