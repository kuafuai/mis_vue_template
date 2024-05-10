import axios from 'axios';
import { ElMessage, ElMessageBox } from 'element-plus';

// 创建axios实例
const service = axios.create({
    baseURL: import.meta.env.VITE_APP_BASE_API,
    timeout: 50000, // 请求超时时间：50s
    headers: { 'Content-Type': 'application/json;charset=utf-8' },
});


// 请求拦截器
service.interceptors.request.use(
    (config) => {
      
      if (!config.headers) {
        throw new Error(`Expected 'config' and 'config.headers' not to be undefined`);
      }
  
      return config;
    },
    (error) => {
      return Promise.reject(error);
    },
);

// 响应拦截器
service.interceptors.response.use(
    (response) => {
      const res = response.data;
      const { code, message } = res;
      if (code === 0) {
        return res;
      } else {
        
        if (code === -1) {
          handleError();
        } else {
          ElMessage({
            message: message || '系统出错',
            type: 'error',
            duration: 5 * 1000,
          });
        }
        return Promise.reject(new Error(message || 'Error'));
      }
    },
    (error) => {
      console.log('请求异常：', error);
      const { message } = error.response.data;
      // 未认证
      if (error.response.status === 401) {
        handleError();
      } else {
        ElMessage({
          message: '网络异常，请稍后再试!',
          type: 'error',
          duration: 5 * 1000,
        });
        return Promise.reject(new Error(message || 'Error'));
      }
    },
);
  
// 统一处理请求响应异常
function handleError() {
    
}
  
// 导出实例
export default service;