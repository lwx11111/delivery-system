import {
  createRouter,
  createWebHistory,
  createWebHashHistory,
} from 'vue-router'

import Login from '@/login/index.vue';
// 管理端
import managementRouter from "@/router/Management/index";
// 消费者端
import consumerRouter from "@/router/Consumer/index";

// 模块化路由
const routes = [
  {
    path: '/login',
    component: Login,
    name: 'Login'
  },
  managementRouter,
  consumerRouter,
]

// 创建路由对象
const router = createRouter({
  // history: createWebHashHistory(), // hash模式：createWebHashHistory，有#
  history: createWebHistory(),  // history模式：createWebHistory
  routes
})

export default router

