package com.example.studentadmin.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("grade")
public class Grade {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long studentId;
    
    @TableField(exist = false)
    private String studentName;
    
    @TableField(exist = false)
    private String studentNo;
    
    private String courseId;
    
    @TableField(exist = false)
    private String courseName;
    
    private BigDecimal score;
    private String semester;
    private LocalDate examTime;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;
    
    @TableLogic
    private Integer deleted;
} 