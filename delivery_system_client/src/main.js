import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from "./router/index";
import axios from 'axios'
import store from './store/index'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// permission control
import './permission'
import './utils/stringUtil'
// Cache
import AddressStorage from "@/cache/addressStorage.js";
// 全局变量
import dataJson from "../public/config.json";
import * as echarts from 'echarts';
import VueNativeSock from "vue-native-websocket-vue3";
// 使用VueNativeSock插件，并进行相关配置
import websocket from 'vue-native-websocket'

const app = createApp(App);
app.config.globalProperties.$axios = axios;
app.config.globalProperties.$echarts = echarts;
// use(VueNativeSock,'ws://127.0.0.1:8000/ws/token',{
//     // 这里要填的是服务器的地址，可以换一个在线服务器wss://echo.websocket.org
//     reconnection: true, // (Boolean)是否自动重连，默认false
//     reconnectionAttempts: 5, // 重连次数
//     reconnectionDelay: 3000, // 再次重连等待时常(1000)
// }).
app.use(ElementPlus).use(router).use(store).mount('#app');

// 全局注册element-plus的图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

// 全局变量配置
app.config.globalProperties = {
    APP_NAME: dataJson.APP_NAME,
    GATEWAY_URL: dataJson.GATEWAY_URL,
    AUTH_NAME: dataJson.AUTH_NAME,
    CAPTCHA_URL: dataJson.CAPTCHA_URL,
    APP_ID: dataJson.APP_ID,
};

// 全局JS文件
app.provide('$addressStorage', AddressStorage)

