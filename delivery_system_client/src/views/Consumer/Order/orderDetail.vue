<template>
    <!--订单状态-->
    <el-row @click="showOrderStatus">
        <el-col :span="3">
            <h1 style="text-align: center">{{data.order.statusName}}</h1>
        </el-col>
        <el-col :span="2" style="padding-top: 25px">
            <el-icon><ArrowRight /></el-icon>
        </el-col>
    </el-row>
    <!-- 操作-->
    <el-card style="margin-bottom: 10px">
        <!--标语-->
        <el-row>
            <div>期待再次光联</div>
        </el-row>
        <el-divider></el-divider>
        <el-row>
<!--            <div style="margin: 10px;"-->
<!--                 @click="toAfterSale">-->
<!--                <el-icon> <ChatDotRound></ChatDotRound></el-icon>-->
<!--                <div>申请售后</div>-->
<!--            </div>-->
<!--            <div style="margin: 10px;"-->
<!--                 @click="toRiderDetail">-->
<!--                <el-icon> <ChatDotRound></ChatDotRound></el-icon>-->
<!--                <div>打赏骑手</div>-->
<!--            </div>-->
<!--            <div style="margin: 10px;"-->
<!--                 @click="orderAgain">-->
<!--                <el-icon> <ChatDotRound></ChatDotRound></el-icon>-->
<!--                <div>再来一单</div>-->
<!--            </div>-->
<!--            <div style="margin: 10px;"-->
<!--                 @click="toComment()">-->
<!--                <el-icon> <ChatDotRound></ChatDotRound></el-icon>-->
<!--                <div>评价</div>-->
<!--            </div>-->
<!--            <div style="margin: 10px;">-->
<!--                <el-icon> <ChatDotRound></ChatDotRound></el-icon>-->
<!--                <div>联系商家</div>-->
<!--            </div>-->
<!--            <div style="margin: 10px;">-->
<!--                <el-icon> <ChatDotRound></ChatDotRound></el-icon>-->
<!--                <div>联系骑手</div>-->
<!--            </div>-->
<!--            <div style="margin: 10px;">-->
<!--                <el-icon> <ChatDotRound></ChatDotRound></el-icon>-->
<!--                <div>发票</div>-->
<!--            </div>-->
        </el-row>
    </el-card>
    <!--理赔-->
<!--    <el-card style="margin-bottom: 10px">-->
<!--        理赔-->
<!--    </el-card>-->
    <!--点单金额-->
    <el-card style="margin-bottom: 10px">
        <!--头部店铺信息-->
        <el-row @click="toShopDetail">
            <h1>{{data.order.shop.name}}</h1>
        </el-row>
        <el-divider></el-divider>
        <!-- 物品列表 -->
        <el-row v-for="item in data.order.orderItems"
                @click="toShopDetail"
                style="margin-bottom: 5px">
            <el-col style="margin-right: 5px"
                    :span="6">
                <el-image :src="item.shopItem.picture"></el-image>
            </el-col>
            <el-col :span="6">
                {{item.shopItem.name}}
            </el-col>
            <el-col :span="5">
                数量：{{item.amount}}
            </el-col>
            <el-col style="color:red;" :span="5">
                价格：{{item.shopItem.price}}￥
            </el-col>
        </el-row>
        <el-divider></el-divider>
        <!--订单基本信息-金额 -->
        <el-row>
            <el-col :span="6"></el-col>
            <el-col :span="6"></el-col>
            <el-col :span="5"></el-col>
            <el-col :span="5">
                <el-row style="color:red;">
                    打包费: {{data.order.packingCharges}}￥
                </el-row>
                <el-row style="color:red;">
                    配送费: {{data.order.deliveryCharge}}￥
                </el-row>
                <el-row style="color:red;">
                    总金额：{{data.order.totalCharge}}￥
                </el-row>
            </el-col>
        </el-row>
    </el-card>

    <!--订单详细信息-->
    <el-card>
        <el-row style="margin-bottom: 5px">
            期望时间: {{data.order.expectedTime}}
        </el-row>
        <el-row style="margin-bottom: 5px">
            配送地址: {{data.addressData.detailAddress}}
        </el-row>
        <el-row style="margin-bottom: 5px">
            用户信息: {{data.addressData.contacts}} ---- {{data.addressData.phone}}
        </el-row>
<!--        <el-row style="margin-bottom: 5px">-->
<!--            配送服务: {{data.order.deliveryService}}-->
<!--        </el-row>-->
        <el-row @click="toRiderDetail"
                style="margin-bottom: 5px">
            配送骑手: {{data.order.riderName}}
