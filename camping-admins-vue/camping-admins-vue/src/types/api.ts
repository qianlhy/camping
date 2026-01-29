// src/types/api.ts
/**
 * 后端统一返回格式泛型接口
 * @template T - 响应数据 data 的类型，默认空对象
 */
export interface ApiResponse<T = Record<string, never>> {
    code: number; // 状态码（200 成功，其他失败）
    msg: string; // 提示信息
    data: T; // 响应数据（泛型，按需指定具体类型）
  }

  