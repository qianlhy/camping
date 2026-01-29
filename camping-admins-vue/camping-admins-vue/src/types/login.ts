// src/types/login.ts
/**
 * 登录表单数据类型
 */
export interface LoginFormData {
    username: string; // 管理员账号
    password: string; // 管理员密码
  }
  
  /**
   * 登录接口返回数据类型（token 信息）
   */
  export interface LoginResponseData {
    token: string; // 登录令牌（JWT）
  }
  
  /**
   * 管理员信息类型（登录成功后获取）
   */
  export interface AdminInfoData {
    username: string; // 用户名
    role: string; // 角色（ADMIN/OPERATOR）
    realName?: string; // 真实姓名（可选）
  }