<template>
    <el-form :model="data.form" ref="formRef" :rules="data.rules" label-width="80px">
        <el-form-item label="用户名" prop="username">
            <el-input v-model="data.form.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="data.form.password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
            <el-input type="password" v-model="data.form.confirmPassword"></el-input>
        </el-form-item>
        <el-form-item label="手机号" >
            <el-input type="phone" v-model="data.form.phone"></el-input>
        </el-form-item>
        <el-form-item prop="verifyCode">
            <el-input v-model="data.form.verifyCode" />
            <img
                alt="验证码"
                id="captchaImg"
                :src="data.captchaUrl"
                class="verifyImg"
                @click="setCaptchUrl"/>
        </el-form-item>
        <el-form-item prop="type">
            <el-select v-model="data.form.type" placeholder="请选择">
                <el-option v-for="(item, index) in data.types"
                           :label="item.name"
                           :value="item.id" />
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="submitForm">注册</el-button>
        </el-form-item>
    </el-form>
</template>

<script lang="ts" setup>
import commonUtil from '@/utils/common-util.js';
import {reactive, onMounted, ref, getCurrentInstance} from 'vue';
import ApiUser from '@/api/User/auth.js';
import {getEncryptPassword} from '@/utils/passwordEncrypt.js';
import { useStore } from "vuex";
import {useRouter} from "vue-router";
const store = useStore();
const router = useRouter()

// Data
const data = reactive({
    captchaUrl: '',
    uuid: '',
    types:[
        {
            id:"consumer",
            name:"消费者"
        },
        {
            id:"merchant",
            name:"商家"
        },
        {
            id:"rider",
            name:"骑手"
        },
    ],
    form : {
        username: '',
        password: '',
        confirmPassword: '',
        phone: '',
        verifyCode: '',
        appId: '1',
        type:''
    },
    rules: {
        username: [
            {required: true, message: '用户名不能为空', trigger: 'blur'}
        ],
        password: [
            {required: true, message: '密码不能为空', trigger: 'blur'}
        ],
        confirmPassword: [
            {required: true, message: '确认密码不能为空', trigger: 'blur'}
        ],
        verifyCode: [
            {required: true, message: '验证码不能为空', trigger: 'blur'}
        ],
    }
})


// Mounted
onMounted(() => {
    setCaptchUrl();
})

/**
 * 获取验证码
 */
const currentInstance = getCurrentInstance();
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
    data.form.verifyCode = ''
}

// 验证密码是否一致的方法
function validatePassword(rule, value, callback) {
    if (value !== data.form.value.password) {
        callback(new Error('两次输入的密码不一致'));
    } else {
        callback();
    }
}

// 提交表单的方法
const formRef = ref();
function submitForm() {
    // 表单验证
    formRef.value.validate((valid) => {
        if (valid) {
            const data1 = {
                verify: data.form.verifyCode,
                uuid: data.uuid,
                username: data.form.username,
                password: getEncryptPassword(data.form.password, 'aes'),
                appId: data.form.appId,
                type: data.form.type
            }
            ApiUser.register(data1).then(res => {
                console.log(res);
            })
            // 页面跳转
            router.push({
                path: '/login',
            })

        } else {
            // 表单验证不通过，不执行任何操作
            alert('表单验证不通过');
        }
    })

}
</script>

<style scoped>
/* 添加一些基本的样式 */
.el-form-item__label {
    font-weight: bold;
}
.el-input {
    width: 300px;
    margin-bottom: 15px;
}
.el-button {
    margin-top: 10px;
}
</style>
