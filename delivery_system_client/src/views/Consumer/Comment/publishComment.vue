<template>
    <el-dialog v-model="data.showDialog"
               destroy-on-close
               width="90%"
               :title="data.operateTitle">
        <!--骑手打分-->
        <el-card style="margin-bottom: 10px">
            <el-row>
                <h1>骑手评价</h1>
            </el-row>
            <el-row>
                <el-col :span="10">
                    {{data.rider.accountName}}
                </el-col>
                <el-col :span="10">
                    送达时间：{{data.order.expectedTime}}
                </el-col>
            </el-row>
            <el-row>
                <el-input v-model="data.riderComment" type="textarea"></el-input>
            </el-row>
        </el-card>

        <!--商家打分-->
        <el-card style="margin-bottom: 10px">
            <el-row>
                <h1>商家评价</h1>
            </el-row>
            <el-row>
                <el-col>
                    <el-image :src="data.shop.picture"></el-image>
                </el-col>
                <el-col>
                    {{data.shop.name}}
                </el-col>
            </el-row>
            <el-row>
                <el-input v-model="data.shopComment" type="textarea"></el-input>
            </el-row>
        </el-card>

        <el-card>
            <el-button @click="submit()">提交</el-button>
        </el-card>
    </el-dialog>
</template>

<script lang="ts" setup>
import Api from '@/api/Order/api_orderinfo.js'
import ApiShop from '@/api/Shop/api_shop.js'
import ApiComment from '@/api/Comment/api_comment.js'
import ApiUser from '@/api/auth.js'

import { reactive, onMounted } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";
import UserStorage from '@/cache/userStorage.js'
const store = useStore();
const router = useRouter()

// Data
const data = reactive({
    order: {},
    // dialog
    operateTitle: '评论',
    showDialog: false,
    // 骑手信息
    rider:{
        id:'1',
        name:'11',
    },
    // 商家
    shop:{
        id:'',
        name:'',
        picture:'',

    },
    // 个人
    user:{
        id:'1'
    },
    orderId: '',
    time:'111',
    // 评论信息
    riderComment:'',
    shopComment:'',
})

// Props
const props = defineProps({

})

// Mounted
onMounted(() => {

})

// Methods
const submit = () => {
    if (data.riderComment === '' || data.shopComment === ''){
        ElMessage.error('请填写完整信息');
        return;
    }
    const param = {
        userId: UserStorage.getUserId(),
        orderId: data.orderId,
        riderId: data.rider.id,
        shopId: data.shop.id,
        riderComment: data.riderComment,
        shopComment: data.shopComment,
    }
    ApiComment.saveComments(param).then(res => {
        if (res.code === 200){
            ElMessage.success('评论成功');
            data.showDialog = false;
            // 订单状态修改
            const params = {
                orderId: data.orderId
            }
            Api.orderComment(params).then(res => {
                console.log(res)
                if (res.code === 200){
                    router.go(0)
                }
            })
        } else {
            ElMessage.error('评论失败');
        }
    })
}

const getOrderInfo = () => {
    Api.sel4orderinfo(data.orderId).then(res => {
        console.log(res);
        if (res.code === 200){
            data.order = res.data;
            getRiderByOrderId();
        }
    })
}

const getShopByOrderId = () => {
    console.log(data.orderId)
    ApiShop.getShopByOrderId(data.orderId).then(res => {
        console.log(res);
        if (res.code === 200){
            data.shop = res.data;
        }
    })
}

const getRiderByOrderId = () => {
    ApiUser.getAccountById(data.order.riderId).then(res => {
        console.log(res);
        if (res.code === "20000"){
            data.rider = res.data.account;
        }
    })
}

const init = (orderId) => {
    data.orderId = orderId;
    // 获取信息
    getShopByOrderId();
    getOrderInfo();
    // 界面显示
    data.showDialog = true;
}

//暴露state和play方法
defineExpose({
    init,
});
</script>

<style scoped>

</style>
