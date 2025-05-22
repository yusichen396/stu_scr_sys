<template>
  <div class="login-container">
    <div class="login-content">
      <div class="login-left">
        <div class="welcome-text">
          <h1>欢迎使用</h1>
          <h2>学生成绩管理系统</h2>
          <p>高效、便捷的成绩管理解决方案</p>
        </div>
        <div class="animation-container">
          <!-- 这里可以放置SVG动画 -->
          <svg class="login-animation" viewBox="0 0 200 200" xmlns="http://www.w3.org/2000/svg">
            <path fill="#165DFF" d="M45.7,-78.2C58.9,-71.3,69.1,-58.1,76.8,-43.5C84.5,-28.9,89.7,-13.9,89.1,0.7C88.5,15.3,82.1,29.6,73.5,42.1C64.9,54.6,54.1,65.3,41.2,72.8C28.3,80.3,13.2,84.6,-1.5,86.8C-16.2,89,-32.4,89.1,-46.1,82.5C-59.8,75.9,-71,62.6,-78.1,47.3C-85.2,32,-88.2,14.7,-87.1,-2.1C-86,-18.9,-80.8,-37.3,-71.1,-52.5C-61.4,-67.7,-47.2,-79.7,-32.1,-85.1C-17,-90.5,-1,-89.3,13.3,-85.1C27.6,-80.9,54.2,-73.7,45.7,-78.2Z" transform="translate(100 100)" />
          </svg>
        </div>
      </div>
      <div class="login-right">
        <div class="login-form">
          <h2>系统登录</h2>
          <a-form
            ref="formRef"
            :model="form"
            :rules="rules"
            @submit="handleSubmit"
          >
            <a-form-item field="username" :rules="rules.username">
              <a-input
                v-model="form.username"
                placeholder="用户名"
                allow-clear
                autocomplete="username"
              >
                <template #prefix>
                  <icon-user />
                </template>
              </a-input>
            </a-form-item>
            <a-form-item field="password" :rules="rules.password">
              <a-input-password
                v-model="form.password"
                placeholder="密码"
                allow-clear
                autocomplete="current-password"
              >
                <template #prefix>
                  <icon-lock />
                </template>
              </a-input-password>
            </a-form-item>
            <a-form-item>
              <a-space>
                <a-checkbox v-model="form.remember">记住密码</a-checkbox>
                <a-link @click="showResetPassword">忘记密码？</a-link>
              </a-space>
            </a-form-item>
            <a-form-item>
              <a-button
                type="primary"
                long
                :loading="loading"
                @click="handleSubmit"
              >
                登录
              </a-button>
            </a-form-item>
          </a-form>
        </div>
      </div>
    </div>

    <!-- 重置密码对话框 -->
    <a-modal
      :visible="resetPasswordVisible"
      title="重置密码"
      @ok="handleResetPassword"
      @cancel="resetPasswordVisible = false"
    >
      <a-form
        ref="resetFormRef"
        :model="resetForm"
        :rules="resetRules"
      >
        <a-form-item field="email" :rules="resetRules.email">
          <a-input
            v-model="resetForm.email"
            placeholder="请输入注册邮箱"
            allow-clear
            autocomplete="email"
          >
            <template #prefix>
              <icon-email />
            </template>
          </a-input>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { Message } from '@arco-design/web-vue';
import { IconUser, IconLock, IconEmail } from '@arco-design/web-vue/es/icon';

const router = useRouter();
const formRef = ref(null);
const resetFormRef = ref(null);
const loading = ref(false);
const resetPasswordVisible = ref(false);

const form = reactive({
  username: '',
  password: '',
  remember: false
});

const resetForm = reactive({
  email: ''
});

const rules = {
  username: [
    { required: true, message: '请输入用户名' },
    { minLength: 3, message: '用户名长度不能小于3个字符' }
  ],
  password: [
    { required: true, message: '请输入密码' },
    { minLength: 4, message: '密码长度不能小于4个字符' }
  ]
};

const resetRules = {
  email: [
    { required: true, message: '请输入邮箱' },
    { type: 'email', message: '请输入正确的邮箱格式' }
  ]
};

const handleSubmit = async () => {
  if (!formRef.value) {
    Message.error('系统错误，请刷新页面重试');
    return;
  }

  try {
    loading.value = true;
    
    // 检查用户名和密码
    if (form.username === 'admin' && form.password === '1234') {
      // 存储登录状态
      localStorage.setItem('token', 'mock-token-12345');
      if (form.remember) {
        localStorage.setItem('username', form.username);
      }
      
      Message.success('登录成功');
      
      // 使用 replace 而不是 push，这样用户点击返回时不会回到登录页
      await router.replace('/');
    } else {
      Message.error('用户名或密码错误');
    }
  } catch (error) {
    console.error('登录过程发生错误:', error);
    Message.error('登录失败，请重试');
  } finally {
    loading.value = false;
  }
};

const showResetPassword = () => {
  resetPasswordVisible.value = true;
};

const handleResetPassword = async () => {
  const { error } = await resetFormRef.value.validate();
  if (error) return;

  try {
    // TODO: 实现重置密码API调用
    Message.success('重置密码链接已发送到您的邮箱');
    resetPasswordVisible.value = false;
  } catch (error) {
    Message.error('重置密码失败');
  }
};
</script>

<style scoped>
.login-container {
  height: 100vh;
  width: 100vw;
  background: linear-gradient(135deg, #f5f7ff 0%, #e4e8ff 100%);
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-content {
  width: 1000px;
  height: 600px;
  background: white;
  border-radius: 16px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
  display: flex;
  overflow: hidden;
}

.login-left {
  flex: 1;
  background: linear-gradient(135deg, #165DFF 0%, #0E42D2 100%);
  padding: 40px;
  color: white;
  position: relative;
  overflow: hidden;
}

.welcome-text {
  position: relative;
  z-index: 1;
}

.welcome-text h1 {
  font-size: 36px;
  margin-bottom: 16px;
}

.welcome-text h2 {
  font-size: 24px;
  margin-bottom: 24px;
  opacity: 0.9;
}

.welcome-text p {
  font-size: 16px;
  opacity: 0.8;
}

.login-animation {
  position: absolute;
  bottom: -50px;
  right: -50px;
  width: 400px;
  height: 400px;
  opacity: 0.1;
  animation: float 6s ease-in-out infinite;
}

.login-right {
  flex: 1;
  padding: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-form {
  width: 320px;
}

.login-form h2 {
  font-size: 24px;
  color: #1D2129;
  margin-bottom: 32px;
  text-align: center;
}

@keyframes float {
  0% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-20px);
  }
  100% {
    transform: translateY(0px);
  }
}

@media (max-width: 768px) {
  .login-content {
    width: 100%;
    height: 100%;
    border-radius: 0;
  }
  
  .login-left {
    display: none;
  }
  
  .login-right {
    padding: 20px;
  }
}
</style> 