<template>
  <div class="grade">
    <a-typography-title :heading="2">成绩管理</a-typography-title>
    <div class="operation-bar">
      <a-space>
        <a-button type="primary" @click="showAddGradeModal">
          <template #icon><icon-plus /></template>
          添加成绩
        </a-button>
        <a-select
          v-model="searchStudentId"
          placeholder="选择学生"
          style="width: 200px"
          allow-clear
          @change="handleSearch"
        >
          <a-option v-for="student in students" :key="student.studentId" :value="student.studentId">
            {{ student.name }} ({{ student.studentId }})
          </a-option>
        </a-select>
        <a-select
          v-model="searchCourseId"
          placeholder="选择课程"
          style="width: 200px"
          allow-clear
          @change="handleSearch"
        >
          <a-option v-for="course in courses" :key="course.courseId" :value="course.courseId">
            {{ course.name }} ({{ course.courseId }})
          </a-option>
        </a-select>
        <a-select
          v-model="searchSemester"
          placeholder="选择学期"
          style="width: 200px"
          allow-clear
          @change="handleSearch"
        >
          <a-option value="2024-2025-1">2024-2025学年第一学期</a-option>
          <a-option value="2024-2025-2">2024-2025学年第二学期</a-option>
        </a-select>
      </a-space>
    </div>
    <a-table 
      :columns="columns" 
      :data="grades" 
      :pagination="pagination"
      :loading="loading"
      :scroll="{ x: '100%' }"
      :bordered="false"
      :stripe="true"
      @page-change="onPageChange"
    >
      <template #operations="{ record }">
        <a-space>
          <a-button type="text" size="small" @click="showEditGradeModal(record)">
            <template #icon><icon-edit /></template>
            编辑
          </a-button>
          <a-button type="text" status="danger" size="small" @click="handleDeleteGrade(record)">
            <template #icon><icon-delete /></template>
            删除
          </a-button>
        </a-space>
      </template>
    </a-table>

    <!-- 添加/编辑成绩弹窗 -->
    <a-modal
      v-model:visible="gradeModalVisible"
      :title="isEdit ? '编辑成绩' : '添加成绩'"
      @ok="handleSubmitGrade"
      @cancel="closeGradeModal"
    >
      <a-form :model="gradeForm" ref="gradeFormRef">
        <a-form-item field="studentId" label="学生" :rules="[{ required: true, message: '请选择学生' }]">
          <a-select
            v-model="gradeForm.studentId"
            placeholder="请选择学生"
            :disabled="isEdit"
          >
            <a-option v-for="student in students" :key="student.studentId" :value="student.studentId">
              {{ student.name }} ({{ student.studentId }})
            </a-option>
          </a-select>
        </a-form-item>
        <a-form-item field="courseId" label="课程" :rules="[{ required: true, message: '请选择课程' }]">
          <a-select
            v-model="gradeForm.courseId"
            placeholder="请选择课程"
            :disabled="isEdit"
          >
            <a-option v-for="course in courses" :key="course.courseId" :value="course.courseId">
              {{ course.name }} ({{ course.courseId }})
            </a-option>
          </a-select>
        </a-form-item>
        <a-form-item field="semester" label="学期" :rules="[{ required: true, message: '请选择学期' }]">
          <a-select
            v-model="gradeForm.semester"
            placeholder="请选择学期"
            :disabled="isEdit"
          >
            <a-option value="2024-2025-1">2024-2025学年第一学期</a-option>
            <a-option value="2024-2025-2">2024-2025学年第二学期</a-option>
          </a-select>
        </a-form-item>
        <a-form-item field="score" label="成绩" :rules="[{ required: true, message: '请输入成绩' }]">
          <a-input-number
            v-model="gradeForm.score"
            placeholder="请输入成绩"
            :min="0"
            :max="100"
            :precision="2"
          />
        </a-form-item>
        <a-form-item field="examTime" label="考试时间">
          <a-date-picker
            v-model="gradeForm.examTime"
            style="width: 100%"
            placeholder="请选择考试时间"
          />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { Message } from '@arco-design/web-vue';
import { IconPlus, IconEdit, IconDelete } from '@arco-design/web-vue/es/icon';
import { getGrades, addGrade, updateGrade, deleteGrade } from '../api/grade';
import { getStudents } from '../api/student';
import { getCourses } from '../api/course';

