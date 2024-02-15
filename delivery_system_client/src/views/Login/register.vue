<template>
    <div class="back" style="border: 1px solid white">
        <el-card class="login-form-content">
            <h1 style="text-align: center">注册</h1>
            <el-form :model="data.form"
                     ref="formRef"
                     :rules="data.rules"
                     label-width="100px">
                <el-form-item label="用户名"
                              prop="username"
                              class="input-item flex align-center">
                    <el-input v-model="data.form.username"></el-input>
                </el-form-item>
                <el-form-item label="密码"
                              prop="password"
                              class="input-item flex align-center">
                    <el-input type="password" v-model="data.form.password"
                              show-password></el-input>
                </el-form-item>
                <el-form-item label="确认密码"
                              prop="confirmPassword"
                              class="input-item flex align-center">
                    <el-input type="password" v-model="data.form.confirmPassword"
                              show-password></el-input>
                </el-form-item>
                <el-form-item label="手机号"
                              prop="phone"
                              class="input-item flex align-center">
                    <el-input type="phone" v-model="data.form.phone"></el-input>
                </el-form-item>
                <el-form-item label="验证码"
                              prop="verifyCode"
                              class="input-item flex align-center">
                    <el-input v-model="data.form.verifyCode"
                              style="width: 80%">
                    </el-input>
                    <img
                        alt="验证码"
                        id="captchaImg"
                        :src="data.captchaUrl"
                        class="verifyImg"
                        @click="setCaptchUrl"/>
                </el-form-item>
                <el-form-item label="用户类型"
                              prop="type">
                    <el-select v-model="data.form.type" placeholder="请选择">
                        <el-option v-for="(item, index) in data.types"
                                   :label="item.name"
                                   :value="item.id" />
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm()">注册</el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script lang="ts" setup>
import commonUtil from '@/utils/common-util.js';
import {reactive, onMounted, ref, getCurrentInstance} from 'vue';
import ApiUser from '@/api/User/auth.js';
import {getEncryptPassword} from '@/utils/passwordEncrypt.js';
import { useStore } from "vuex";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
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
        phone: [
            {required: true, message: '电话号不能为空', trigger: 'blur'}
        ],
        verifyCode: [
            {required: true, message: '验证码不能为空', trigger: 'blur'}
        ],
        type: [
            {required: true, message: '用户类型不能为空', trigger: 'blur'}
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
const validatePassword = () => {
    if (data.form.password !== data.form.confirmPassword) {
        ElMessage.error('注册失败');
        return false;
    }
    return true;
}

const validatePhone = () => {
    if (data.form.phone.length === 11){
        return true;
    }
    ElMessage.error('电话失败');
    return false;
}

// 提交表单的方法
const formRef = ref();
const submitForm = () => {
    // 表单验证
    formRef.value.validate(valid => {
        if (valid) {
            if (validatePassword() && validatePhone()){
                const data1 = {
                    verify: data.form.verifyCode,
                    uuid: data.uuid,
                    username: data.form.username,
                    password: getEncryptPassword(data.form.password, 'md5'),
                    appId: data.form.appId,
                    type: data.form.type
                }
                ApiUser.register(data1).then(res => {
                    console.log(res);
                    if (res.code === '20000'){
                        ElMessage.success('注册成功');
                        // 页面跳转
                        router.push({
                            path: '/login',
                        })
                    } else {
                        ElMessage.error('注册失败');
                    }
                })
            }
        } else {
            // 表单验证不通过，不执行任何操作
            alert('表单验证不通过');
        }
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
    margin-top: 5%;
    margin-left: 25%;
    width: 50%;
    height: 80%;
    background-color: #f5f6f7;
    .input-item {
        margin: 20px auto;
        height: 45px;
        border-radius: 20px;
    }
}
</style>
