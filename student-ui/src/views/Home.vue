<template>
  <div class="home">
    <a-typography-title :heading="2" style="margin-bottom: 24px;">
      欢迎使用学生成绩管理系统
    </a-typography-title>
    
    <!-- 统计卡片 -->
    <a-row :gutter="16" style="margin-bottom: 24px;">
      <a-col :span="8">
        <a-card class="stat-card" :bordered="false" hoverable>
          <template #title>学生总数</template>
          <a-statistic :value="statistics.studentCount" animation>
            <template #suffix>
              <icon-user />
            </template>
          </a-statistic>
        </a-card>
      </a-col>
      <a-col :span="8">
        <a-card class="stat-card" :bordered="false" hoverable>
          <template #title>课程总数</template>
          <a-statistic :value="statistics.courseCount" animation>
            <template #suffix>
              <icon-book />
            </template>
          </a-statistic>
        </a-card>
      </a-col>
      <a-col :span="8">
        <a-card class="stat-card" :bordered="false" hoverable>
          <template #title>成绩记录</template>
          <a-statistic :value="statistics.gradeCount" animation>
            <template #suffix>
              <icon-file />
            </template>
          </a-statistic>
        </a-card>
      </a-col>
    </a-row>

    <!-- 快速操作卡片 -->
    <a-typography-title :heading="5" style="margin-bottom: 16px;">
      快速操作
    </a-typography-title>
    <a-row :gutter="16" style="margin-bottom: 24px;">
      <a-col :span="6">
        <a-card class="action-card" :bordered="false" hoverable @click="showAddStudentModal">
          <div class="action-content">
            <icon-user-add class="action-icon" />
            <div class="action-title">添加学生</div>
          </div>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card class="action-card" :bordered="false" hoverable @click="showAddCourseModal">
          <div class="action-content">
            <icon-plus-circle class="action-icon" />
            <div class="action-title">添加课程</div>
          </div>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card class="action-card" :bordered="false" hoverable @click="showAddGradeModal">
          <div class="action-content">
            <icon-edit class="action-icon" />
            <div class="action-title">录入成绩</div>
          </div>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card class="action-card" :bordered="false" hoverable @click="showAnalysisModal">
          <div class="action-content">
            <icon-bar-chart class="action-icon" />
            <div class="action-title">成绩分析</div>
          </div>
        </a-card>
      </a-col>
    </a-row>

    <!-- 系统工具 -->
    <a-typography-title :heading="5" style="margin-bottom: 16px;">
      系统工具
    </a-typography-title>
    <a-row :gutter="16" style="margin-bottom: 24px;">
      <a-col :span="6">
        <a-card class="action-card" :bordered="false" hoverable @click="navigateTo('test-connection')">
          <div class="action-content">
            <icon-experiment class="action-icon" />
            <div class="action-title">连接测试</div>
          </div>
        </a-card>
      </a-col>
    </a-row>

    <!-- 添加学生弹窗 -->
    <a-modal
      v-model:visible="addStudentVisible"
      title="添加学生"
      @ok="handleAddStudent"
      @cancel="addStudentVisible = false"
    >
      <a-form :model="studentForm" ref="studentFormRef">
        <a-form-item field="studentId" label="学号" :rules="[{ required: true, message: '请输入学号' }]">
          <a-input v-model="studentForm.studentId" placeholder="请输入学号" />
        </a-form-item>
        <a-form-item field="name" label="姓名" :rules="[{ required: true, message: '请输入姓名' }]">
          <a-input v-model="studentForm.name" placeholder="请输入姓名" />
        </a-form-item>
        <a-form-item field="gender" label="性别">
          <a-radio-group v-model="studentForm.gender">
            <a-radio value="male">男</a-radio>
            <a-radio value="female">女</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item field="age" label="年龄">
          <a-input-number v-model="studentForm.age" placeholder="请输入年龄" />
        </a-form-item>
        <a-form-item field="class" label="班级">
          <a-input v-model="studentForm.class" placeholder="请输入班级" />
        </a-form-item>
        <a-form-item field="major" label="专业">
          <a-input v-model="studentForm.major" placeholder="请输入专业" />
        </a-form-item>
      </a-form>
    </a-modal>
    
    <!-- 添加课程弹窗 -->
    <a-modal
      v-model:visible="addCourseVisible"
      title="添加课程"
      @ok="handleAddCourse"
      @cancel="addCourseVisible = false"
    >
      <a-form :model="courseForm" ref="courseFormRef">
        <a-form-item field="courseId" label="课程编号" :rules="[{ required: true, message: '请输入课程编号' }]">
          <a-input v-model="courseForm.courseId" placeholder="请输入课程编号" />
        </a-form-item>
        <a-form-item field="name" label="课程名称" :rules="[{ required: true, message: '请输入课程名称' }]">
          <a-input v-model="courseForm.name" placeholder="请输入课程名称" />
        </a-form-item>
        <a-form-item field="credit" label="学分">
          <a-input-number v-model="courseForm.credit" placeholder="请输入学分" />
        </a-form-item>
        <a-form-item field="hours" label="学时">
          <a-input-number v-model="courseForm.hours" placeholder="请输入学时" />
        </a-form-item>
        <a-form-item field="teacher" label="任课教师">
          <a-input v-model="courseForm.teacher" placeholder="请输入任课教师" />
        </a-form-item>
      </a-form>
    </a-modal>
    
    <!-- 录入成绩弹窗 -->
    <a-modal
      v-model:visible="addGradeVisible"
      title="录入成绩"
      @ok="handleAddGrade"
      @cancel="addGradeVisible = false"
    >
      <a-form :model="gradeForm" ref="gradeFormRef">
        <a-form-item field="studentId" label="学号" :rules="[{ required: true, message: '请输入学号' }]">
          <a-input v-model="gradeForm.studentId" placeholder="请输入学号" />
        </a-form-item>
        <a-form-item field="courseId" label="课程编号" :rules="[{ required: true, message: '请输入课程编号' }]">
          <a-input v-model="gradeForm.courseId" placeholder="请输入课程编号" />
        </a-form-item>
        <a-form-item field="score" label="成绩" :rules="[{ required: true, message: '请输入成绩' }]">
          <a-input-number v-model="gradeForm.score" placeholder="请输入成绩" :min="0" :max="100" />
        </a-form-item>
        <a-form-item field="semester" label="学期">
          <a-select v-model="gradeForm.semester" placeholder="请选择学期">
            <a-option value="2024-2025-1">2024-2025学年第一学期</a-option>
            <a-option value="2024-2025-2">2024-2025学年第二学期</a-option>
            <a-option value="2025-2026-1">2025-2026学年第一学期</a-option>
            <a-option value="2025-2026-2">2025-2026学年第二学期</a-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>

    <!-- 成绩分析弹窗 -->
    <a-modal v-model:visible="analysisVisible" title="成绩分析" @cancel="analysisVisible = false" :footer="false">
      <a-space direction="vertical" style="width:100%">
        <a-select v-model="analysisStudentId" placeholder="请选择学生" style="width: 100%" @change="handleSelectStudent">
          <a-option v-for="stu in analysisStudents" :key="stu.studentId" :value="stu.studentId">
            {{ stu.name }} ({{ stu.studentId }})
          </a-option>
        </a-select>
        <a-table :columns="analysisColumns" :data="analysisGrades" :loading="analysisLoading" :pagination="false" style="margin-top: 16px" />
      </a-space>
    </a-modal>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { Message } from '@arco-design/web-vue';
