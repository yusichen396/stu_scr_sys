import axios from 'axios';

const baseURL = '/api';

// 获取课程列表
export const getCourses = (params) => {
  return axios.get(`${baseURL}/courses/page`, { params });
};

// 添加课程
export const addCourse = (data) => {
  return axios.post(`${baseURL}/courses`, data);
};

// 更新课程信息
export const updateCourse = (data) => {
  return axios.put(`${baseURL}/courses`, data);
};

// 删除课程
export const deleteCourse = (id) => {
  return axios.delete(`${baseURL}/courses/${id}`);
}; 