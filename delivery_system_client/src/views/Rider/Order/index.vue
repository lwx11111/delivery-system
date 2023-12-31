<template>
    <div>
        <el-row>
            <el-button v-if="data.isReceiveOrder" key="0" @click="receiveOrder()">开始接收订单</el-button>
            <el-button v-else @click="refuseOrder()" key="1">结束接收订单</el-button>
        </el-row>
        <el-divider></el-divider>
        <div v-for="(item,key) in data.orderList">
            <el-card style="margin-bottom: 10px">
                <el-row>{{item.shopName}}</el-row>
                <el-row>商家地址：{{item.shopLocation}}</el-row>
                <el-row>消费者地址：{{item.location}}</el-row>
                <el-row>{{item.remark}}</el-row>
                <el-row>{{item.deliveryCharge}}</el-row>
                <el-row>{{item.expectedTime}}</el-row>
                <el-button v-if="item.status === 3"
                           @click="orderDelivery(key)">已取货</el-button>
                <el-button v-if="item.status === 4"
                           @click="orderReceive(key)">已收货</el-button>
            </el-card>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import Api from '@/api/Order/api_orderinfo.js'
import ApiUser from '@/api/User/auth.js';
import {ElMessage, ElMessageBox} from "element-plus";

const store = useStore();
const router = useRouter()
// Data
const data = reactive({
    // 是否接受订单
    isReceiveOrder: false,
    // 订单信息
    orderList:[
        {
            id:1,
            shopName: '1',
            shopLocation:'',
            expectedTime:'2021-10-10 10:10:10',
            location:'',
            deliveryCharge:'',
            remark:'',
            status:''
        },
    ],
    // 骑手信息
    rider:{
        accountId: localStorage.getItem("userId"),
        status: 0
    }
})


// Mounted
onMounted(() => {
    listOrderByRiderId();
})

// Methods
const listOrderByRiderId = () => {
    Api.listOrderByRiderId(data.rider).then(res => {
        data.orderList = res.data;
    })
}
const orderDelivery = (key) => {
    const param = {
        orderId: data.orderList[key].id,
        test:"lwx"
    }
    console.log(param)
    Api.orderDelivery(param).then(res => {
        if (res.code === 200 && res.data === true){
            ElMessage({
                type: 'success',
                message: '派送成功',
            })
            listOrderByRiderId();
        } else {
            ElMessage({
                type: 'warning',
                message: '取消接单失败，请重试',
            })
        }
    })
}
const orderReceive = (key) => {
    const param = {
        orderId: data.orderList[key].id
    }
    Api.orderReceive(param).then(res => {
        if (res.code === 200 && res.data === true){
            ElMessage({
                type: 'success',
                message: '收货成功',
            })

        } else {
            ElMessage({
                type: 'warning',
                message: '取消接单失败，请重试',
            })
        }
    })
}
const receiveOrder = () => {
    data.rider.status = 0;
    ApiUser.update4rider(data.rider.accountId,data.rider).then(res => {
        if (res.code === 200){
            ElMessage({
                type: 'success',
                message: '接单成功',
            })
            data.isReceiveOrder = false;

        } else {
            ElMessage({
                type: 'warning',
                message: '接单失败，请重试',
            })
        }
    })
}

const refuseOrder = () => {
    data.rider.status = 1;
    ApiUser.update4rider(data.rider.accountId,data.rider).then(res => {
        if (res.code === 200){
            ElMessage({
                type: 'success',
                message: '停止接单成功',
            })
            data.isReceiveOrder = true;
            console.log(data.isReceiveOrder)
        } else {
            ElMessage({
                type: 'warning',
                message: '停止接单失败，请重试',
            })
        }
    })

}
</script>

<style scoped>

</style>
