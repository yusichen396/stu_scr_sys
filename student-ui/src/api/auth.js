import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
  timeout: 5000
});

// 请求拦截器
api.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// 响应拦截器
api.interceptors.response.use(
  response => response.data,
  error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export const login = (data) => {
  return api.post('/auth/login', data);
};

export const getUserInfo = () => {
  return api.get('/auth/user-info');
};

export const updateUserInfo = (data) => {
  return api.put('/auth/user-info', data);
};

export const changePassword = (data) => {
  return api.put('/auth/change-password', data);
};

export const logout = () => {
  return api.post('/auth/logout');
}; 