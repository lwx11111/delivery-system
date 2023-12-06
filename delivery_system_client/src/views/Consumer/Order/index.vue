<template>
    <div class="common-layout">
        <el-container>
            <el-main style="background: #FFFAFA">
                <!--外卖首页信息和地址-->
                <el-row>
                    <el-col :span="24"
                            style="background: #DAA520; height: 50px">
                        外卖 地址 消息
                    </el-col>
                </el-row>
                <!-- 搜索框-->
                <el-row style="margin-top: 10px; margin-bottom: 10px;">
                    <el-col :span="12">
                        <el-input style="width: 490px;"
                                placeholder="Please input"
                                class="input-with-select">
                            <template #prepend>
                                <el-button :icon="Search" />
                            </template>
                            <template #append>
                                <div>搜索</div>
                            </template>
                        </el-input>
                    </el-col>
                </el-row>
                <!--菜单栏-->
                <el-tabs v-model="data.tabName" class="demo-tabs" @tab-click="handleClick">
                    <el-tab-pane label="全部订单" name="allOrder"></el-tab-pane>
                    <el-tab-pane label="待评价" name="toComment">Role</el-tab-pane>
                    <el-tab-pane label="退款" name="refund">Role</el-tab-pane>
                </el-tabs>
                <!--订单信息-->
                <div v-for="(item, key) in data.orderList">
                    <el-card style="margin-top: 10px">
                        <!--头部店铺信息-->
                        <el-row style="margin-bottom: 10px"
                                @click="toOrderDetail(key)">
                            <el-col :span="4"
                                    style="margin-right: 5px">
                                <el-image src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"></el-image>
                            </el-col>
                            <el-col :span="8">
                                {{item.shop.name}}
                            </el-col>
                            <el-col :span="4">
                                {{item.status}}
                            </el-col>
                        </el-row>
                        <el-divider></el-divider>
                        <!-- 物品列表 -->
                        <div v-for="(it,i) in item.orderItems"
                             @click="toOrderDetail(key)">
                            <el-row style="margin-bottom: 10px">
                                <el-col :span="7"
                                        style="margin-right: 5px">
                                    <el-image src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"></el-image>
                                </el-col>
                                <el-col :span="10">
                                    {{it.shopItem.name}}
                                </el-col>
                                <el-col :span="7">
                                    {{item.totalCharge}}
                                </el-col>
                            </el-row>
                        </div>

                        <!--操作-->
                        <el-row>
                            <el-button v-if="item.status === 1 || item.status === 2 || item.status === 3 || item.status === 4"
                                        @click="orderCancel(key)">取消订单</el-button>
                            <el-button v-if="item.status === 5"
                                       @click="orderRefund(key)">退款</el-button>
                            <el-button>再来一单</el-button>
                            <el-button>评价</el-button>
                        </el-row>
                    </el-card>
                </div>
            </el-main>
        </el-container>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";
import ApiOrder from '../../../api/api_order.js'
import Api from '@/api/Order/api_orderinfo.js'
import StringUtil from '@/utils/stringUtil.js'
const store = useStore();
const router = useRouter()
import { Search } from '@element-plus/icons-vue'
// Data
const data = reactive({
    // 订单列表
    orderList: [
        {
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
                userId:"1",
            },
            shopId:"1",
            userId:"1",
        },
    ],
    // 菜单栏名称
    tabName: 'allOrder',
    // 查询参数
    params: {
        // todo user
        userId: '1',
        status: '',
        pageIndex: 1,
        pageSize: 10
    },
})
// 解构抛出 直接使用
// const { type} = toRefs(data)

// Mounted
onMounted(() => {
    getOrderList(0);
})

// Methods

// 查询订单列表
const getOrderList = (status) => {
    // data.params.status = status;
    // todo 逻辑删除不展示
    ApiOrder.selpage4order(data.params).then(res => {
        if (res.code === 200){
            console.log(res.data.records)
            data.orderList = res.data.records;
        }
    })
}

// 打开订单详情界面
const toOrderDetail = (key) => {
    router.push({
        path: '/Consumer/Order/orderDetail',
        query: {
            orderId: data.orderList[key].id
        }
    })
}

// 菜单栏点击事件
const handleClick = (tab: TabsPaneContext, event: Event) => {
    switch (tab.props.name){
        case 'allOrder' :
            console.log("lwx");
            getOrderList(0);
            break;
        case 'toComment':
            getOrderList(1);
            break;
        case 'refund':
            getOrderList(2);
            break;
    }
}

const orderCancel = (key) => {
    let orderId = data.orderList[key].id
    // 提示确认
    ElMessageBox.confirm(
        '确认取消？',
        '警告',
        {
            confirmButtonText: '确认取消',
            cancelButtonText: '取消',
            type: 'warning',
        }
    ).then(() => {
        const param = {
            orderId: orderId
        }
        Api.orderCancel(param).then(res => {
            if (res.code === 200 && res.data === true){
                ElMessage({
                    type: 'success',
                    message: '取消订单成功',
                })
                getOrderList(0);
            } else {
                ElMessage({
                    type: 'warning',
                    message: '取消订单失败，请重试',
                })
            }
        })
    }).catch(() => {
        ElMessage({
            type: 'info',
            message: '已取消',
        })
    })
}


const orderRefund = (key) => {
    let orderId = data.orderList[key].id
    // 提示确认
    ElMessageBox.confirm(
        '确认取消？',
        '警告',
        {
            confirmButtonText: '确认取消',
            cancelButtonText: '取消',
            type: 'warning',
        }
    ).then(() => {
        const param = {
            orderId: orderId
        }
        Api.orderRefund(param).then(res => {
            if (res.code === 200 && res.data === true){
                ElMessage({
                    type: 'success',
                    message: '取消订单成功',
                })
                getOrderList(0);
            } else {
                ElMessage({
                    type: 'warning',
                    message: '退款失败，请重试',
                })
            }
        })
    }).catch(() => {
        ElMessage({
            type: 'info',
            message: '已取消',
        })
    })
}

</script>

<style scoped>

</style>
