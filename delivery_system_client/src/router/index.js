import {
  createRouter,
  createWebHistory,
  createWebHashHistory,
} from 'vue-router'

import Login from '@/views/Login/index.vue';
import Register from '@/views/Login/register.vue'
// 管理端
import managementRouter from "@/router/Management/index";
// 消费者端
import consumerRouter from "@/router/Consumer/index";
// 商家端
import merchantRouter from "@/router/Merchant/index";
import riderRouter from "@/router/Rider";
// 模块化路由
const routes = [
    // 路由守卫 vue3动态路由问题导致刷新完页面会爆出No match found for location with path
    {
        path: "/:pathMatch(.*)*", // 必备
        component: () => import("@/views/404.vue"),
    },
    {
        path: '/login',
        component: Login,
        name: 'Login'
    },
    {
        path: '/register',
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

