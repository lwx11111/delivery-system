<template>
    <!--外卖首页信息和地址-->
    <el-row>
        <el-col :span="24"
                style="text-align: center;background: #DAA520; height: 50px">
            <h1>订单</h1>
        </el-col>
    </el-row>
    <!-- 搜索框-->
    <el-row style="margin-top: 10px; margin-bottom: 10px;">
        <el-col :span="24">
            <el-input placeholder="请输入"
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
        <el-tab-pane label="待评价" name="toComment"></el-tab-pane>
        <el-tab-pane label="退款" name="refund"></el-tab-pane>
    </el-tabs>
    <!--订单信息-->
<!--    <OrderCardList :order-list="data.orderList"></OrderCardList>-->
    <div v-for="(item, key) in data.orderList">
        <el-card style="margin-top: 10px">
            <!--头部店铺信息-->
            <el-row style="margin-bottom: 10px"
                    @click="toOrderDetail(key)">
                <el-col :span="4"
                        style="margin-right: 5px">
                    <el-image :src="item.shop.picture"></el-image>
                </el-col>
                <el-col :span="8">
                    <h1>{{item.shop.name}}</h1>
                </el-col>
                <el-col :span="4">
                    <h1>{{item.statusName}}</h1>
                </el-col>
            </el-row>
            <el-divider></el-divider>
            <!-- 物品列表 -->
            <div v-for="(it,i) in item.orderItems"
                 @click="toOrderDetail(key)">
                <el-row style="margin-bottom: 10px">
                    <el-col :span="6"
                            style="margin-right: 5px">
                        <el-image src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"></el-image>
                    </el-col>
                    <el-col :span="6">
                        {{it.shopItem.name}}
                    </el-col>
                    <el-col :span="5">
                        数量：{{it.amount}}
                    </el-col>
                    <el-col style="color:red;" :span="5">
                        价格：{{it.shopItem.price}}￥
                    </el-col>
                </el-row>
            </div>
            <el-row>
                <el-col :span="6">

                </el-col>
                <el-col :span="6">

                </el-col>
                <el-col :span="5">

                </el-col>
                <el-col :span="5">
                    总价：<span style="color: red">{{item.totalCharge}}￥</span>
                </el-col>
            </el-row>
            <el-divider></el-divider>
            <!--操作-->
            <el-row>
                <el-col :span="14">
                </el-col>
                <el-col :span="10">
                    <el-button v-if="item.status === 1 || item.status === 2 || item.status === 3 || item.status === 4"
                               @click="orderCancel(key)">取消订单
                    </el-button>
                    <el-button v-if="item.status === 5"
                               @click="orderRefund(key)">退款
                    </el-button>
                    <el-button v-if="item.status === 5">再来一单</el-button>
                    <el-button v-if="item.status === 5 && item.status !== 51"
                               @click="toComment(key)">评价
                    </el-button>
                </el-col>
            </el-row>
        </el-card>
    </div>
    <PublishComment ref="commentDialog"></PublishComment>
</template>

<script lang="ts" setup>
import {reactive, onMounted, ref,} from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import {ElMessage, ElMessageBox, TabsPaneContext} from "element-plus";
import {Comment, Search} from '@element-plus/icons-vue'
import Api from '@/api/Order/api_orderinfo.js'
import ApiDict from '@/api/Common/api_sysdict.js'
import OrderCardList from "./components/orderCardList.vue";
import PublishComment from "../Comment/publishComment.vue";

const store = useStore();
const router = useRouter()

// Data
const data = reactive({
    orderStatus:[
        {
            name:'',
            value:''
        }
    ],
    // 订单列表
    orderList: [
        {
            // 基本信息
            deliveryCharge: '',
            deliveryRiderId: "",
            deliveryService: "",
            expectedTime: "",
            id: "",
            location: "",
            orderTime:"",
            packingCharges:0,
            paymentMethod:"",
            remark:"",
            status:0,
            statusName:'',
            tableware:"",
            totalCharge:0,
            // 订单物品
            orderItems: [
                {
                    shopItem: {
                        categoryName: "",
                        description: "",
                        id: null,
                        name: "",
                        picture: "",
                        price: 0,
                        shopId: null,
                    },
                    amount: 0
                },
                {
                    shopItem: {},
                    amount: 0
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
                userId:localStorage.getItem('userId'),
            },
            shopId:"1",
            userId:localStorage.getItem('userId'),
        },
    ],
    // 菜单栏名称
    tabName: 'allOrder',
    // 查询参数
    params: {
        name:'',
        userId: localStorage.getItem('userId'),
        status: '',
        pageIndex: 1,
        pageSize: 10
    },
})

// Mounted
onMounted(() => {
    getOrderStatus()
    getOrderList(0);
})

// Methods

const commentDialog = ref();
const toComment = (key) => {
    commentDialog.value.init(data.orderList[key].id);
}

/**
 * 获取订单状态字典
 */
const getOrderStatus = () => {
    const param = {
        parentId: '1'
    }
    ApiDict.selpage4sysdict(param).then(res => {
        console.log(res)
        if (res.code === 200){
            console.log(res.data.records)
            data.orderStatus = res.data.records;
        }
    })
}
// 查询订单列表
const getOrderList = (status) => {
    // 0默认全部订单
    if (status === 0){
        data.params.status = '';
    } else {
        data.params.status = status;
    }
    Api.selpage4orderinfo(data.params).then(res => {
        if (res.code === 200){
            console.log(res.data.records)
            data.orderList = res.data.records;
            // 订单状态赋值
            for (let i = 0; i < data.orderList.length; i++) {
                if (data.orderList[i].statusName === null || data.orderList[i].statusName === undefined || data.orderList[i].statusName === ''){
                    for (let j = 0; j < data.orderStatus.length; j++){
                        if (data.orderList[i].status.toString() === data.orderStatus[j].value){
                            data.orderList[i].statusName = data.orderStatus[j].name
                        }
                    }
                }

            }
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
    console.log(tab);
    switch (tab.props.name){
        case 'allOrder' :
            console.log("lwx");
            getOrderList(0);
            break;
        case 'toComment':
            getOrderList(5);
            break;
        case 'refund':
            getOrderList(8);
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
