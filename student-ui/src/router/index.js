import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import Student from '../views/Student.vue'
import Course from '../views/Course.vue'
import Grade from '../views/Grade.vue'
import TestConnection from '../views/TestConnection.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: { requiresAuth: false }
  },
  {
    path: '/',
    name: 'Home',
    component: Home,
    meta: { requiresAuth: true }
  },
  {
    path: '/student',
    name: 'Student',
    component: Student,
    meta: { requiresAuth: true }
  },
  {
    path: '/course',
    name: 'Course',
    component: Course,
    meta: { requiresAuth: true }
  },
  {
    path: '/grade',
    name: 'Grade',
    component: Grade,
    meta: { requiresAuth: true }
  },
  {
    path: '/test-connection',
    name: 'TestConnection',
    component: TestConnection,
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 全局路由守卫
router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem('token')
  
  // 如果需要认证且未登录
  if (to.meta.requiresAuth && !isAuthenticated) {
    next('/login')
  }
  // 如果已登录且访问登录页
  else if (isAuthenticated && to.path === '/login') {
    next('/')
  }
  // 其他情况正常放行
  else {
    next()
  }
})

export default router 