<!--            <el-icon><ArrowRight></ArrowRight></el-icon>-->
        </el-row>
        <el-row style="margin-bottom: 5px">
            点单号: {{data.order.id}}
        </el-row>
        <el-row style="margin-bottom: 5px">
            下单时间: {{data.order.orderTime}}
        </el-row>
        <el-row style="margin-bottom: 5px">
            支付方式: {{data.order.paymentMethod}}
        </el-row>
        <el-row style="margin-bottom: 5px">
            点单备注: {{data.order.remark}}
        </el-row>
        <el-row style="margin-bottom: 5px">
            餐具数量:
            <div v-if="data.order.tableware === '1'">需要</div>
            <div v-else>不需要</div>
        </el-row>
    </el-card>

    <!--订单状态流转-->
    <OrderStatus ref="orderStatusDialog"></OrderStatus>

    <PublishComment ref="commentDialog"></PublishComment>
</template>

<script lang="ts" setup>
import { reactive, onMounted, ref } from 'vue'
import { useStore } from "vuex";
import { useRouter, useRoute } from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";
import UserStorage from '@/cache/userStorage.js'
import Api from '@/api/Order/api_orderinfo.js'
import ApiOrderStatus from '@/api/Order/api_orderstatus.js'
import ApiAddress from '@/api/Address/api_address.js'
import { ArrowRight, ChatDotRound } from '@element-plus/icons-vue'
import OrderStatus from "./components/orderStatus.vue";
import PublishComment from "../Comment/publishComment.vue";

const store = useStore();
const router = useRouter();
const route = useRoute();

// Data
const data = reactive({
    // 用户地址信息
    addressData: {},
    // 订单状态
    orderStatus: [],
    // 订单信息
    order: {
        // 地址信息
        addressData: {},
        // 基本信息
        deliveryCharge: 1,
        deliveryRiderId: "1",
        deliveryService: "1",
        expectedTime: "2023-11-30 05:59:56",
        id: "1730165452519075841",
        location: "1",
        orderTime:"2023-11-30 05:59:59",
        packingCharges:1,
        paymentMethod:"0",
        remark:"1",
        status:0,
        tableware:"1",
        totalCharge:1,
        // 订单物品
        orderItems: [
            {
                shopItem: {
                    categoryName: "1111",
                    description: "1",
                    id: null,
                    name: "物品1",
                    picture: "1",
                    price: 1,
                    shopId: null,
                },
                amount: 1
            },
            {
                shopItem: {},
                amount: 1
            },
        ],
        // 店铺信息
        shop: {
            categoryIds:'',
            closeTime:"15:31:12",
            county:"1",
            deliveryCharge:2,
            description:"1",
            id:"1",
            itemCategory:"1",
            location:"1",
            minPrice:2,
            name:"店铺1",
            openTime:"08:00:01",
            params:null,
            picture:"http://127.0.0.1:9000/test/ad69ca1afd7630be2e290b9e3ea57541_.jpg",
            province:"1",
            safetyFile:"http://127.0.0.1:9000/test/226f5de9d23239c69cc02902e5974d7f_.jpg",
            salesVolume:1,
            score:1,
            status:1,
            userId: UserStorage.getUserId(),
        },
        shopId:"1",
        userId:UserStorage.getUserId(),
    },
    // 骑手信息
    rider: {},
    // 用户信息
    user:{
        name: "lwx",
        phone: '123'
    }

})
// Mounted
onMounted(() => {
    data.order.id = route.query.orderId;
    getOrder(route.query.orderId);
})

// Methods

const commentDialog = ref();
const toComment = (key) => {
    commentDialog.value.init(data.order.id);
}

/**
 * 查看订单状态流转信息
 */
const orderStatusDialog = ref();
const showOrderStatus = () => {
    const params = {
        orderId: data.order.id
    }
    ApiOrderStatus.selpage4orderstatus(params).then(res =>{
        if (res.code === 200){
            data.orderStatus = res.data.records;
            orderStatusDialog.value.init(data.orderStatus);
        } else {
            ElMessage.error(res.message);
        }
    })

}

const getOrder = (id) => {
    Api.sel4orderinfo(id).then(res => {
        console.log(res)
        if (res.code === 200){
            data.order = res.data;
            // 获得地址信息
            getAddress(data.order.userAddressId);
        }
    })
}

/**
 * 获取地址信息
 * @param id
 */
const getAddress = (id) => {
    ApiAddress.sel4address(id).then(res => {
        if (res.code === 200){
            data.addressData = res.data;
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
