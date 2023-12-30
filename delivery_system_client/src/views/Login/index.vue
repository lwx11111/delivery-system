<template>
    <div class="back" style="border: 1px solid white">
        <el-card class="login-form-content">
            <el-form :model="form"
                     label-width="100px">
                <el-form-item label="用户名"
                              class="input-item flex align-center">
                    <el-input v-model="form.username" />
                </el-form-item>
                <el-form-item label="密码"
                              class="input-item flex align-center">
                    <el-input v-model="form.password"
                              show-password/>
                </el-form-item>
                <el-form-item label="验证码"
                              class="input-item flex align-center">
                    <el-input v-model="form.verifyCode"
                              style="width: 80%">
                    </el-input>
                    <img class="login-code"
                         alt="验证码"
                         id="captchaImg"
                         :src="data.captchaUrl"
                         @click="setCaptchUrl"/>
                </el-form-item>
                <el-form-item class="input-item flex align-center">
                    <el-button type="primary"
                               @click="onSubmit()">
                        登录
                    </el-button>
                    <el-button  @click="toRegister()">
                        注册
                    </el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script lang="ts" setup>
import {getCurrentInstance, onMounted, reactive} from 'vue'
import commonUtil from '../../utils/common-util.js';
import {getEncryptPassword} from '../../utils/passwordEncrypt.js';
import Api from '@/api/auth.js';
import ApiShop from '@/api/Shop/api_shop.js'
import ApiUser from '@/api/User/auth.js';
import { setToken } from '@/utils/auth/auth.js'
import { useStore } from "vuex";
import { useRouter} from "vue-router";
import {ElMessage} from "element-plus";

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
    // return 'http://localhost:9999/manager/v1/public/anon/verification-code/create?uuid=' + uuid;
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
            let account = res.data.info.account
            // store存储
            store.commit('setAccount',res.data.info.account);
            store.commit('setUser',res.data.info.user);
            store.commit('setSuperAdmin',res.data.info.superAdmin);
            store.commit('setToken',res.data.token);
            // 本地存储
            localStorage.setItem('userId', account.accountId)
            localStorage.setItem('userName', account.loginName)
            setToken(res.data.token.accessToken);
            // 根据类型跳转
            if (account.customAccountId === 'consumer'){
                router.push({
                    path: '/Consumer/index',
                })
            } else if(account.customAccountId === 'merchant') {
                // 是否商铺已注册
                let param = {
                    userId: account.accountId,
                }
                ApiShop.selpage4shop(param).then(res => {
                    console.log(res.data.records);
                    if (res.code === 200){
                        if (res.data.records.length === 0){
                            router.push({
                                path: '/Merchant/register',
                            })
                        } else {
                            router.push({
                                path: '/homepage',
                            })
                        }
                    }
                })
            } else if (account.customAccountId === 'rider') {
                ApiUser.riderRegister(account.accountId).then(res => {
                    console.log(res);
                    if (res.code === '20000'){
                        // 页面跳转
                        router.push({
                            path: '/Rider/Order/index',
                        })
                    } else {
                        ElMessage.error('登录失败');
                    }
                })
                router.push({
                    path: '/homepage',
                })
            }

        }
    });
}
const toRegister = () => {
    router.push({
        path: '/register',
    })
}
</script>

<style scoped>
.back {
    background-image: url('/src/assets/backgroud.jpg');
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center center; /* 将背景图像置于容器中央 */
    width: 100%;
    height: 700px;
}

.login-form-content {
    text-align: center;
    margin-top: 10%;
    margin-left: 25%;
    width: 50%;
    height: 40%;
    background-color: #f5f6f7;
    .input-item {
        margin: 20px auto;
        height: 45px;
        border-radius: 20px;
    }

    .login-code {
        height: 38px;
        float: right;
    }
}
</style>
