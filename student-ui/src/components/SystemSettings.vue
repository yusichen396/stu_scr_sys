<template>
  <div class="system-settings">
    <a-modal
      :visible="visible"
      title="系统设置"
      :mask-closable="false"
      :footer="false"
      @cancel="handleCancel"
    >
      <a-tabs>
        <a-tab-pane key="basic" title="基本设置">
          <a-form
            ref="formRef"
            :model="form"
            :rules="rules"
            @submit="handleSubmit"
          >
            <a-form-item field="systemName" label="系统名称">
              <a-input v-model="form.systemName" placeholder="请输入系统名称" />
            </a-form-item>
            <a-form-item field="systemLogo" label="系统Logo">
              <a-upload
                :custom-request="handleUpload"
                :show-file-list="false"
                accept="image/*"
              >
                <template #upload-button>
                  <div class="upload-trigger">
                    <img
                      v-if="form.systemLogo"
                      :src="form.systemLogo"
                      alt="logo"
                      class="preview-image"
                    />
                    <div v-else class="upload-placeholder">
                      <icon-upload />
                      <div>点击上传Logo</div>
                    </div>
                  </div>
                </template>
              </a-upload>
            </a-form-item>
            <a-form-item field="theme" label="主题设置">
              <a-radio-group v-model="form.theme">
                <a-radio value="light">浅色</a-radio>
                <a-radio value="dark">深色</a-radio>
                <a-radio value="auto">跟随系统</a-radio>
              </a-radio-group>
            </a-form-item>
            <a-form-item>
              <a-space>
                <a-button type="primary" @click="handleSubmit">
                  保存设置
                </a-button>
                <a-button @click="handleCancel">
                  取消
                </a-button>
              </a-space>
            </a-form-item>
          </a-form>
        </a-tab-pane>
        <a-tab-pane key="notification" title="通知设置">
          <a-form
            ref="notificationFormRef"
            :model="notificationForm"
            @submit="handleNotificationSubmit"
          >
            <a-form-item field="emailNotification" label="邮件通知">
              <a-switch v-model="notificationForm.emailNotification" />
            </a-form-item>
            <a-form-item field="systemNotification" label="系统通知">
              <a-switch v-model="notificationForm.systemNotification" />
            </a-form-item>
            <a-form-item>
              <a-space>
                <a-button type="primary" @click="handleNotificationSubmit">
                  保存设置
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
import { ref, reactive } from 'vue';
import { Message } from '@arco-design/web-vue';
import { IconUpload } from '@arco-design/web-vue/es/icon';

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(['update:visible']);

const formRef = ref(null);
const notificationFormRef = ref(null);

const form = reactive({
  systemName: '',
  systemLogo: '',
  theme: 'light'
});

const notificationForm = reactive({
  emailNotification: true,
  systemNotification: true
});

const rules = {
  systemName: [{ required: true, message: '请输入系统名称' }]
};

const handleUpload = async (option) => {
  const { file, onSuccess, onError } = option;
  try {
    // 这里应该调用上传API
    // const formData = new FormData();
    // formData.append('file', file);
    // const response = await uploadFile(formData);
    // form.systemLogo = response.url;
    onSuccess();
    Message.success('上传成功');
  } catch (error) {
    onError();
    Message.error('上传失败');
  }
};

const handleSubmit = async () => {
  const { error } = await formRef.value.validate();
  if (error) return;

  try {
    // 这里应该调用保存设置的API
    // await saveSystemSettings(form);
    Message.success('设置保存成功');
    emit('update:visible', false);
  } catch (error) {
    Message.error('设置保存失败');
  }
};

const handleNotificationSubmit = async () => {
  try {
    // 这里应该调用保存通知设置的API
    // await saveNotificationSettings(notificationForm);
    Message.success('通知设置保存成功');
    emit('update:visible', false);
  } catch (error) {
    Message.error('通知设置保存失败');
  }
};

const handleCancel = () => {
  emit('update:visible', false);
};
</script>

<style scoped>
.system-settings {
  :deep(.arco-modal) {
    width: 500px;
  }
}

.upload-trigger {
  width: 120px;
  height: 120px;
  border: 1px dashed var(--color-border);
  border-radius: 4px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;

  &:hover {
    border-color: rgb(var(--primary-6));
  }
}

.preview-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.upload-placeholder {
  text-align: center;
  color: var(--color-text-3);

  .arco-icon {
    font-size: 24px;
    margin-bottom: 8px;
  }
}
</style> 