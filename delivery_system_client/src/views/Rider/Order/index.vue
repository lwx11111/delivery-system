<template>
    <div>
        <el-row>
            <el-button v-if="data.isReceiveOrder" @click="receiveOrder">开始接收订单</el-button>
            <el-button v-else @click="refuseOrder">结束接收订单</el-button>
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
                           @click="orderDeliver(key)">已取货</el-button>
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
        id:'1'
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
const orderDeliver = (key) => {
    const param = {
        orderId: data.orderList[key].id
    }
    console.log(param)
    Api.orderDeliver(param).then(res => {
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
            listOrderByRiderId();
        } else {
            ElMessage({
                type: 'warning',
                message: '取消接单失败，请重试',
            })
        }
    })
}
const receiveOrder = () => {

}

const refuseOrder = () => {

}
</script>

<style scoped>

</style>
