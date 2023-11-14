<template>
    <div class="common-layout">
        <el-container>
            <el-header style="border: 1px solid red">Header</el-header>
            <el-container>
                <el-aside width="200px"></el-aside>
                <el-container>
                    <el-main class="main" style="background: #FFFAFA">
                        <!--商铺和菜品信息-->
                        <el-card>
                            <el-row>
                                <el-col :span="16">
                                    {{data.shop.name}}
                                </el-col>
                                <el-col :span="16">
                                    <el-icon><ArrowRight></ArrowRight></el-icon>
                                </el-col>
                                <el-col :span="16">
                                    <el-icon><ArrowRight></ArrowRight></el-icon>
                                </el-col>
                            </el-row>
                            <el-row v-for="item in data.orderItemList">
                                <el-col :span="4">
                                    <el-image src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"></el-image>
                                </el-col>
                                <el-col :span="20">
                                    {{item.name}}
                                </el-col>
                            </el-row>
                        </el-card>
                        <!--原因选择-->
                        <el-card>
                            <el-row>
                                请选择原因
                            </el-row>
                            <el-row>
                                <el-radio-group v-model="data.reason">
                                    <el-row> <el-radio label="1" size="large">商家缺货</el-radio></el-row>
                                    <el-row> <el-radio label="2" size="large">商家少送物品</el-radio></el-row>
                                    <el-row> <el-radio label="3" size="large">商家送错物品</el-radio></el-row>
                                    <el-row> <el-radio label="4" size="large">口味不佳</el-radio></el-row>
                                    <el-row> <el-radio label="5" size="large">餐品与描述不符</el-radio></el-row>
                                    <el-row> <el-radio label="6" size="large">送太慢了</el-radio></el-row>
                                    <el-row> <el-radio label="7" size="large">餐品洒漏</el-radio></el-row>
                                    <el-row> <el-radio label="8" size="large">餐品有异物</el-radio></el-row>
                                    <el-row> <el-radio label="9" size="large">变质</el-radio></el-row>
                                </el-radio-group>
                            </el-row>
                        </el-card>
                        <el-button @click="submit">申请售后</el-button>
                    </el-main>
                </el-container>
                <el-aside width="200px"></el-aside>
            </el-container>
        </el-container>
        <el-footer style="border: 1px solid red">Footer</el-footer>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter, useRoute } from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";

import ApiOrder from '../../../api/api_order.js'
import ApiShop from '../../../api/api_shop.js'
import ApiShopItem from '../../../api/api_shop_item.js'

import { ArrowRight, ChatDotRound } from '@element-plus/icons-vue'
const store = useStore();
const router = useRouter();
const route = useRoute();

// Data
const data = reactive({
    // 店铺信息
    shop: {
        id: '',
        userId : '',
        name : 'name',
        province : '',
        county : '',
        location : '',
        score : '',
        salesVolume : '',
        minPrice : '',
        deliveryCharge : '',
        safetyFile : '',
        openTime : '',
        closeTime : '',
        status : '',
        description : '',
        itemCategory : '',
        picture : ''
    },
    // 订单信息
    order: {
        id: '',
        itemInfo:[],
        packingCharges: '111',
        delivery_charge: '1',
        expected_time: '11',
        total_charge: '11',
        location: '',
        delivery_service: '',
        delivery_rider_id: '',
        order_time: '',
    },
    // 订单菜品
    orderItemList: [
        {
            name: '11',
            price: '11'
        }
    ],
    // 售后原因
    reason: '',
})
// 解构抛出 直接使用
// const { type} = toRefs(data)

// Mounted
onMounted(() => {
    data.order.id = route.query.orderId;
    data.shop.id = route.query.shopId;
    getOrder();
    getShop()
})

// Methods
const getOrder = () => {
    ApiOrder.sel4order(data.order.id).then(res => {
        console.log(res)
        if (res.code === 200){
            data.order = res.data;
            data.shop.id = res.data.shopId;

        } else {

        }
    })
}

const getShop = () => {
    ApiShop.sel4shop(data.shop.id).then(res => {
        console.log(res)
        if (res.code === 200){
            data.shop = res.data;
        } else {

        }
    })
}

const submit = () => {
    const params = {
        orderId: data.order.id,
        shopId: data.shop.id,
        reason: data.reason
    }
    ApiOrder.afterSale(params).then(res => {
        console.log(res)
        if (res.code === 200){
            ElMessage({
                message: '修改成功',
                type: 'success'
            })
        } else {
            ElMessage({
                message: '修改失败',
                type: 'error'
            })
        }
    })
}


</script>

<style scoped>

</style>
