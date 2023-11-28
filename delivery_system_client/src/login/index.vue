<template>
    <el-form :model="form" label-width="120px">
        <el-form-item label="username">
            <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="password">
            <el-input v-model="form.password" />
        </el-form-item>
        <el-form-item>
            <el-input v-model="form.verifyCode" />
            <img
                alt="验证码"
                id="captchaImg"
                :src="data.captchaUrl"
                class="verifyImg"
                @click="setCaptchUrl"/>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">登录</el-button>
            <el-button>重置</el-button>
        </el-form-item>
    </el-form>
</template>

<script lang="ts" setup>
import {getCurrentInstance, onMounted, reactive} from 'vue'
import commonUtil from '../utils/common-util.js';
import {getEncryptPassword} from '../utils/passwordEncrypt.js';
import Api from '@/api/auth.js';
import { setToken } from '@/utils/auth/auth.js'
import { useStore } from "vuex";
import {useRouter} from "vue-router";
const store = useStore();
const router = useRouter()
const data = reactive({
    captchaUrl: '',
    uuid: ''
})
const form = reactive({
    username: '',
    password: '',
    verifyCode: '',
})

// Mounted
onMounted(() => {
    setCaptchUrl();
})

const currentInstance = getCurrentInstance();

/**
 * 获取验证码
 */
const getCaptchaUrl = () => {
    const uuid = commonUtil.createGuid()
    data.uuid = uuid;
    // 使用getCurrentInstanceAPI获取全局对象方法一
    // 从globalProperties中可以获取到所有的全局变量
    const globalProperties = currentInstance?.appContext.config.globalProperties
    return 'http://localhost:8921/auth-external-microservice-lwx/v1/public/anon/verification-code/create?uuid=' + uuid;
}

/**
 * 重新获取验证码
 */
const setCaptchUrl = () => {
    data.captchaUrl = getCaptchaUrl()
    form.verifyCode = ''
}

/**
 * 登录
 */
const onSubmit = () => {
    var params = {
        verify: form.verifyCode,
        uuid: data.uuid,
        username: getEncryptPassword(form.username, 'aes'),
        password: getEncryptPassword(form.password, 'aes'),
        appId: '1',
        appName: 'dw'
    }

    Api.loginWithCode(params).then(res => {
        console.log(res);
        if (res.code === "20000"){
            // store存储
            store.commit('setAccount',res.data.info.account);
            store.commit('setUser',res.data.info.user);
            store.commit('setSuperAdmin',res.data.info.superAdmin);
            store.commit('setToken',res.data.token);

            setToken(res.data.token.accessToken);
            // sessionStorage.setItem("Auth-Token",res.data.token.accessToken)
            // 根据类型跳转
            router.push({
                path: '/homePage',
            })
        }
    });



}

</script>

<style scoped>

</style>
