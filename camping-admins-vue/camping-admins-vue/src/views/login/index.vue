<!-- src/views/login/index.vue -->
<template>
  <div class="login-container">
    <el-card class="login-card">
      <div class="login-title">露营管理后台</div>
      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginRules"
        label-width="80px"
        class="login-form"
      >
        <!-- 用户名输入框 -->
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入管理员账号"
            prefix-icon="User"
            clearable
            type="text"
          />
        </el-form-item>

        <!-- 密码输入框 -->
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入管理员密码"
            prefix-icon="Lock"
            clearable
            :show-password="showPassword"
            @blur="handlePasswordBlur"
          />
          <el-checkbox v-model="showPassword" class="show-password-checkbox">
            显示密码
          </el-checkbox>
        </el-form-item>

        <!-- 登录按钮 -->
        <el-form-item class="login-btn-item">
          <el-button
            type="primary"
            class="login-btn"
            :loading="loginLoading"
            @click="handleLogin"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import { useRouter, RouteLocationNormalized } from 'vue-router';
import { ElForm, ElFormItem, ElInput, ElButton, ElCheckbox, ElMessage, FormInstance, FormRules } from 'element-plus';
import { useAdminUserStore } from '@/store/adminUser';
import { LoginFormData } from '@/types/login';

// 1. 初始化路由和 Store
const router = useRouter();
const adminUserStore = useAdminUserStore();

// 2. 响应式变量定义（补充 TS 类型）
const loginFormRef = ref<FormInstance | null>(null); // 表单实例引用
const loginLoading = ref<boolean>(false); // 登录按钮加载状态
const showPassword = ref<boolean>(false); // 是否显示密码

// 3. 登录表单数据（严格遵循 LoginFormData 类型）
const loginForm = reactive<LoginFormData>({
  username: '',
  password: '',
});

// 4. 表单校验规则（严格遵循 FormRules 类型）
const loginRules = reactive<FormRules>({
  username: [
    { required: true, message: '请输入管理员账号', trigger: 'blur' },
    { min: 3, max: 20, message: '账号长度在 3 到 20 个字符之间', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入管理员密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符之间', trigger: 'blur' },
  ],
});

// 5. 密码输入框失焦事件（校验密码）
const handlePasswordBlur = (): void => {
  if (loginFormRef.value) {
    loginFormRef.value.validateField('password');
  }
};

// 6. 登录提交核心逻辑
const handleLogin = async (): Promise<void> => {
  // 第一步：表单校验（确保数据合法）
  if (!loginFormRef.value) return;
  try {
    await loginFormRef.value.validate();
  } catch (error) {
    ElMessage.error('表单填写有误，请检查！');
    return;
  }

  // 第二步：调用登录接口，提交数据
  loginLoading.value = true;
  try {
    await adminUserStore.login(loginForm);

    // 第三步：登录成功，跳转至目标页面（优先跳转之前访问的页面，否则跳转到仪表盘）
    const currentRoute = router.currentRoute as RouteLocationNormalized;
    const redirect = currentRoute.query.redirect as string || '/dashboard';
    await router.push(redirect);
  } catch (error) {
    console.error('登录失败：', error);
  } finally {
    loginLoading.value = false; // 关闭加载状态
  }
};
</script>

<style scoped>
.login-container {
  width: 100vw;
  height: 100vh;
  background-color: #f5f7fa;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-card {
  width: 400px;
  padding: 20px 30px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.login-title {
  font-size: 24px;
  color: #1989fa;
  text-align: center;
  margin-bottom: 30px;
  font-weight: bold;
}

.login-form {
  margin-top: 20px;
}

.show-password-checkbox {
  margin-top: 10px;
  text-align: right;
}

.login-btn-item {
  margin-top: 30px;
  text-align: center;
}

.login-btn {
  width: 100%;
  height: 40px;
  font-size: 16px;
}
</style>