// src/router/index.ts
// 导入布局和页面
import MainLayout from '@/layouts/MainLayout.vue';
import { useAdminUserStore } from '@/store/adminUser';
import type { RouteMeta } from '@/types/router';
import Dashboard from '@/views/dashboard/index.vue';
import Login from '@/views/login/index.vue';
import { ElMessage } from 'element-plus';
import { createRouter, createWebHashHistory } from 'vue-router';
import type { RouteRecordRaw } from 'vue-router';



// 定义路由规则（补充 RouteRecordRaw 类型约束，meta 遵循 RouteMeta 类型）
const routes: Array<RouteRecordRaw> = [
  // 登录页
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: {
      title: '管理员登录',
    } as RouteMeta,
  },
  // 后台主布局（嵌套所有后台页面）
  {
    path: '/',
    component: MainLayout,
    redirect: '/dashboard',
    children: [
      // 数据仪表盘（首页）
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: Dashboard,
        meta: {
          title: '数据看板',
          requiresAuth: true,
        } as RouteMeta,
      },
    ],
  },
  // 404 页面（重定向到仪表盘）
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard',
  },
];

// 创建路由实例
const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

// 路由守卫：拦截未登录请求，保护需要权限的页面
router.beforeEach((to, from, next) => {
  const adminUserStore = useAdminUserStore();
  const isLogin = !!adminUserStore.token; // 判断是否已登录（token 是否存在）
  const routeMeta = to.meta as RouteMeta;

  // 设置页面标题
  if (routeMeta.title) {
    document.title = `${routeMeta.title} - 露营管理后台`;
  }

  // 拦截需要登录权限的页面
  if (routeMeta.requiresAuth) {
    if (isLogin) {
      next();
    } else {
      ElMessage.warning('请先登录后再访问！');
      next({
        path: '/login',
        query: { redirect: to.fullPath }, // 记录跳转前的页面，登录后自动返回
      });
    }
  } else {
    // 无需登录的页面（如登录页），已登录用户自动跳转到仪表盘
    if (to.path === '/login' && isLogin) {
      next('/dashboard');
    } else {
      next();
    }
  }
});

export default router;