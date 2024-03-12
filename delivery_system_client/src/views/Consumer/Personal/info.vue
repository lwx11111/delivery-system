<template>
    <el-card>
        <el-form>
            <el-form-item label="用户名">
                <el-input v-model="data.user.name"></el-input>
            </el-form-item>
            <el-form-item label="密码">
                <el-input v-model="data.user.name"></el-input>
            </el-form-item>
            <el-form-item label="手机号">
                <el-input v-model="data.user.name"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input v-model="data.user.name"></el-input>
            </el-form-item>
            <el-form-item label="性别">
                <el-input v-model="data.user.name"></el-input>
            </el-form-item>
            <el-form-item label="生日">
                <el-input v-model="data.user.name"></el-input>
            </el-form-item>
            <el-form-item label="地址">
                <el-input v-model="data.user.name"></el-input>
            </el-form-item>
            <el-form-item label="头像">
                <el-input v-model="data.user.name"></el-input>
            </el-form-item>
            <el-form-item label="个性签名">
                <el-input v-model="data.user.name"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary">修改</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";
import ApiOrder from '../../../api/api_order.js'

const store = useStore();
const router = useRouter()
import { Search } from '@element-plus/icons-vue'
// Data
const data = reactive({
    //用户信息
    user: {
        name: 'lwx'
    }

})

// Mounted
onMounted(() => {

})

// Methods



</script>

<style scoped>

</style>
