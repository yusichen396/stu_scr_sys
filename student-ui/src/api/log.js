import axios from 'axios';
export const getLogs = (params) => axios.get('/api/logs', { params }); 