import { 
  IconUser, 
  IconBook, 
  IconFile, 
  IconUserAdd, 
  IconPlusCircle, 
  IconEdit, 
  IconBarChart,
  IconExperiment
} from '@arco-design/web-vue/es/icon';
import { addStudent } from '../api/student';
import { addCourse } from '../api/course';
import { addGrade } from '../api/grade';
import { getStudents } from '../api/student';
import { getCourses } from '../api/course';
import { getGrades } from '../api/grade';
import { getLogs } from '../api/log';

const router = useRouter();
const statistics = reactive({
  studentCount: 0,
  courseCount: 0,
  gradeCount: 0
});

// 导航函数
const navigateTo = (route) => {
  router.push(`/${route}`);
};

// 添加学生相关
const addStudentVisible = ref(false);
const studentFormRef = ref(null);
const studentForm = reactive({
  studentId: '',
  name: '',
  gender: 'male',
  age: 20,
  class: '',
  major: ''
});

const showAddStudentModal = () => {
  addStudentVisible.value = true;
};

const handleAddStudent = async () => {
  try {
    await studentFormRef.value.validate(); // 校验不通过会抛异常
    await addStudent({
      studentId: studentForm.studentId,
      name: studentForm.name,
      gender: studentForm.gender === 'male' ? '男' : '女',
      age: studentForm.age,
      className: studentForm.class,
      major: studentForm.major
    });
    Message.success('学生添加成功');
    addStudentVisible.value = false;
    statistics.studentCount += 1;
    studentForm.studentId = '';
    studentForm.name = '';
    studentForm.gender = 'male';
    studentForm.age = 20;
    studentForm.class = '';
    studentForm.major = '';
  } catch (error) {
    let msg = '学生添加失败';
    if (error?.response?.data?.msg) {
      msg += '：' + error.response.data.msg;
    } else if (error?.response?.data?.message) {
      msg += '：' + error.response.data.message;
    } else if (error?.message) {
      msg += '：' + error.message;
    }
    Message.error(msg);
    console.error('添加学生失败', error);
  }
};

