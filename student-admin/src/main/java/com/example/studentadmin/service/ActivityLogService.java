package com.example.studentadmin.service;

import com.example.studentadmin.entity.ActivityLog;
import com.example.studentadmin.mapper.ActivityLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ActivityLogService {
    @Resource
    private ActivityLogMapper logMapper;

    public List<ActivityLog> getRecentLogs(int page, int size) {
        int offset = (page - 1) * size;
        return logMapper.getRecentLogs(offset, size);
    }

    public void addLog(String username, String action, String detail) {
        ActivityLog log = new ActivityLog();
        log.setUsername(username);
        log.setAction(action);
        log.setDetail(detail);
        logMapper.insertLog(log);
    }
} 