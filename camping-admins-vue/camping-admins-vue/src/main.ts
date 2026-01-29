// src/main.ts
// 导入全局样式
import '@/assets/css/global.css';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
// 导入 Element Plus 及其样式、图标
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import { createPinia } from 'pinia';
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';


// 创建 Vue 实例
const app = createApp(App);
const pinia = createPinia();

// 注册 Element Plus 所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}

// 全局注册插件
app.use(pinia);
app.use(router);
app.use(ElementPlus);

// 挂载 Vue 实例
app.mount('#app');