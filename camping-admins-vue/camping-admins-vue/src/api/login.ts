// src/api/login.ts
import type { AdminInfoData, LoginFormData, LoginResponseData } from '@/types/login';
import request from '@/utils/request';
import qs from 'qs';
import type { ApiResponse } from '../types/api';

/**
 * 管理员登录接口
 * @param data - 登录表单数据
 * @returns Promise<ApiResponse<LoginResponseData>>
 */
export const adminLogin = (data: LoginFormData): Promise<ApiResponse<LoginResponseData>> => {
  return request<LoginResponseData>({
    url: '/admin/index',
    method: 'post',
    data: qs.stringify(data), // 转换为 x-www-form-urlencoded 格式（Spring Security 要求）
  });
};

/**
 * 获取管理员详细信息（登录成功后调用）
 * @returns Promise<ApiResponse<AdminInfoData>>
 */
export const getAdminInfo = (): Promise<ApiResponse<AdminInfoData>> => {
  return request<AdminInfoData>({
    url: '/admin/info',
    method: 'post',
  });
};