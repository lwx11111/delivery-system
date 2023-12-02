<template>
    <el-row>设置 客服</el-row>
    <!-- 头像名字-->
    <el-card @click="toUserDetail" style="margin-bottom: 10px">
        <el-row>
            <el-col :span="4">
                <el-image src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"></el-image>
            </el-col>
            <el-col :span="20">
                {{data.user.name}}
            </el-col>
        </el-row>
    </el-card>
    <!--个人信息-->
    <el-card style="margin-bottom: 10px">
        <el-row>
            <el-col :span="6"
                    @click="toCart">
                <el-icon><Search></Search></el-icon>
                <div>购物车</div>
            </el-col>
            <el-col :span="6"
                    @click="toCollection">
                <el-icon><Search></Search></el-icon>
                <div>我的收藏</div>
            </el-col>
            <el-col :span="6">
                <el-icon><Search></Search></el-icon>
                <div>浏览记录</div>
            </el-col>
            <el-col :span="6">
                <el-icon><Search></Search></el-icon>
                <div>红包</div>
            </el-col>
        </el-row>
    </el-card>
    <!--订单操作-->
    <el-card>
        <el-row>
            <h1>订单</h1>
            查看全部
        </el-row>
        <el-row>
            <el-col :span="4"
                    @click="toOrderIndex(1)">
                <el-icon><Search></Search></el-icon>
                <div>待付款</div>
            </el-col>
            <el-col :span="4"
                    @click="toOrderIndex(2)">
                <el-icon><Search></Search></el-icon>
                <div>待收货</div>
            </el-col>
            <el-col :span="4"
                    @click="toOrderIndex(3)">
                <el-icon><Search></Search></el-icon>
                <div>待使用</div>
            </el-col>
            <el-col :span="4"
                    @click="toOrderIndex(4)">
                <el-icon><Search></Search></el-icon>
                <div>待评价</div>
            </el-col>
            <el-col :span="4"
                    @click="toOrderIndex(5)">
                <el-icon><Search></Search></el-icon>
                <div>退款/售后</div>
            </el-col>
        </el-row>
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
        id: '1',
        name: 'lwx'
    }

})

// Mounted
onMounted(() => {

})

// Methods

const toUserDetail = () => {
    router.push({
        path: '/Consumer/Personal/userDetail',
        query: {
            userId: data.user.id
        }
    })
}

const toCart = () => {
    router.push({
        path: '/Consumer/Personal/shoppingCart',
        // query: {
        //     userId: data.user.id
        // }
    })
}

const toCollection = () => {
    router.push({
        path: '/Consumer/Personal/collection',
        // query: {
        //     userId: data.user.id
        // }
    })
}

const toOrderIndex = (type) => {
    router.push({
        path: '/Consumer/Order/index',
        query: {
            userId: data.user.id,
            type: type
        }
    })
}

</script>

<style scoped>

</style>
