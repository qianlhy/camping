<!-- src/layouts/MainLayout.vue -->
<template>
  <div class="main-layout">
    <router-view />
  </div>
</template>

<script setup lang="ts">
// 后续补充侧边导航、顶部导航时，在此添加 TS 类型约束
</script>

<style scoped>
.main-layout {
  width: 100vw;
  height: 100vh;
  background-color: #f5f7fa;
}
</style>