const columns = [
  {
    title: '学号',
    dataIndex: 'studentId',
    width: 120,
  },
  {
    title: '学生姓名',
    dataIndex: 'studentName',
    width: 120,
  },
  {
    title: '课程编号',
    dataIndex: 'courseId',
    width: 120,
  },
  {
    title: '课程名称',
    dataIndex: 'courseName',
    width: 200,
  },
  {
    title: '成绩',
    dataIndex: 'score',
    width: 100,
  },
  {
    title: '学期',
    dataIndex: 'semester',
    width: 150,
  },
  {
    title: '考试时间',
    dataIndex: 'examTime',
    width: 120,
  },
  {
    title: '操作',
    slotName: 'operations',
    width: 160,
    fixed: 'right',
  },
];

// 成绩数据
const grades = ref([]);
const loading = ref(false);
const searchStudentId = ref(null);
const searchCourseId = ref(null);
const searchSemester = ref(null);
const pagination = reactive({
  total: 0,
  current: 1,
  pageSize: 10,
});

// 学生和课程数据
const students = ref([]);
const courses = ref([]);

// 成绩表单相关
const gradeModalVisible = ref(false);
const gradeFormRef = ref(null);
const isEdit = ref(false);
const gradeForm = reactive({
  id: null,
  studentId: null,
  courseId: null,
  score: null,
  semester: null,
  examTime: null
});

// 获取成绩列表
const fetchGrades = async () => {
  try {
    loading.value = true;
    const params = {
      current: pagination.current,
      size: pagination.pageSize,
      studentId: searchStudentId.value,
      courseId: searchCourseId.value,
      semester: searchSemester.value
    };
    const res = await getGrades(params);
    grades.value = res.data.data.records;
    pagination.total = res.data.data.total;
  } catch (error) {
    Message.error('获取成绩列表失败：' + error.message);
  } finally {
    loading.value = false;
  }
};

// 获取学生列表
const fetchStudents = async () => {
  try {
    const res = await getStudents({ size: 1000 });
    students.value = res.data.data.records;
  } catch (error) {
    Message.error('获取学生列表失败：' + error.message);
  }
};

// 获取课程列表
const fetchCourses = async () => {
  try {
    const res = await getCourses({ size: 1000 });
    courses.value = res.data.data.records;
  } catch (error) {
    Message.error('获取课程列表失败：' + error.message);
  }
};

// 搜索
const handleSearch = () => {
  pagination.current = 1;
  fetchGrades();
};

// 分页
const onPageChange = (current) => {
  pagination.current = current;
  fetchGrades();
};

// 显示添加成绩弹窗
const showAddGradeModal = () => {
  isEdit.value = false;
  gradeForm.id = null;
  gradeForm.studentId = null;
  gradeForm.courseId = null;
  gradeForm.score = null;
  gradeForm.semester = null;
  gradeForm.examTime = null;
  gradeModalVisible.value = true;
};

// 显示编辑成绩弹窗
const showEditGradeModal = (record) => {
  isEdit.value = true;
  Object.assign(gradeForm, record);
  gradeModalVisible.value = true;
};

// 关闭成绩弹窗
const closeGradeModal = () => {
  gradeModalVisible.value = false;
  gradeFormRef.value?.resetFields();
};

// 提交成绩表单
const handleSubmitGrade = async () => {
  try {
    await gradeFormRef.value.validate();
    
    if (isEdit.value) {
      await updateGrade(gradeForm);
      Message.success('成绩信息更新成功');
    } else {
      await addGrade(gradeForm);
      Message.success('成绩添加成功');
    }
    
    closeGradeModal();
    fetchGrades();
  } catch (error) {
    Message.error((isEdit.value ? '更新' : '添加') + '失败：' + error.message);
  }
};

// 删除成绩
const handleDeleteGrade = async (record) => {
  try {
    await deleteGrade(record.id);
    Message.success('成绩删除成功');
    fetchGrades();
  } catch (error) {
    Message.error('删除失败：' + error.message);
  }
};

// 初始化
onMounted(() => {
  fetchGrades();
  fetchStudents();
  fetchCourses();
});
</script>

<style scoped>
.grade {
  padding: 20px;
}

.operation-bar {
  margin-bottom: 16px;
}
</style> 