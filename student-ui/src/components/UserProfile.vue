<template>
  <div class="user-profile">
    <a-modal
      :visible="visible"
      title="个人中心"
      :mask-closable="false"
      :footer="false"
      @cancel="handleCancel"
    >
      <a-tabs>
        <a-tab-pane key="info" title="个人信息">
          <a-form
            ref="formRef"
            :model="form"
            :rules="rules"
            @submit="handleSubmit"
          >
            <a-form-item field="name" label="姓名">
              <a-input v-model="form.name" placeholder="请输入姓名" />
            </a-form-item>
            <a-form-item field="email" label="邮箱">
              <a-input v-model="form.email" placeholder="请输入邮箱" />
            </a-form-item>
            <a-form-item field="phone" label="手机号">
              <a-input v-model="form.phone" placeholder="请输入手机号" />
            </a-form-item>
            <a-form-item>
              <a-space>
                <a-button type="primary" @click="handleSubmit">
                  保存修改
                </a-button>
                <a-button @click="handleCancel">
                  取消
                </a-button>
              </a-space>
            </a-form-item>
          </a-form>
        </a-tab-pane>
        <a-tab-pane key="password" title="修改密码">
          <a-form
            ref="passwordFormRef"
            :model="passwordForm"
            :rules="passwordRules"
            @submit="handlePasswordSubmit"
          >
            <a-form-item field="oldPassword" label="原密码">
              <a-input-password
                v-model="passwordForm.oldPassword"
                placeholder="请输入原密码"
              />
            </a-form-item>
            <a-form-item field="newPassword" label="新密码">
              <a-input-password
                v-model="passwordForm.newPassword"
                placeholder="请输入新密码"
              />
            </a-form-item>
            <a-form-item field="confirmPassword" label="确认密码">
              <a-input-password
                v-model="passwordForm.confirmPassword"
                placeholder="请再次输入新密码"
              />
            </a-form-item>
            <a-form-item>
              <a-space>
                <a-button type="primary" @click="handlePasswordSubmit">
                  修改密码
                </a-button>
                <a-button @click="handleCancel">
                  取消
                </a-button>
              </a-space>
            </a-form-item>
          </a-form>
        </a-tab-pane>
      </a-tabs>
    </a-modal>
  </div>
</template>

<script setup>
import { ref, reactive, watch, onMounted } from 'vue';
import { Message } from '@arco-design/web-vue';
import { updateUserInfo, changePassword, getUserInfo } from '../api/auth';

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(['update:visible']);

const formRef = ref(null);
const passwordFormRef = ref(null);

const form = reactive({
  name: '',
  email: '',
  phone: ''
});

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
});

const rules = {
  name: [{ required: true, message: '请输入姓名' }],
  email: [
    { required: true, message: '请输入邮箱' },
    { type: 'email', message: '请输入正确的邮箱格式' }
  ],
  phone: [
    { required: true, message: '请输入手机号' },
    { match: /^1[3-9]\d{9}$/, message: '请输入正确的手机号格式' }
  ]
};

const passwordRules = {
  oldPassword: [{ required: true, message: '请输入原密码' }],
  newPassword: [
    { required: true, message: '请输入新密码' },
    { minLength: 6, message: '密码长度不能小于6个字符' }
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码' },
    {
      validator: (value, cb) => {
        if (value !== passwordForm.newPassword) {
          cb('两次输入的密码不一致');
        }
        cb();
      }
    }
  ]
};

const handleSubmit = async () => {
  const { error } = await formRef.value.validate();
  if (error) return;

  try {
    await updateUserInfo(form);
    Message.success('个人信息修改成功');
    emit('update:visible', false);
  } catch (error) {
    Message.error('修改失败');
  }
};

const handlePasswordSubmit = async () => {
  const { error } = await passwordFormRef.value.validate();
  if (error) return;

  try {
    await changePassword({
      oldPassword: passwordForm.oldPassword,
      newPassword: passwordForm.newPassword
    });
    Message.success('密码修改成功');
    emit('update:visible', false);
  } catch (error) {
    Message.error('密码修改失败');
  }
};

const handleCancel = () => {
  emit('update:visible', false);
};

// 加载用户信息
onMounted(async () => {
  try {
    const userInfo = await getUserInfo();
    form.name = userInfo.name || '';
    form.email = userInfo.email || '';
    form.phone = userInfo.phone || '';
  } catch (error) {
    console.error('获取用户信息失败', error);
  }
});
</script>

<style scoped>
.user-profile {
  :deep(.arco-modal) {
    width: 500px;
  }
}
</style> 