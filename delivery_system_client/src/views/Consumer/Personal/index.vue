<template>
    <el-row style="margin-bottom: 10px">
        <h3 @click="toSetting()">设置</h3>
    </el-row>
    <!-- 头像名字-->
    <el-card @click="toUserInfo()" style="margin-bottom: 10px">
        <el-row>
            <el-col style="margin-right: 10px"
                    :span="4">
                <el-image :src="data.user.avatar"></el-image>
            </el-col>
            <el-col :span="10">
                <h1>{{data.user.name}}</h1>
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
            <el-col :span="6"
                    @click="toOrderIndex()">
                <el-icon><Search></Search></el-icon>
                <div>我的订单</div>
            </el-col>
<!--            <el-col :span="6">-->
<!--                <el-icon><Search></Search></el-icon>-->
<!--                <div>浏览记录</div>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--                <el-icon><Search></Search></el-icon>-->
<!--                <div>红包</div>-->
<!--            </el-col>-->
        </el-row>
    </el-card>
    <!--订单操作-->
<!--    <el-card>-->
<!--        <el-row>-->
<!--            <el-col :span="20">-->
<!--                <h1>订单</h1>-->
<!--            </el-col>-->
<!--            <el-col :span="4">-->
<!--                <h1 @click="toOrderIndex()">查看全部</h1>-->
<!--            </el-col>-->
<!--        </el-row>-->
<!--        <el-divider></el-divider>-->
<!--        <el-row>-->
<!--            <el-col :span="6"-->
<!--                    @click="toOrderIndex(1)">-->
<!--                <el-icon><Search></Search></el-icon>-->
<!--                <div>待付款</div>-->
<!--            </el-col>-->
<!--            <el-col :span="6"-->
<!--                    @click="toOrderIndex(2)">-->
<!--                <el-icon><Search></Search></el-icon>-->
<!--                <div>待收货</div>-->
<!--            </el-col>-->
<!--&lt;!&ndash;            <el-col :span="5"&ndash;&gt;-->
<!--&lt;!&ndash;                    @click="toOrderIndex(3)">&ndash;&gt;-->
<!--&lt;!&ndash;                <el-icon><Search></Search></el-icon>&ndash;&gt;-->
<!--&lt;!&ndash;                <div>待使用</div>&ndash;&gt;-->
<!--&lt;!&ndash;            </el-col>&ndash;&gt;-->
<!--            <el-col :span="6"-->
<!--                    @click="toOrderIndex(4)">-->
<!--                <el-icon><Search></Search></el-icon>-->
<!--                <div>待评价</div>-->
<!--            </el-col>-->
<!--            <el-col :span="6"-->
<!--                    @click="toOrderIndex(5)">-->
<!--                <el-icon><Search></Search></el-icon>-->
<!--                <div>退款/售后</div>-->
<!--            </el-col>-->
<!--        </el-row>-->
<!--    </el-card>-->
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";

import UserStorage from '@/cache/userStorage.js';
const store = useStore();
const router = useRouter()
import { Search } from '@element-plus/icons-vue'
// Data
const data = reactive({
    imageURL:'',
    //用户信息
    user: {
        id: UserStorage.getUserId(),
        name: UserStorage.getUserName(),
        avatar:''
    }

})

// Mounted
onMounted(() => {
    // data.imageURL = new URL(`@/assets/profile.png`, import.meta.url).href
    data.user.avatar = UserStorage.getUser().avatar
})

// Methods
const toSetting = () => {
    router.push({
        path: '/Consumer/Personal/setting',
    })
}

/**
 * 跳转用户信息页
 */
const toUserInfo = () => {
    router.push({
        path: '/Consumer/Personal/info',
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
