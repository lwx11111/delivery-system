<template>
    <div style="height: 50%">
        <el-row>
            选择支付方式
        </el-row>
        <el-card style="margin-top: 10px">
            <el-radio-group v-model="data.paymentMethod">
                <el-radio value="wx" size="large" border label="微信支付"></el-radio>
                <el-radio value="zfb" size="large" border label="支付宝支付"></el-radio>
            </el-radio-group>
        </el-card>

        <el-button @click="orderPay()"
                   style="background: gold; margin-top: 500px;width: 100%; height: 50px">
            支付
        </el-button>
    </div>

</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter, useRoute } from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";
import Api from '@/api/Order/api_orderinfo.js'
const store = useStore();
const router = useRouter();
const route = useRoute();

// Data
const data = reactive({
    orderId:'',
    paymentMethod: '',
})

// Mounted
onMounted(() => {
    data.orderId = route.query.orderId;
})

// Methods
/**
 * 支付订单
 */
const orderPay = () => {
    if (data.paymentMethod === ''){
        ElMessage.error('请选择支付方式')
        return
    }

    const param = {
        orderId: data.orderId,
        paymentMethod: data.paymentMethod
    }
    console.log(param)
    Api.orderPay(param).then(res => {
        console.log(res)
        if (res.code === 200){
            if (res.data === true){
                ElMessage.success('支付成功')
                router.push({
                    path: '/Consumer/Order/index',
                })
            } else {
                ElMessage.error('支付失败，请重试')
            }
        } else {
            ElMessage.error(res.message)
        }
    })
}

</script>

<style scoped>

</style>
