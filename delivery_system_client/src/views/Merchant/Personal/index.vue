<template>
    <!-- 头像名字-->
    <el-card style="margin-bottom: 10px">
        <el-row>
            <el-col style="margin-right: 10px"
                    :span="4">
                <el-image :src="data.shop.picture"></el-image>
            </el-col>
            <el-col :span="10">
                <h1>{{data.shop.name}}</h1>
            </el-col>
        </el-row>
    </el-card>
<!--    <el-card style="margin-bottom: 10px">-->
<!--        <el-row>-->
<!--            <el-col :span="12">-->
<!--                <h1>收益：</h1>-->
<!--            </el-col>-->
<!--            <el-col :span="12">-->
<!--                <h1 style="color: red">{{ data.earningsData.total - data.earningsData.deliveryChargeTotal }} ￥</h1>-->
<!--            </el-col>-->
<!--        </el-row>-->
<!--        <el-row>-->
<!--            <el-col :span="12">-->
<!--                <h1>完成订单数：</h1>-->
<!--            </el-col>-->
<!--            <el-col :span="12">-->
<!--                <h1>{{data.earningsData.num}}</h1>-->
<!--            </el-col>-->
<!--        </el-row>-->
<!--    </el-card>-->

    <el-row>
        <PieWithPadAngle style="margin-right: 20px"></PieWithPadAngle>
        <MixedLineAndBar></MixedLineAndBar>
    </el-row>

</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import {ElMessage} from "element-plus";
import Api from '@/api/Shop/api_shop.js'
import ApiOrder from '@/api/Order/api_orderinfo.js'
import UserStorage from '@/cache/userStorage.js';
import PieWithPadAngle from '@/views/echarts/PieWithPadAngle.vue'
import MixedLineAndBar from "../../echarts/mixedLineAndBar.vue";
const store = useStore();
const router = useRouter()

// Data
const data = reactive({
    //用户信息
    user: {
        id: UserStorage.getUserId(),
        name: UserStorage.getUserName(),
    },
    // 店铺信息
    shop:{

    },
    // 收益数据
    earningsData: {
        total:'',
        num:'',
        deliveryChargeTotal: ''
    },

})

// Mounted
onMounted(() => {
    getData()

})

// Methods
const getData = () => {
    if (data.user.id === null){
        ElMessage({
            type: 'warning',
            message: '请先登录',
        })
        router.push("/login")
    }
    let param = {
        userId: data.user.id
    }
    Api.selpage4shop(param).then(res=> {
        console.log(res)
        if (res.code === 200){
            data.shop = res.data.records[0];
            // getEarningsData()
        }
    })
}

/**
 * 查询本周收益
 */
const getEarningsData = () => {
    const params = {
        shopId: data.shop.id
    }

    ApiOrder.getEarningsData(params).then(res => {
        console.log(res)
        if (res.code === 200){
            data.earningsData = res.data;
        }
    })

}
</script>

<style scoped>

</style>
