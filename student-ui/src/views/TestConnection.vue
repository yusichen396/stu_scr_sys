<template>
  <div class="test-connection">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>前后端连接测试</span>
        </div>
      </template>
      <div class="test-content">
        <el-button type="primary" @click="testConnection">测试连接</el-button>
        <div v-if="result" class="result">
          <pre>{{ JSON.stringify(result, null, 2) }}</pre>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'TestConnection',
  data() {
    return {
      result: null
    }
  },
  methods: {
    async testConnection() {
      try {
        const response = await axios.get('/api/test/connection')
        this.result = response.data
      } catch (error) {
        this.result = {
          status: 'error',
          message: error.message,
          timestamp: new Date().getTime()
        }
      }
    }
  }
}
</script>

<style scoped>
.test-connection {
  padding: 20px;
}
.box-card {
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.test-content {
  text-align: center;
}
.result {
  margin-top: 20px;
  text-align: left;
  background-color: #f5f7fa;
  padding: 15px;
  border-radius: 4px;
}
pre {
  margin: 0;
  white-space: pre-wrap;
  word-wrap: break-word;
}
</style> 