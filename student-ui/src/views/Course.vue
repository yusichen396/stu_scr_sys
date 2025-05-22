<template>
  <div class="course">
    <a-typography-title :heading="2">课程管理</a-typography-title>
    <div class="operation-bar">
      <a-space>
        <a-button type="primary" @click="showAddCourseModal">
          <template #icon><icon-plus /></template>
          添加课程
        </a-button>
        <a-input-search
          v-model="searchKeyword"
          placeholder="请输入课程名称或编号"
          style="width: 300px"
          @search="handleSearch"
        />
      </a-space>
    </div>
    <a-table 
      :columns="columns" 
      :data="courses" 
      :pagination="pagination"
      :loading="loading"
      :scroll="{ x: '100%' }"
      :bordered="false"
      :stripe="true"
      @page-change="onPageChange"
    >
      <template #operations="{ record }">
        <a-space>
          <a-button type="text" size="small" @click="showEditCourseModal(record)">
            <template #icon><icon-edit /></template>
            编辑
          </a-button>
          <a-button type="text" status="danger" size="small" @click="handleDeleteCourse(record)">
            <template #icon><icon-delete /></template>
            删除
          </a-button>
        </a-space>
      </template>
    </a-table>

    <!-- 添加/编辑课程弹窗 -->
    <a-modal
      v-model:visible="courseModalVisible"
      :title="isEdit ? '编辑课程' : '添加课程'"
      @ok="handleSubmitCourse"
      @cancel="closeCourseModal"
    >
      <a-form :model="courseForm" ref="courseFormRef">
        <a-form-item field="courseId" label="课程编号" :rules="[{ required: true, message: '请输入课程编号' }]">
          <a-input v-model="courseForm.courseId" placeholder="请输入课程编号" />
        </a-form-item>
        <a-form-item field="name" label="课程名称" :rules="[{ required: true, message: '请输入课程名称' }]">
          <a-input v-model="courseForm.name" placeholder="请输入课程名称" />
        </a-form-item>
        <a-form-item field="credit" label="学分" :rules="[{ required: true, message: '请输入学分' }]">
          <a-input-number v-model="courseForm.credit" placeholder="请输入学分" :min="0" :max="10" :precision="1" />
        </a-form-item>
        <a-form-item field="teacher" label="授课教师">
          <a-input v-model="courseForm.teacher" placeholder="请输入授课教师" />
        </a-form-item>
        <a-form-item field="courseType" label="课程类型">
          <a-select v-model="courseForm.courseType" placeholder="请选择课程类型">
            <a-option value="必修">必修</a-option>
            <a-option value="选修">选修</a-option>
          </a-select>
        </a-form-item>
        <a-form-item field="description" label="课程描述">
          <a-textarea v-model="courseForm.description" placeholder="请输入课程描述" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { Message } from '@arco-design/web-vue';
import { IconPlus, IconEdit, IconDelete } from '@arco-design/web-vue/es/icon';
import { getCourses, addCourse, updateCourse, deleteCourse } from '../api/course';

const columns = [
  {
    title: '课程编号',
    dataIndex: 'courseId',
    width: 120,
  },
  {
    title: '课程名称',
    dataIndex: 'name',
    width: 200,
  },
  {
    title: '学分',
    dataIndex: 'credit',
    width: 80,
  },
  {
    title: '授课教师',
    dataIndex: 'teacher',
    width: 120,
  },
  {
    title: '课程类型',
    dataIndex: 'courseType',
    width: 100,
  },
  {
    title: '操作',
    slotName: 'operations',
    width: 160,
    fixed: 'right',
  },
];

// 课程数据
const courses = ref([]);
const loading = ref(false);
const searchKeyword = ref('');
const pagination = reactive({
  total: 0,
  current: 1,
  pageSize: 10,
});

// 课程表单相关
const courseModalVisible = ref(false);
const courseFormRef = ref(null);
const isEdit = ref(false);
const courseForm = reactive({
  id: null,
  courseId: '',
  name: '',
  credit: 0,
  teacher: '',
  courseType: '必修',
  description: ''
});

// 获取课程列表
const fetchCourses = async () => {
  try {
    loading.value = true;
    const params = {
      current: pagination.current,
      size: pagination.pageSize,
      keyword: searchKeyword.value
    };
    const res = await getCourses(params);
    courses.value = res.data.data.records;
    pagination.total = res.data.data.total;
  } catch (error) {
    Message.error('获取课程列表失败：' + error.message);
  } finally {
    loading.value = false;
  }
};

// 搜索
const handleSearch = () => {
  pagination.current = 1;
  fetchCourses();
};

// 分页
const onPageChange = (current) => {
  pagination.current = current;
  fetchCourses();
};

// 显示添加课程弹窗
const showAddCourseModal = () => {
  isEdit.value = false;
  courseForm.id = null;
  courseForm.courseId = '';
  courseForm.name = '';
  courseForm.credit = 0;
  courseForm.teacher = '';
  courseForm.courseType = '必修';
  courseForm.description = '';
  courseModalVisible.value = true;
};

// 显示编辑课程弹窗
const showEditCourseModal = (record) => {
  isEdit.value = true;
  Object.assign(courseForm, record);
  courseModalVisible.value = true;
};

// 关闭课程弹窗
const closeCourseModal = () => {
  courseModalVisible.value = false;
  courseFormRef.value?.resetFields();
};

// 提交课程表单
const handleSubmitCourse = async () => {
  try {
    await courseFormRef.value.validate();
    
    if (isEdit.value) {
      await updateCourse(courseForm);
      Message.success('课程信息更新成功');
    } else {
      await addCourse(courseForm);
      Message.success('课程添加成功');
    }
    
    closeCourseModal();
    fetchCourses();
  } catch (error) {
    Message.error((isEdit.value ? '更新' : '添加') + '失败：' + error.message);
  }
};

// 删除课程
const handleDeleteCourse = async (record) => {
  try {
    await deleteCourse(record.id);
    Message.success('课程删除成功');
    fetchCourses();
  } catch (error) {
    Message.error('删除失败：' + error.message);
  }
};

// 初始化
onMounted(() => {
  fetchCourses();
});
</script>

<style scoped>
.course {
  padding: 20px;
}

.operation-bar {
  margin-bottom: 16px;
}
</style> 