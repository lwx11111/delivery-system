<template>
    <div class="common-layout">
        <el-container>
            <el-header style="border: 1px solid red">Header</el-header>
            <el-container>
                <el-aside width="200px"></el-aside>
                <el-container>
                    <el-main class="main" style="background: #FFFAFA">
                            <!--点单状态-->
                            <el-row>
                                点单完成
                            </el-row>
                            <!-- 操作-->
                            <el-card>
                                <el-row style="border: 1px solid red">
                                    <div>111111111111111111111111111111111111</div>
                                </el-row>
                                <el-row style="border: 1px solid red">
                                    <div style="margin: 10px;"
                                         @click="toAfterSale">
                                        <el-icon> <ChatDotRound></ChatDotRound></el-icon>
                                        <div>申请售后</div>
                                    </div>
                                    <div style="margin: 10px;"
                                         @click="toRiderDetail">
                                        <el-icon> <ChatDotRound></ChatDotRound></el-icon>
                                        <div>打赏骑手</div>
                                    </div>
                                    <div style="margin: 10px;"
                                         @click="orderAgain">
                                        <el-icon> <ChatDotRound></ChatDotRound></el-icon>
                                        <div>再来一单</div>
                                    </div>
                                    <div style="margin: 10px;"
                                         @click="toComment">
                                        <el-icon> <ChatDotRound></ChatDotRound></el-icon>
                                        <div>评价</div>
                                    </div>
                                    <div style="margin: 10px;"
                                        >
                                        <el-icon> <ChatDotRound></ChatDotRound></el-icon>
                                        <div>联系商家</div>
                                    </div>
                                    <div style="margin: 10px;">
                                        <el-icon> <ChatDotRound></ChatDotRound></el-icon>
                                        <div>联系骑手</div>
                                    </div>
                                    <div style="margin: 10px;">
                                        <el-icon> <ChatDotRound></ChatDotRound></el-icon>
                                        <div>发票</div>
                                    </div>
                                </el-row>
                            </el-card>
                            <!--理赔-->
                            <el-card>
                                理赔
                            </el-card>
                            <!--点单金额-->
                            <el-card>
                                <el-row @click="toShopDetail">
                                    {{data.shop.name}}
                                </el-row>
                                <el-row v-for="item in data.orderItemList"
                                        @click="toShopDetail">
                                    <el-col :span="4">
                                        <el-image src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"></el-image>
                                    </el-col>
                                    <el-col :span="16">
                                        {{item.name}}
                                    </el-col>
                                    <el-col :span="4">
                                        {{item.price}}
                                    </el-col>
                                </el-row>
                                <el-row>
                                    打包费: {{data.order.packingCharges}}
                                </el-row>
                                <el-row>
                                    配送费: {{data.order.deliveryCharge}}
                                </el-row>
                                <el-row>
                                    总金额： {{data.order.totalCharge}}
                                </el-row>
                            </el-card>
                            <!--订单详细信息-->
                            <el-card>
                                <el-row>
                                    期望时间: {{data.order.expectedTime}}
                                </el-row>
                                <el-row>
                                    配送地址: {{data.order.location}}
                                </el-row>
                                <el-row>
                                    用户信息: {{data.user.name}} {{data.user.phone}}
                                </el-row>
                                <el-row>
                                    配送服务: {{data.order.deliveryService}}
                                </el-row>
                                <el-row @click="toRiderDetail">
                                    配送骑手: {{data.order.deliveryRiderId}}
                                    <el-icon><ArrowRight></ArrowRight></el-icon>
                                </el-row>
                                <el-row>
                                    点单号: {{data.order.id}}
                                    <el-button>复制</el-button>
                                </el-row>
                                <el-row>
                                    下单时间: {{data.order.orderTime}}
                                </el-row>
                                <el-row>
                                    支付方式: {{data.order.paymentMethod}}
                                </el-row>
                                <el-row>
                                    点单备注: {{data.order.remark}}
                                </el-row>
                                <el-row>
                                    餐具数量: {{data.order.tableware}}
                                </el-row>
                            </el-card>
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
    // 骑手信息
    rider: {},
    // 用户信息
    user:{
        name: "lwx",
        phone: '123'
    }

})
// 解构抛出 直接使用
// const { type} = toRefs(data)

// Mounted
onMounted(() => {
    data.order.id = route.query.orderId;
    getOrder();
})

// Methods
const getOrder = () => {
    ApiOrder.sel4order(data.order.id).then(res => {
        console.log(res)
        if (res.code === 200){
            data.order = res.data;
            data.shop.id = res.data.shopId;
            getShop()
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
const toShopDetail = (key) => {
    router.push({
        path: '/Consumer/Shop/shopDetail',
        query: {
            shopId: data.shop.id
        }
    })
}

const toRiderDetail = (key) => {
    router.push({
        path: '/Rider/riderDetail',
        query: {
            riderId: data.rider.id
        }
    })
}

// 跳转申请售后界面
const toAfterSale = () => {
    router.push({
        path: '/Consumer/Order/afterSale',
        query: {
            riderId: data.rider.id
        }
    })
}

// todo 再来一单 封装菜品信息并跳转
const orderAgain = () => {
    // 菜品信息Map
    const shopItemMap = new Map();

    router.push({
        path: '/Consumer/Shop/shopDetail',
        query: {
            shopId: data.shop.id,
            shopItem: JSON.stringify(shopItemMap)
        }
    })
}


</script>

<style scoped>

</style>
