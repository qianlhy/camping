// src/store/adminUser.ts
import { adminLogin, getAdminInfo } from '@/api/login';
import type { AdminInfoData, LoginFormData, LoginResponseData } from '@/types/login';
import { ElMessage } from 'element-plus';
import { defineStore } from 'pinia';

/**
 * 管理员状态类型约束
 */
interface AdminUserState {
  token: string; // 登录令牌
  username: string; // 用户名
  role: string; // 角色（ADMIN/OPERATOR）
  realName: string; // 真实姓名
}

/**
 * 管理员用户 Store（登录/退出/信息存储）
 */
export const useAdminUserStore = defineStore('adminUser', {
  state: (): AdminUserState => ({
    // 从 localStorage 读取持久化数据，初始化状态
    token: localStorage.getItem('adminToken') || '',
    username: localStorage.getItem('adminUsername') || '',
    role: localStorage.getItem('adminRole') || '',
    realName: localStorage.getItem('adminRealName') || '',
  }),
  actions: {
    /**
     * 管理员登录
     * @param loginForm - 登录表单数据
     * @returns Promise<void>
     */
    async login(loginForm: LoginFormData): Promise<void> {
      try {
        // 调用登录接口，获取 token
        const res = await adminLogin(loginForm);
        const tokenData: LoginResponseData = res.data;

        // 更新 state 中的 token
        this.token = tokenData.token;
        localStorage.setItem('adminToken', tokenData.token);

        // 登录成功后，获取管理员详细信息
        // await this.fetchAdminInfo();

        ElMessage.success('登录成功！');
      } catch (error) {
        ElMessage.error('登录失败，请检查账号或密码');
        throw error; // 抛出异常，便于页面处理
      }
    },

    /**
     * 获取管理员详细信息并存储
     */
    async fetchAdminInfo(): Promise<void> {
      try {
        const res = await getAdminInfo();
        const adminInfo: AdminInfoData = res.data;

        // 更新 state 中的用户信息
        this.username = adminInfo.username;
        this.role = adminInfo.role;
        this.realName = adminInfo.realName || '';

        // 持久化存储到 localStorage
        localStorage.setItem('adminUsername', adminInfo.username);
        localStorage.setItem('adminRole', adminInfo.role);
        if (adminInfo.realName) {
          localStorage.setItem('adminRealName', adminInfo.realName);
        }
      } catch (error) {
        ElMessage.error('获取管理员信息失败');
        throw error;
      }
    },

    /**
     * 管理员退出登录（清除所有状态和持久化数据）
     */
    logout(): void {
      // 清除 state 数据
      this.token = '';
      this.username = '';
      this.role = '';
      this.realName = '';

      // 清除 localStorage 中的持久化数据
      localStorage.removeItem('adminToken');
      localStorage.removeItem('adminUsername');
      localStorage.removeItem('adminRole');
      localStorage.removeItem('adminRealName');

      ElMessage.info('已安全退出登录！');
    },
  },
});