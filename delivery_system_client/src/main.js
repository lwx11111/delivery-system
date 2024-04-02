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


const app = createApp(App);
app.config.globalProperties.$axios = axios;
app.config.globalProperties.$echarts = echarts;
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