// 添加课程相关
const addCourseVisible = ref(false);
const courseFormRef = ref(null);
const courseForm = reactive({
  courseId: '',
  name: '',
  credit: 3,
  hours: 48,
  teacher: ''
});

const showAddCourseModal = () => {
  addCourseVisible.value = true;
};

const handleAddCourse = async () => {
  try {
    await courseFormRef.value.validate(); // 校验不通过会抛异常
    await addCourse({
      courseId: courseForm.courseId,
      name: courseForm.name,
      credit: courseForm.credit,
      hours: courseForm.hours,
      teacher: courseForm.teacher
    });
    Message.success('课程添加成功');
    addCourseVisible.value = false;
    statistics.courseCount += 1;
    courseForm.courseId = '';
    courseForm.name = '';
    courseForm.credit = 3;
    courseForm.hours = 48;
    courseForm.teacher = '';
  } catch (error) {
    let msg = '课程添加失败';
    if (error?.response?.data?.msg) {
      msg += '：' + error.response.data.msg;
    } else if (error?.response?.data?.message) {
      msg += '：' + error.response.data.message;
    } else if (error?.message) {
      msg += '：' + error.message;
    }
    Message.error(msg);
    console.error('添加课程失败', error);
  }
};

// 录入成绩相关
const addGradeVisible = ref(false);
const gradeFormRef = ref(null);
const gradeForm = reactive({
  studentId: '',
  courseId: '',
  score: 80,
  semester: '2024-2025-1'
});

const showAddGradeModal = () => {
  addGradeVisible.value = true;
};

const handleAddGrade = async () => {
  try {
    await gradeFormRef.value.validate(); // 校验不通过会抛异常
    await addGrade({
      studentId: gradeForm.studentId,
      courseId: gradeForm.courseId,
      score: gradeForm.score,
      semester: gradeForm.semester
    });
    Message.success('成绩录入成功');
    addGradeVisible.value = false;
    statistics.gradeCount += 1;
    gradeForm.studentId = '';
    gradeForm.courseId = '';
    gradeForm.score = 80;
    gradeForm.semester = '2024-2025-1';
  } catch (error) {
    let msg = '成绩录入失败';
    if (error?.response?.data?.msg) {
      msg += '：' + error.response.data.msg;
    } else if (error?.response?.data?.message) {
      msg += '：' + error.response.data.message;
    } else if (error?.message) {
      msg += '：' + error.message;
    }
    Message.error(msg);
    console.error('录入成绩失败', error);
  }
};

// 成绩分析相关
const analysisVisible = ref(false);
const analysisStudentId = ref('');
const analysisStudents = ref([]);
const analysisGrades = ref([]);
const analysisLoading = ref(false);

const analysisColumns = [
  { title: '课程编号', dataIndex: 'courseId' },
  { title: '课程名称', dataIndex: 'courseName' },
  { title: '成绩', dataIndex: 'score' },
  { title: '学期', dataIndex: 'semester' },
  { title: '考试时间', dataIndex: 'examTime' }
];

const showAnalysisModal = async () => {
  analysisVisible.value = true;
  analysisStudentId.value = '';
  analysisGrades.value = [];
  analysisLoading.value = false;
  // 获取学生列表
  try {
    const res = await getStudents({ size: 1000 });
    analysisStudents.value = res.data.data.records;
  } catch (e) {
    Message.error('获取学生列表失败');
  }
};

const handleSelectStudent = async (studentId) => {
  if (!studentId) {
    analysisGrades.value = [];
    return;
  }
  analysisLoading.value = true;
  try {
    const res = await getGrades({ studentId, size: 100 });
    analysisGrades.value = res.data.data.records;
  } catch (e) {
    Message.error('获取成绩失败');
    analysisGrades.value = [];
  } finally {
    analysisLoading.value = false;
  }
};

onMounted(() => {
  // 获取真实统计数据
  getStudents({ size: 1 }).then(res => {
    statistics.studentCount = res.data.data.total;
  });
  getCourses({ size: 1 }).then(res => {
    statistics.courseCount = res.data.data.total;
  });
  getGrades({ size: 1 }).then(res => {
    statistics.gradeCount = res.data.data.total;
  });
});
</script>

<style scoped>
.home {
  padding: 20px;
}

.stat-card {
  margin-bottom: 16px;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
}

.action-card {
  margin-bottom: 16px;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
  height: 120px;
  cursor: pointer;
  transition: all 0.3s;
}

.action-card:hover {
  transform: translateY(-5px);
  background-color: var(--color-fill-2);
}

.action-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.action-icon {
  font-size: 32px;
  color: rgb(var(--primary-6));
  margin-bottom: 12px;
}

.action-title {
  font-size: 16px;
  font-weight: 500;
}
</style> 