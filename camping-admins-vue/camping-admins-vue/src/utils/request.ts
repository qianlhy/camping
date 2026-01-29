// src/utils/request.ts
import { useAdminUserStore } from '@/store/adminUser';
import { ApiResponse } from '@/types/api';
import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios';
import { ElMessage } from 'element-plus';

// 创建 axios 实例
const service: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
  }
});

// 请求拦截器：自动携带 token
service.interceptors.request.use(
  (config: AxiosRequestConfig): AxiosRequestConfig => {
    const adminUserStore = useAdminUserStore();
    // 携带 JWT token（Bearer 格式）
    if (adminUserStore.token) {
      config.headers = config.headers || {};
      config.headers['Authorization'] = `Bearer ${adminUserStore.token}`;
    }
    return config;
  },
  (error) => {
    console.error('请求出错：', error);
    ElMessage.error('请求发送失败，请稍后重试');
    return Promise.reject(error);
  }
);

// 响应拦截器：统一处理响应和异常
service.interceptors.response.use(
  <T = Record<string, never>>(response: AxiosResponse<ApiResponse<T>>): ApiResponse<T> => {
    const res = response.data;
    // 校验后端返回状态码
    if (res.code !== 200) {
      ElMessage.error(res.msg || '请求处理失败');
      return Promise.reject(new Error(res.msg || '请求处理失败'));
    }
    return res;
  },
  (error) => {
    console.error('响应出错：', error);
    const adminUserStore = useAdminUserStore();

    // 处理 HTTP 状态码异常
    if (error.response?.status === 401) {
      // Token 过期/未登录，清除登录态并跳转登录页
      adminUserStore.logout();
      ElMessage.error('登录态已过期，请重新登录');
      window.location.href = '/#/login';
    } else if (error.response?.status === 403) {
      ElMessage.error('无权限执行该操作');
    } else {
      ElMessage.error(error.message || '服务器内部错误，请稍后重试');
    }
    return Promise.reject(error);
  }
);

/**
 * 封装通用请求方法（支持泛型）
 * @template T - 响应数据 data 的类型
 * @param config - axios 请求配置
 * @returns Promise<ApiResponse<T>>
 */
const request = <T = Record<string, never>>(config: AxiosRequestConfig): Promise<ApiResponse<T>> => {
  return service(config) as Promise<ApiResponse<T>>;
};

export default request;