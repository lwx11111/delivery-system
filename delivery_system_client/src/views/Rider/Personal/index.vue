<template>
    <el-row style="margin-bottom: 10px">
        <h3 @click="toSetting()">设置</h3>
    </el-row>
    <!-- 头像名字-->
    <el-card @click="toUserDetail" style="margin-bottom: 10px">
        <el-row>
            <el-col style="margin-right: 10px"
                    :span="4">
                <el-image :src="data.imageURL"></el-image>
            </el-col>
            <el-col :span="10">
                <h1>{{data.user.name}}</h1>
            </el-col>
        </el-row>
    </el-card>

    <el-card @click="toUserDetail" style="margin-bottom: 10px">
        <el-row>
            <el-col :span="12">
                <h1>收益：</h1>
            </el-col>
            <el-col :span="12">
                <h1 style="color: red">{{ data.earningsData.deliveryChargeTotal }} ￥</h1>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <h1>完成订单数：</h1>
            </el-col>
            <el-col :span="12">
                <h1>{{data.earningsData.num}}</h1>
            </el-col>
        </el-row>
    </el-card>
</template>

<script lang="ts" setup>
import ApiOrder from '@/api/Order/api_orderinfo.js'
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import UserStorage from '@/cache/userStorage.js';
const store = useStore();
const router = useRouter()

// Data
const data = reactive({
    imageURL:'',
    //用户信息
    user: {
        id: UserStorage.getUserId(),
        name: UserStorage.getUserName(),
    },
    riderOrder:{
        id:'',
        num:'',
        earnings:''
    },
    // 收益数据
    earningsData: {
        total:'',
        num:'',
        deliveryChargeTotal: ''
    }

})

// Mounted
onMounted(() => {
    let user = UserStorage.getUser();
    data.user.id = user.accountId;
    data.user.name = user.accountName;
    data.imageURL = new URL(`@/assets/profile.png`, import.meta.url).href;
    getEarningsData();
})

// Methods

/**
 * 查询本周收益
 */
const getEarningsData = () => {
    const params = {
        riderId: UserStorage.getUserId(),
    }

    ApiOrder.getEarningsData(params).then(res => {
        console.log(res)
        if (res.code === 200){
            data.earningsData = res.data;
        }
    })

}

const toSetting = () => {
    router.push({
        path: '/Rider/Personal/setting',
    })
}
const toUserDetail = () => {
    router.push({
        path: '/Rider/Personal/userDetail',
        query: {
            userId: data.user.id
        }
    })
}
</script>

<style scoped>

</style>
