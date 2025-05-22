import axios from 'axios';

const baseURL = '/api';

// 获取学生列表
export const getStudents = (params) => {
  return axios.get(`${baseURL}/students/page`, { params });
};

// 添加学生
export const addStudent = (data) => {
  return axios.post(`${baseURL}/students`, data);
};

// 更新学生信息
export const updateStudent = (data) => {
  return axios.put(`${baseURL}/students`, data);
};

// 删除学生
export const deleteStudent = (id) => {
  return axios.delete(`${baseURL}/students/${id}`);
}; 