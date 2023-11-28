import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from "./router/index";
import axios from 'axios'
import store from './store/index'
import dataJson from "../public/config.json";
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import './permission' // permission control

const app = createApp(App);
app.config.globalProperties.$axios = axios;
app.use(ElementPlus).use(router).use(store).mount('#app');
// 全局注册element-plus的图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.config.globalProperties = {
    BASE_URL: dataJson.BASE_URL,
    PERMISSIONSYSTEM_ENABLE: dataJson.PERMISSIONSYSTEM_ENABLE,
    PERMISSIONSYSTEM : dataJson.PERMISSIONSYSTEM,
    APP_NAME: dataJson.APP_NAME,
    SHOP_SERVER: dataJson.SHOP_SERVER,
};

// 引入外部配置 public/config.json
// function getServerConfig () {
//     return new Promise ((resolve, reject) => {
//         axios.get('./public/config.json').then(data => {
//             // 挂载全局
//             app.config.globalProperties.$baseURL = data.data.baseURL;
//
//             resolve();
//         }).catch(error => {
//             console.log(error);
//             reject()
//         })
//     })
// }
//
// async function init() {
//     await getServerConfig();
// }
//
// app.use(init) // this.$baseURL 使用
