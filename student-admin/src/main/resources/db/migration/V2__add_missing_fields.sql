-- 添加课程表缺失的字段
ALTER TABLE course 
ADD COLUMN description TEXT COMMENT '课程描述' AFTER teacher,
ADD COLUMN course_type VARCHAR(50) COMMENT '课程类型' AFTER description; 