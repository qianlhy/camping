// src/env.d.ts
/// <reference types="vite/client" />

// 声明环境变量类型
interface ImportMetaEnv {
    readonly VITE_API_BASE_URL: string; // 接口前缀
    readonly VITE_DEBUG: boolean; // 是否开启调试
  }
  
  interface ImportMeta {
    readonly env: ImportMetaEnv;
  }