-- 添加课程编号字段
ALTER TABLE course 
ADD COLUMN course_id VARCHAR(20) NOT NULL COMMENT '课程编号' AFTER id;

-- 添加唯一索引
ALTER TABLE course 
ADD UNIQUE INDEX uk_course_id (course_id); 