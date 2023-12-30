<template>
  <!--订单信息-->
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
                    <el-col :span="4">
                        {{it.shopItem.name}}
                    </el-col>
                    <el-col :span="4">
                        数量：{{it.amount}}
                    </el-col>
                    <el-col :span="4">
                        价格：{{it.shopItem.price}}￥
                    </el-col>
                </el-row>
            </div>
            <el-row >
                总价：<span style="color: red">{{item.totalCharge}}￥</span>
            </el-row>
            <!--操作-->
            <el-row>
                <el-button v-if="item.status === 1 || item.status === 2 || item.status === 3 || item.status === 4"
                           @click="orderCancel(key)">取消订单</el-button>
                <el-button v-if="item.status === 5"
                           @click="orderRefund(key)">退款</el-button>
                <el-button>再来一单</el-button>
                <el-button @click="toComment(key)">评价</el-button>
            </el-row>
        </el-card>
    </div>

</template>

<script lang="ts" setup>
import {reactive, onMounted, defineProps, ref} from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";
import PublishComment from "../../Comment/publishComment.vue";

const store = useStore();
const router = useRouter()

// Props
const props = defineProps({
    orderList: [],
})

// Data
const data = reactive({

})

// Mounted
onMounted(() => {

})

// Methods
const commentDialog = ref();
const toComment = (key) => {
    commentDialog.value.init(props.orderList[key].id);
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
