<template>
    <el-dialog v-model="data.showDialog"
               destroy-on-close
               width="95%"
               top="5vh"
               title="物品信息">
        <el-card style="border: 1px solid gold;"
                 class="box-card"
                 shadow="never">
            <div v-for="(item,index) in data.order.orderItems">
                <el-row>
                    <!-- todo -->
                    <!-- {{it.picture}}-->
                    <el-col :span="6">
                        <el-form-item label="物品名">
                            <el-input v-model="item.shopItem.name"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="物品价格">
                            <el-input-number v-model="item.shopItem.price"></el-input-number>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="物品描述">
                            <el-input v-model="item.shopItem.description" type="textarea"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="物品数量">
                            <el-input v-model="item.amount"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </div>

            <el-row>
                <el-button @click="orderPay()">
                    立即支付
                </el-button>
                <el-button @click="orderCanel()">
                    取消订单
                </el-button>
            </el-row>
        </el-card>
    </el-dialog>
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
    showDialog: true,
    // 订单信息
    order: {
        // 基本信息
        deliveryCharge: 1,
        deliveryRiderId: "1",
        deliveryService: "1",
        expectedTime: "2023-11-30 05:59:56",
        id: "1730165452519075841",
        location: "1",
        orderTime: "2023-11-30 05:59:59",
        packingCharges: 1,
        paymentMethod: "0",
        remark: "1",
        status: 0,
        tableware: "1",
        totalCharge: 1,
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
            categoryIds: '',
            closeTime: "15:31:12",
            county: "1",
            deliveryCharge: 2,
            description: "1",
            id: "1",
            itemCategory: "1",
            location: "1",
            minPrice: 2,
            name: "店铺1",
            openTime: "08:00:01",
            params: null,
            picture: "http://127.0.0.1:9000/test/ad69ca1afd7630be2e290b9e3ea57541_.jpg",
            province: "1",
            safetyFile: "http://127.0.0.1:9000/test/226f5de9d23239c69cc02902e5974d7f_.jpg",
            salesVolume: 1,
            score: 1,
            status: 1,
            userId: localStorage.getItem('userId'),
        },
        shopId: "1",
        userId: localStorage.getItem('userId'),
    }
})

// Mounted
onMounted(() => {
    data.order.id = route.query.orderId;
    getOrderData(route.query.orderId);
})

// Methods
const getOrderData = (id) => {
    Api.sel4orderinfo(id).then(res => {
        console.log(res)
        if (res.code === 200){
            data.order = res.data;
        }
    })
}

const orderPay = () => {
    const param = {
        orderId: data.order.id,
        paymentMethod: 'wx'
    }
    Api.orderPay(param).then(res => {
        console.log(res)
        if (res.code === 200){
            if (res.data === true){
                ElMessage.success('支付成功')
                data.showDialog = false;
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
