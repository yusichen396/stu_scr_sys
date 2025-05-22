package com.example.studentadmin.mapper;

import com.example.studentadmin.entity.ActivityLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ActivityLogMapper {
    List<ActivityLog> getRecentLogs(@Param("offset") int offset, @Param("size") int size);
    void insertLog(ActivityLog log);
} 