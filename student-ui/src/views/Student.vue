<template>
  <div class="student">
    <a-typography-title :heading="2">学生管理</a-typography-title>
    <div class="operation-bar">
      <a-space>
        <a-button type="primary" @click="showAddStudentModal">
          <template #icon><icon-plus /></template>
          添加学生
        </a-button>
        <a-input-search
          v-model="searchKeyword"
          placeholder="请输入学生姓名或学号"
          style="width: 300px"
          @search="handleSearch"
        />
      </a-space>
    </div>
    <a-table 
      :columns="columns" 
      :data="students" 
      :pagination="pagination"
      :loading="loading"
      :scroll="{ x: '100%' }"
      :bordered="false"
      :stripe="true"
      @page-change="onPageChange"
    >
      <template #operations="{ record }">
        <a-space>
          <a-button type="text" size="small" @click="showEditStudentModal(record)">
            <template #icon><icon-edit /></template>
            编辑
          </a-button>
          <a-button type="text" status="danger" size="small" @click="handleDeleteStudent(record)">
            <template #icon><icon-delete /></template>
            删除
          </a-button>
        </a-space>
      </template>
    </a-table>

    <!-- 添加/编辑学生弹窗 -->
    <a-modal
      v-model:visible="studentModalVisible"
      :title="isEdit ? '编辑学生' : '添加学生'"
      @ok="handleSubmitStudent"
      @cancel="closeStudentModal"
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
            <a-radio value="男">男</a-radio>
            <a-radio value="女">女</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item field="age" label="年龄">
          <a-input-number v-model="studentForm.age" placeholder="请输入年龄" />
        </a-form-item>
        <a-form-item field="className" label="班级">
          <a-input v-model="studentForm.className" placeholder="请输入班级" />
        </a-form-item>
        <a-form-item field="major" label="专业">
          <a-input v-model="studentForm.major" placeholder="请输入专业" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { Message } from '@arco-design/web-vue';
import { IconPlus, IconEdit, IconDelete } from '@arco-design/web-vue/es/icon';
import { getStudents, addStudent, updateStudent, deleteStudent } from '../api/student';

const columns = [
  {
    title: '学号',
    dataIndex: 'studentId',
    width: 120,
  },
  {
    title: '姓名',
    dataIndex: 'name',
    width: 120,
  },
  {
    title: '性别',
    dataIndex: 'gender',
    width: 80,
  },
  {
    title: '年龄',
    dataIndex: 'age',
    width: 80,
  },
  {
    title: '专业',
    dataIndex: 'major',
    width: 200,
  },
  {
    title: '班级',
    dataIndex: 'className',
    width: 120,
  },
  {
    title: '操作',
    slotName: 'operations',
    width: 160,
    fixed: 'right',
  },
];

// 学生数据
const students = ref([]);
const loading = ref(false);
const searchKeyword = ref('');
const pagination = reactive({
  total: 0,
  current: 1,
  pageSize: 10,
});

// 学生表单相关
const studentModalVisible = ref(false);
const studentFormRef = ref(null);
const isEdit = ref(false);
const studentForm = reactive({
  id: null,
  studentId: '',
  name: '',
  gender: '男',
  age: 20,
  className: '',
  major: ''
});

// 获取学生列表
const fetchStudents = async () => {
  try {
    loading.value = true;
    const params = {
      current: pagination.current,
      size: pagination.pageSize,
      keyword: searchKeyword.value
    };
    const res = await getStudents(params);
    students.value = res.data.data.records;
    pagination.total = res.data.data.total;
  } catch (error) {
    Message.error('获取学生列表失败：' + error.message);
  } finally {
    loading.value = false;
  }
};

// 搜索
const handleSearch = () => {
  pagination.current = 1;
  fetchStudents();
};

// 分页
const onPageChange = (current) => {
  pagination.current = current;
  fetchStudents();
};

// 显示添加学生弹窗
const showAddStudentModal = () => {
  isEdit.value = false;
  studentForm.id = null;
  studentForm.studentId = '';
  studentForm.name = '';
  studentForm.gender = '男';
  studentForm.age = 20;
  studentForm.className = '';
  studentForm.major = '';
  studentModalVisible.value = true;
};

// 显示编辑学生弹窗
const showEditStudentModal = (record) => {
  isEdit.value = true;
  Object.assign(studentForm, record);
  studentModalVisible.value = true;
};

// 关闭学生弹窗
const closeStudentModal = () => {
  studentModalVisible.value = false;
  studentFormRef.value?.resetFields();
};

// 提交学生表单
const handleSubmitStudent = async () => {
  try {
    await studentFormRef.value.validate();
    
    if (isEdit.value) {
      await updateStudent(studentForm);
      Message.success('学生信息更新成功');
    } else {
      await addStudent(studentForm);
      Message.success('学生添加成功');
    }
    
    closeStudentModal();
    fetchStudents();
  } catch (error) {
    Message.error((isEdit.value ? '更新' : '添加') + '失败：' + error.message);
  }
};

// 删除学生
const handleDeleteStudent = async (record) => {
  try {
    await deleteStudent(record.id);
    Message.success('学生删除成功');
    fetchStudents();
  } catch (error) {
    Message.error('删除失败：' + error.message);
  }
};

// 初始化
onMounted(() => {
  fetchStudents();
});
</script>

<style scoped>
.student {
  padding: 20px;
}

.operation-bar {
  margin-bottom: 16px;
}
</style> 