import {
  createRouter,
  createWebHistory,
  createWebHashHistory,
} from 'vue-router'

import Login from '@/login/index.vue';
import Register from '@/views/Register/index.vue'
// 管理端
import managementRouter from "@/router/Management/index";
// 消费者端
import consumerRouter from "@/router/Consumer/index";
// 商家端
import merchantRouter from "@/router/Merchant/index";
import riderRouter from "@/router/Rider";
// 模块化路由
const routes = [
    {
        path: '/login',
        component: Login,
        name: 'Login'
    },
    {
        path: '/Register/index',
        component: Register,
        name: 'Register'
    },
    managementRouter,
    consumerRouter,
    merchantRouter,
    riderRouter
]

// 创建路由对象
const router = createRouter({
  // history: createWebHashHistory(), // hash模式：createWebHashHistory，有#
  history: createWebHistory(),  // history模式：createWebHistory
  routes
})

export default router

