/**
 * ajax请求配置
 */
import axios from 'axios'
import { ElMessage } from 'element-plus'
import qs from "qs";

// axios默认配置
axios.defaults.timeout = 10000 // 超时时间

// 网关地址
axios.defaults.baseURL = 'http://localhost:9999'

// 整理数据
axios.defaults.transformRequest = function(data) {

    // data = JSON.stringify(data)
    return data
}

// 路由请求拦截
axios.interceptors.request.use(
    config => {
        if (config.type === 'form'){
            // 后端@RequestParams注解接收
            config.headers['Content-Type'] = 'application/x-www-form-urlencoded'
            config.data = qs.stringify(config.data)
        } else if (config.type === 'file') {
            // 后端@RequestParams注解接收
            config.headers['Content-Type'] = 'multipart/form-data'
            config.data = qs.stringify(config.data)
        } else {
            // 后端@RequestBody注解接收
            config.headers['Content-Type'] = 'application/json;charset=UTF-8'
            config.data = JSON.stringify(config.data)
        }
        return config
    },
    error => {
        return Promise.reject(error.response)
    })

// 路由响应拦截
axios.interceptors.response.use(
    response => {
        if (response.data.success === false) {
            return ElMessage.error(response.data.errDesc)
        } else {
            return response.data
        }
    },
    error => {
        return Promise.reject(error.response) // 返回接口返回的错误信息
    })
export default axios
