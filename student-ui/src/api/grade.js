import axios from 'axios';

const baseURL = '/api';

// 获取成绩列表
export const getGrades = (params) => {
  return axios.get(`${baseURL}/grades/page`, { params });
};

// 添加成绩
export const addGrade = (data) => {
  return axios.post(`${baseURL}/grades`, data);
};

// 更新成绩信息
export const updateGrade = (data) => {
  return axios.put(`${baseURL}/grades`, data);
};

// 删除成绩
export const deleteGrade = (id) => {
  return axios.delete(`${baseURL}/grades/${id}`);
}; 