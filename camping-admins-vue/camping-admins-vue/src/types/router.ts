// src/types/router.ts
/**
 * 路由元信息类型约束
 */
export interface RouteMeta {
    title: string; // 页面标题
    requiresAuth?: boolean; // 是否需要登录权限（默认 false）
  }