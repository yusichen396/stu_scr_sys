<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { Message } from '@arco-design/web-vue';
import {
  IconHome,
  IconUser,
  IconBook,
  IconFile,
  IconExport,
  IconExperiment
} from '@arco-design/web-vue/es/icon';

const router = useRouter();
const route = useRoute();
const collapsed = ref(false);
const username = ref(localStorage.getItem('username') || 'admin');

const isLoggedIn = computed(() => {
  return !!localStorage.getItem('token');
});

const currentRoute = computed(() => {
  return route.path;
});

// 修改计算属性，只在登录页不显示侧边栏
const showSidebar = computed(() => {
  return isLoggedIn.value && currentRoute.value !== '/login';
});

const handleCollapse = (val) => {
  collapsed.value = val;
};

const handleMenuClick = (key) => {
  router.push(key);
};

const handleLogout = () => {
  // 清除登录状态
  localStorage.removeItem('token');
  localStorage.removeItem('username');
  
  Message.success('已退出登录');
  
  // 跳转到登录页
  router.replace('/login');
};

// 初始加载时检查登录状态
onMounted(() => {
  if (!isLoggedIn.value && route.path !== '/login') {
    router.replace('/login');
  }
});
</script>

<template>
  <router-view v-if="!isLoggedIn"></router-view>
  
  <a-layout v-else class="layout">
    <!-- 头部 -->
    <a-layout-header class="header">
      <div class="header-left">
        <div class="logo">
          <span>学生成绩管理系统</span>
        </div>
      </div>
      <div class="header-right">
        <a-dropdown>
          <a-button type="text">
            <template #icon><icon-user /></template>
            {{ username }}
          </a-button>
          <template #content>
            <a-doption @click="handleLogout">
              <template #icon><icon-export /></template>
              退出登录
            </a-doption>
          </template>
        </a-dropdown>
      </div>
    </a-layout-header>

    <a-layout>
      <!-- 侧边栏 - 只在非首页显示 -->
      <a-layout-sider
        v-if="showSidebar"
        class="sider"
        :collapsed="collapsed"
        :collapsible="true"
        :width="220"
        breakpoint="xl"
        @collapse="handleCollapse"
      >
        <div class="menu-wrapper">
          <a-menu
            :selected-keys="[currentRoute]"
            :default-open-keys="['dashboard']"
            :style="{ width: '100%' }"
            @menu-item-click="handleMenuClick"
          >
            <a-menu-item key="/">
              <template #icon><icon-home /></template>
              首页
            </a-menu-item>
            <a-menu-item key="/student">
              <template #icon><icon-user /></template>
              学生管理
            </a-menu-item>
            <a-menu-item key="/course">
              <template #icon><icon-book /></template>
              课程管理
            </a-menu-item>
            <a-menu-item key="/grade">
              <template #icon><icon-file /></template>
              成绩管理
            </a-menu-item>
            <a-menu-item key="/test-connection">
              <template #icon><icon-experiment /></template>
              连接测试
            </a-menu-item>
          </a-menu>
        </div>
      </a-layout-sider>

      <!-- 内容区 -->
      <a-layout-content class="content" :class="{ 'content-full': !showSidebar }">
        <div class="content-wrapper">
          <router-view></router-view>
        </div>
      </a-layout-content>
    </a-layout>

    <!-- 底部 -->
    <a-layout-footer class="footer">
      <div class="footer-content">
        <span>© 2025 学生成绩管理系统</span>
      </div>
    </a-layout-footer>
  </a-layout>
</template>

<style scoped>
.layout {
  height: 100vh;
  width: 100vw;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  background: var(--color-bg-2);
  border-bottom: 1px solid var(--color-border);
  height: 60px;
}

.header-left {
  display: flex;
  align-items: center;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
}

.logo span {
  font-size: 18px;
  font-weight: bold;
  color: var(--color-text-1);
}

.header-right {
  display: flex;
  align-items: center;
}

.sider {
  background: var(--color-bg-2);
  border-right: 1px solid var(--color-border);
  height: calc(100vh - 60px - 48px);
}

.menu-wrapper {
  height: 100%;
  overflow-y: auto;
}

.content {
  padding: 20px;
  background: var(--color-bg-1);
  height: calc(100vh - 60px - 48px);
  overflow-y: auto;
}

.content-full {
  margin-left: 0 !important;
}

.content-wrapper {
  background: var(--color-bg-2);
  padding: 20px;
  border-radius: 4px;
  min-height: calc(100% - 40px);
}

.footer {
  background: var(--color-bg-2);
  border-top: 1px solid var(--color-border);
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.footer-content {
  display: flex;
  gap: 24px;
  color: var(--color-text-3);
  font-size: 14px;
}

/* 响应式布局 */
@media (max-width: 768px) {
  .header {
    padding: 0 12px;
  }

  .logo span {
    display: none;
  }

  .content {
    padding: 12px;
  }

  .content-wrapper {
    padding: 12px;
  }
}
</style>
