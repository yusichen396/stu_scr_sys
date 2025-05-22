package com.example.studentadmin.controller;

import com.example.studentadmin.entity.ActivityLog;
import com.example.studentadmin.service.ActivityLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logs")
public class ActivityLogController {
    @Resource
    private ActivityLogService logService;

    @GetMapping
    public Map<String, Object> getRecentLogs(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<ActivityLog> logs = logService.getRecentLogs(page, size);
        Map<String, Object> data = new HashMap<>();
        data.put("records", logs);
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        return result;
    }
} 