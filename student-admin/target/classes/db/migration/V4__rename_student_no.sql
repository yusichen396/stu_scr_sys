-- 重命名学生编号字段
ALTER TABLE student 
CHANGE COLUMN student_no student_id VARCHAR(20) NOT NULL COMMENT '学号'; 