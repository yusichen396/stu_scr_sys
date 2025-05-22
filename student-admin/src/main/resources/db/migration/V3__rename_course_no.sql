-- 重命名课程编号字段
ALTER TABLE course 
CHANGE COLUMN course_no course_id VARCHAR(20) NOT NULL COMMENT '课程编号'; 