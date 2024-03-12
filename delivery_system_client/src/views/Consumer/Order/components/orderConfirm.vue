<!-- 提交订单确认页面 -->
<template>
    <el-dialog v-model="data.dialogVisible">
        <el-card>
            地址
        </el-card>

        <el-card style="margin-top: 10px">
            <!--头部店铺信息-->
            <el-row style="margin-bottom: 10px">
                <el-col :span="8">
                    <h1>{{data.shop.name}}</h1>
                </el-col>
            </el-row>

            <!-- 物品列表 -->
            <div v-for="(item, index) in data.orderInfo.orderItems">
                <el-row style="margin-bottom: 10px">
                    <el-col :span="6"
                            style="margin-right: 5px">
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
            </div>

            <!--餐具和备注  -->
            <el-row style="margin-top: 10px">
                <el-col :span="12">
                    <el-form-item label="餐具数量">
                        <el-select v-model="data.orderInfo.tableware"
                                   placeholder="餐具选择">
                            <el-option label="需要" value="1"></el-option>
                            <el-option label="不需要" value="0"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="备注">
                        <el-input type="textarea"
                                  placeholder="请输入备注"
                                  v-model="data.orderInfo.remark">
                        </el-input>
                    </el-form-item>
                </el-col>
            </el-row>

<!--            <el-row style="margin-top: 10px">-->
<!--                <el-col :span="17"></el-col>-->
<!--                <el-col :span="5">-->
<!--                    打包费：<span style="color: red"> {{data.orderInfo.packingCharges}}￥</span>-->
<!--                </el-col>-->
<!--            </el-row>-->
            <el-row style="margin-top: 10px">
                <el-col :span="17"></el-col>
                <el-col :span="5">
                    配送费：<span style="color: red"> {{data.orderInfo.deliveryCharge}}￥</span>
                </el-col>
            </el-row>
            <el-row style="margin-top: 10px">
                <el-col :span="17"></el-col>
                <el-col :span="5">
                    总价：<span style="color: red">{{data.orderInfo.totalCharge}}￥</span>
                </el-col>
            </el-row>
        </el-card>

        <el-card style="border: 1px solid gold ">
            <el-row>
                <el-col :span="18"></el-col>
                <el-col :span="3">
                    <el-button @click="submitOrder()"
                               style="background: gold">
                        提交订单
                    </el-button>
                </el-col>
            </el-row>
        </el-card>
    </el-dialog>

</template>

<script lang="ts" setup>
import { reactive, watch, onMounted, ref, defineProps } from 'vue'
import { useStore } from "vuex";
import { useRouter, useRoute } from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";

import ApiOrder from '@/api/Order/api_orderinfo.js'
import UserStorage from '@/cache/userStorage.js'
const store = useStore();
const router = useRouter();
const route = useRoute();

// Data
const data = reactive({
    dialogVisible: false,
    // 店铺信息
    shop: {
        id: '',
        userId : UserStorage.getUserId(),
        name : '',
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

    // 发送的订单信息
    orderInfo: {
        id:'',
        // 菜品信息
        orderItems:[],
        userId: UserStorage.getUserId(),
        shopId: '',
        deliveryRiderId: '',
        packingCharges: '',
        deliveryCharge: '',
        totalCharge: '',
        expectedTime: '2023-01-01 00:00:00',
        location: '1',
        deliveryService: '1',
        orderTime: '2023-01-01 00:00:00',
        status: '0',
        paymentMethod: '1',
        remark: '1',
        tableware: '1',
    },
})

// Props
const props = defineProps({

})

// Mounted
onMounted(() => {

})

// Methods

/**
 * 提交订单
 */
const submitOrder = () => {
    ApiOrder.add4orderinfo(data.orderInfo).then(res => {
        if (res.code === 200){
            // 主键保存
            data.orderInfo.id = res.data;
            // 去支付
            router.push({
                path: '/Consumer/Order/orderPay',
                query: {
                    orderId: data.orderInfo.id
                }
            })
        } else {
            ElMessage.error(res.msg)
        }
    })
}
const init = (shop, orderInfo) => {
    // 界面初始化接收参数
    data.shop = shop;
    data.orderInfo = orderInfo;
    data.dialogVisible  = true;
}

//暴露方法
defineExpose({
    init,
});

</script>

<style scoped>

</style>
