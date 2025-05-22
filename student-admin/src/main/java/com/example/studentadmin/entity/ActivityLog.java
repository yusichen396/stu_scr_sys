package com.example.studentadmin.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ActivityLog {
    private Long id;
    private String username;
    private String action;
    private String detail;
    private LocalDateTime createdAt;
} 