<!-- src/views/dashboard/index.vue -->
<template>
  <div class="dashboard-container">
    <!-- 页面头部 -->
    <div class="page-header">
      <h2>数据概览</h2>
      <p>实时监控露营平台核心运营数据</p>
    </div>

    <!-- 数据卡片区域 -->
    <div class="stats-cards">
      <el-card class="stat-card">
        <div class="stat-icon order-icon">
          <el-icon><ShoppingCart /></el-icon>
        </div>
        <div class="stat-content">
          <div class="stat-number">{{ statsData.todayOrders }}</div>
          <div class="stat-label">今日订单</div>
          <div class="stat-change positive">+{{ statsData.orderGrowth }}%</div>
        </div>
      </el-card>

      <el-card class="stat-card">
        <div class="stat-icon revenue-icon">
          <el-icon><Money /></el-icon>
        </div>
        <div class="stat-content">
          <div class="stat-number">¥{{ statsData.todayRevenue }}</div>
          <div class="stat-label">今日营收</div>
          <div class="stat-change positive">+{{ statsData.revenueGrowth }}%</div>
        </div>
      </el-card>

      <el-card class="stat-card">
        <div class="stat-icon camp-icon">
          <el-icon><Location /></el-icon>
        </div>
        <div class="stat-content">
          <div class="stat-number">{{ statsData.availableCamps }}</div>
          <div class="stat-label">可订营位</div>
          <div class="stat-change negative">-{{ statsData.campOccupancy }}%</div>
        </div>
      </el-card>

      <el-card class="stat-card">
        <div class="stat-icon user-icon">
          <el-icon><UserFilled /></el-icon>
        </div>
        <div class="stat-content">
          <div class="stat-number">{{ statsData.newUsers }}</div>
          <div class="stat-label">新增用户</div>
          <div class="stat-change positive">+{{ statsData.userGrowth }}%</div>
        </div>
      </el-card>
    </div>

    <!-- 图表区域 -->
    <div class="charts-section">
      <el-card class="chart-card">
        <template #header>
          <span>近7日订单趋势</span>
        </template>
        <div class="chart-container">
          <div class="mock-chart">📊 订单趋势图表（后续集成 ECharts）</div>
        </div>
      </el-card>

      <el-card class="chart-card">
        <template #header>
          <span>营位类型占比</span>
        </template>
        <div class="chart-container">
          <div class="mock-chart">📈 营位占比饼图（后续集成 ECharts）</div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElCard, ElIcon } from 'element-plus';
import { ShoppingCart, Money, Location, UserFilled } from '@element-plus/icons-vue';

// 模拟数据类型定义
interface DashboardStats {
  todayOrders: number;
  todayRevenue: number;
  availableCamps: number;
  newUsers: number;
  orderGrowth: number;
  revenueGrowth: number;
  campOccupancy: number;
  userGrowth: number;
}

// 初始化模拟数据
const statsData = ref<DashboardStats>({
  todayOrders: 28,
  todayRevenue: 12850,
  availableCamps: 126,
  newUsers: 56,
  orderGrowth: 12.5,
  revenueGrowth: 8.2,
  campOccupancy: 15.3,
  userGrowth: 18.7,
});

// 页面加载时获取真实数据（后续替换为接口请求）
onMounted(() => {
  // 模拟接口请求延迟
  setTimeout(() => {
    console.log('数据看板页面加载完成，可在此处调用接口获取真实数据');
  }, 1000);
});
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: 100%;
}

.page-header {
  margin-bottom: 24px;
}

.page-header h2 {
  font-size: 20px;
  font-weight: 600;
  margin: 0 0 4px 0;
}

.page-header p {
  color: #909399;
  margin: 0;
}

.stats-cards {
  display: flex;
  gap: 20px;
  margin-bottom: 24px;
  flex-wrap: wrap;
}

.stat-card {
  flex: 1;
  min-width: 220px;
  display: flex;
  align-items: center;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  margin-right: 20px;
  color: #fff;
}

.order-icon {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.revenue-icon {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.camp-icon {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.user-icon {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}

.stat-content {
  flex: 1;
}

.stat-number {
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 4px;
  color: #303133;
}

.stat-label {
  font-size: 14px;
  color: #909399;
  margin-bottom: 4px;
}

.stat-change {
  font-size: 12px;
  font-weight: 500;
}

.positive {
  color: #67c23a;
}

.negative {
  color: #f56c6c;
}

.charts-section {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
}

.chart-card {
  flex: 1;
  min-width: 300px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.chart-container {
  height: 300px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #909399;
  font-size: 16px;
}

.mock-chart {
  text-align: center;
}
</style>