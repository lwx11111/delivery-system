<template>
<!--    todo : 该界面作为子组件，shopDetail父组件，公有数据太多-->
    <div class="common-layout">
        <el-dialog>
            <!--物品图片-->
            <el-image src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"></el-image>
            <!--物品描述-->
            <el-row style="border: 1px solid red">
                <el-col style="border: 1px solid red">
                    <div>{{data.shopItemInfo.name}}</div>
                </el-col>
            </el-row>
            <!--价格和加购-->
            <el-row style="border: 1px solid red">
                <el-col :span="10" style="border: 1px solid red">
                    <div>{{data.shopItemInfo.price}}</div>
                </el-col>
                <el-col :span="10" style="border: 1px solid red">
                    <el-row>
                        <el-icon size="20px"
                                 @click="subCart(data.shopItemInfo.id)">
                            <Remove />
                        </el-icon>
                        {{data.sum}}
                        <el-icon size="20px"
                                 @click="addCart(data.shopItemInfo.id)">
                            <CirclePlus />
                        </el-icon>
                    </el-row>
                </el-col>
            </el-row>
            <!--物品内容-->
            <el-tabs v-model="data.tabName" class="demo-tabs" @tab-click="handleClick">
                <el-tab-pane label="详情" name="order">
                    <el-row>1</el-row>
                    <el-row>2</el-row>
                    <el-row>3</el-row>
                </el-tab-pane>
                <el-tab-pane label="评价" name="shopComment">Role</el-tab-pane>
            </el-tabs>
            <!--购物车-->
            <div>
                总金额：<span v-text="data.totalAmount"></span>
                <div @click="submitOrder">
                    提交订单
                </div>
            </div>
        </el-dialog>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter, useRoute } from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";
import ApiCategory from '../../../api/api_category.js'
import ApiShop from '../../../api/api_shop.js'
import ApiShopItem from '../../../api/api_shop_item.js'
import ApiShopItemCategory from '../../../api/api_shopItemCategory.js'
import ApiOrder from '../../../api/api_order.js'

import { Search, CirclePlus, Remove } from '@element-plus/icons-vue'
const store = useStore();
const router = useRouter();
const route = useRoute();

// Data
const data = reactive({
    // 物品信息
    shopItemInfo: {},
    // 订单
    order: new Map(),
    // el-tab 选中的name
    tabName: '',
    // 总价
    totalAmount: 0,
    // 发送的订单信息
    orderInfo: {
        itemInfo:[],
        packing_charges: '',
        delivery_charge: '',
        expected_time: '',
        total_charge: '',
        location: '',
        delivery_service: '',
        delivery_rider_id: '',
        order_time: '',
    },
})
// 解构抛出 直接使用
// const { type} = toRefs(data)

// Mounted
onMounted(() => {
    // json -> map
    let mapJson = route.query.order;
    const obj = JSON.parse(<string>mapJson)
    let map = new Map();
    for (let k of Object.keys(obj)) {
        map.set(k,obj[k]);
    }
    data.order = map
    data.shopItemInfo = JSON.parse(<string>route.query.shopItemInfo)
    data.totalAmount = parseFloat(<string>route.query.totalAmount)

    console.log(data.totalAmount)
})

// Methods

// 添加物品到购物车
const addCart = (id) => {
    // 维护order值
    if (data.order.has(id)){
        data.order.set(id, data.order.get(id) + 1)
    } else {
        data.order.set(id, 1)
    }
    //总价
    data.totalAmount += data.shopItemInfo.price
    console.log(data.order)
    console.log(data.totalAmount)
}
// 减少物品到购物车
const subCart = (id) => {
    // 维护order值
    if (data.order.has(id)){
        data.order.set(id, data.order.get(id) - 1)
        if (data.order.get(id) === 0){
            data.order.delete(id)
        }
    } else {
        data.order.set(id, 0)
    }
    //总价
    data.totalAmount -= data.shopItemInfo.price
    console.log(data.order)
    console.log(data.totalAmount)
}

// 提交订单
const submitOrder = () => {
    data.orderInfo.packing_charges = data.shopItemInfo.packing_charges
    data.orderInfo.delivery_charge = data.shopItemInfo.delivery_charge
    data.orderInfo.total_charge = data.totalAmount
    // 物品信息
    data.order.forEach((value, key) => {
        data.orderInfo.itemInfo.push({
            shopItem: data.shopItemList[key],
            amount: value
        })
    })
    console.log(data.orderInfo)
    ApiOrder.add4order(data.orderInfo).then(res => {
        console.log(res)
        if (res.code === 200){
            ElMessageBox.alert('下单成功', '提示', {
                confirmButtonText: '确定',
                callback: action => {
                    router.push({
                        path: '/Consumer/orderList',
                    })
                }
            });
        } else {
            ElMessage.error(res.msg)
        }
    })
}

const handleClick = (tab: TabsPaneContext, event: Event) => {
    console.log(tab, event)
}
</script>

<style scoped>

</style>
