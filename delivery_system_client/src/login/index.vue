<template>
    <el-form :model="form" label-width="120px">
        <el-form-item label="username">
            <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="password">
            <el-input v-model="form.password" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">Create</el-button>
            <el-button>Cancel</el-button>
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
    </el-form>
</template>

<script lang="ts" setup>
import { reactive,getCurrentInstance } from 'vue'
import commonUtil from '../utils/common-util.js';
import { getEncryptPassword } from '../utils/passwordEncrypt.js';
import Api from '../api/test.js';
import qs from "qs";

const data = reactive({
    captchaUrl: '',
    uuid: ''
})
const form = reactive({
    username: '',
    password: '',
    verifyCode: '',
})
const currentInstance = getCurrentInstance();
const getCaptchaUrl = () => {
    const uuid = commonUtil.createGuid()
    data.uuid = uuid;
    console.log(data.uuid)
    // this.guid = commonUtil.createGuid()

    // 使用getCurrentInstanceAPI获取全局对象方法一
    // 从globalProperties中可以获取到所有的全局变量
    const globalProperties = currentInstance?.appContext.config.globalProperties
    console.log(globalProperties);

    let url = '';
    if (globalProperties.PERMISSIONSYSTEM_ENABLE) {
        // url = globalProperties.BASE_URL + globalProperties.PERMISSIONSYSTEM.URL_CAPTCHA + '?uuid=' + uuid
        // console.log(url);
    } else {
        // return require('../../assets/Login/code.jpg')
    }
    url = 'http://localhost:8921/auth-external-microservice-lwx/v1/public/anon/verification-code/create?uuid='+ uuid;
    return url;
}

const setCaptchUrl = () => {
    console.log(data);

    data.captchaUrl = getCaptchaUrl()
    form.verifyCode = ''
}



const onSubmit = () => {
    var data1 = {
        verify: form.verifyCode,
        uuid: data.uuid,
        username: getEncryptPassword(form.username, 'aes'),
        password: getEncryptPassword(form.password, 'aes'),
        appId: '1',
        appName: 'dw'
    }

    Api.loginWithCode(data1).then(res => {
        console.log(res);
    });

}


</script>

<style scoped>

</style>
