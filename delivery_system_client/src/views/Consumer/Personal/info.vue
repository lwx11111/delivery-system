<template>
    <el-card>
        <el-form>
            <el-form-item label="手机号">
                <el-input v-model="data.user.phone"></el-input>
            </el-form-item>
            <el-form-item label="头像" >
                <MinioUpload v-if="data.user.avatar" :url="data.user.avatar" @getUrl="getUrl"></MinioUpload>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="updateInfo()">修改</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import UserStorage from '@/cache/userStorage'
import AuthStorage from '@/cache/authStorage'
import MinioUpload from "../../components/MinioUpload.vue";
import ApiUser from '@/api/User/auth.js'
import {ElMessage} from "element-plus";
const store = useStore();
const router = useRouter()

// Data
const data = reactive({
    //用户信息
    user: {
        avatar: '',
        phone: ''
    }

})

// Mounted
onMounted(() => {
    var user = UserStorage.getUser();
    console.log(user)
    data.user = user
})

// Methods

const getUrl = (url) =>{
    console.log(url)
    data.user.avatar = url;
}

const updateInfo = () => {
    if (data.user.phone.length !== 11){
        ElMessage.info({
            message:"手机号不通过"
        })
        return;
    }
    console.log(data.user.avatar)
    if (data.user.avatar === null){
        ElMessage.info({
            message:"头像不通过"
        })
        return;
    }
    ApiUser.updateInfo(data.user).then(res => {
        console.log(res)
        if (res.code === '20000'){
            ElMessage.info({
                message:"修改成功",
                type:"success"
            })
            UserStorage.removeUser();
            AuthStorage.removeToken();
            router.go(0)
        }
    })
}
</script>

<style scoped>

</style>
