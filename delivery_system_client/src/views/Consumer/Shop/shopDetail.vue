<template>
    <div class="common-layout">
        <!--店铺信息和图片-->
        <el-card style="margin-bottom: 10px">
            <el-row>
                <el-col :span="20">
                    <div>{{data.shop.name}}</div>
                    <div>{{data.shop.name}}</div>
                </el-col>
                <el-col :span="4">
                    <el-image src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"></el-image>
                </el-col>
            </el-row>
        </el-card>

        <!--店铺描述-->
        <el-card style="margin-bottom: 10px">
            <el-row>
                <el-col>
                    <div>{{data.shop.description}}</div>
                </el-col>
            </el-row>
        </el-card>

        <!--店铺内容-->
        <el-tabs v-model="data.tabName" @tab-click="handleClick">
            <el-tab-pane label="点菜" name="order">
                <el-row>
                    <!-- 分类列表-->
                    <el-col :span="4"
                            style="border-right: 1px solid red; " >
                        <el-scrollbar height="400px" style="padding: 10px">
                            <el-space direction="vertical">
                                <el-card style="width: 90%; border: 1px solid red"
                                         v-for="item in data.shopItemCategoryList">
                                    {{item.name}}
                                </el-card>
                            </el-space>
                        </el-scrollbar>
                    </el-col>
                    <!--菜品列表-->
                    <el-col :span="20">
                        <!--todo 高度-->
                        <el-scrollbar height="300px" style=" border:1px solid red; padding: 10px">
                            <el-space direction="vertical">
                                <!--@click="toShopItemDetail(key)"-->
                                <el-card v-for="(item, key) in data.shopItemList">
                                    <el-row>
                                        <el-col :span="11"
                                                @click="toShopItemDetail(key)"
                                                style="margin-right: 5px">
                                            <el-image src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"></el-image>
                                        </el-col>
                                        <el-col :span="12">
                                            <el-row @click="toShopItemDetail(key)">
                                                <span style="font-size: 20px;font-weight: bold;">
                                                    {{item.name}}
                                                </span>
                                            </el-row>
                                            <el-row>{{item.price}}</el-row>
                                            <el-row>
                                                <el-icon size="20px"
                                                         @click="subCart(key)">
                                                    <Remove />
                                                </el-icon>
                                                {{data.order.get(item.id)}}
                                                <el-icon size="20px"
                                                         @click="addCart(key)">
                                                    <CirclePlus />
                                                </el-icon>
                                            </el-row>
                                        </el-col>
                                    </el-row>
                                </el-card>
                            </el-space>
                        </el-scrollbar>
                    </el-col>
                </el-row>
            </el-tab-pane>
            <el-tab-pane label="Config" name="second">Config</el-tab-pane>
            <el-tab-pane label="评价" name="shopComment">Role</el-tab-pane>
            <el-tab-pane label="店铺" name="shopInfo">
                <div>
                    <el-row>
                        {{data.shop.name}}
                    </el-row>
                    <el-row>
                        {{data.shop.name}}
                    </el-row>
                </div>
            </el-tab-pane>
        </el-tabs>
        <!--购物车-->
        <el-card>
            <div>
                总金额：<span v-text="data.totalAmount"></span>
                <div @click="submitOrder">
                    提交订单
                </div>
            </div>
        </el-card>

        <!--菜品详情组件-->
<!--        <ShopItemDetail></ShopItemDetail>-->
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
import ShopItemDetail from "./shopItemDetail.vue";
import { Search, CirclePlus, Remove } from '@element-plus/icons-vue'
import ShopCardList from "./components/shopCardList.vue";
const store = useStore();
const router = useRouter();
const route = useRoute();

// Data
const data = reactive({
    // 店铺信息
    shop: {
        id: '',
        userId : '',
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
    // el-tab 选中的name
    tabName: 'order',
    // todo 菜品分类列表
    shopItemCategoryList: [],
    // 菜品列表
    shopItemList: [],
    // 总价
    totalAmount: 0,
    // 订单 key为shopItemId, value为数量
    order: new Map(),
    // 发送的订单信息
    orderInfo: {
        // 菜品信息
        orderItems:[],
        shopId: '',
        deliveryRiderId: '',
        packingCharges: '',
        deliveryCharge: '',
        totalCharge: '',
        expectedTime: '2023-01-01 00:00:00',
        location: '1',
        deliveryService: '1',
        orderTime: '2023-01-01 00:00:00',
        status: '1',
        paymentMethod: '1',
        remark: '1',
        tableware: '1',
    },
    // 查询参数
    params: {
        shopId: '',
        pageIndex: 1,
        pageSize: 10
    },
})
// 解构抛出 直接使用
// const { type} = toRefs(data)

// Mounted
onMounted(() => {
    data.shop.id = route.query.shopId;
    data.params.shopId = route.query.shopId;
    getShop();
    getShopItemList();
    getShopItemCategoryList();
})

// Methods

const getShop = () => {
    ApiShop.sel4shop(data.shop.id).then(res => {
        if (res.code === 200){
            data.shop = res.data;
        }
    })
}
// 菜品列表
const getShopItemList = () => {
    ApiShopItem.selpage4shopitem(data.params).then(res => {
        if (res.code === 200){
            data.shopItemList = res.data.records;
        }
    })
}

// 菜品的分类列表
const getShopItemCategoryList  = () => {
    ApiShopItemCategory.selpage4shopitemcategory(data.params).then(res => {
        if (res.code === 200){
            data.shopItemCategoryList = res.data.records;
        }
    })
}
// 添加物品到购物车
const addCart = (key) => {
    // 物品ID
    const id = data.shopItemList[key].id
    // 维护order值
    if (data.order.has(id)){
        data.order.set(id, data.order.get(id) + 1)
    } else {
        data.order.set(id, 1)
    }
    //总价
    data.totalAmount += data.shopItemList[key].price
}
// 减少物品到购物车
const subCart = (key) => {
    // 物品ID
    const id = data.shopItemList[key].id
    // 维护order值
    if (data.order.has(id)){
        if (data.order.get(id) === 0){
            data.order.delete(id)
        } else {
            data.order.set(id, data.order.get(id) - 1)
        }
    } else {
        data.order.set(id, 0)
    }
    //总价
    data.totalAmount -= data.shopItemList[key].price
}

// 提交订单
const submitOrder = () => {
    // 封装参数
    data.orderInfo.packingCharges = data.shop.packingCharges
    data.orderInfo.deliveryCharge = data.shop.deliveryCharge
    data.orderInfo.shopId = data.shop.id
    data.orderInfo.totalCharge = data.totalAmount
    // 物品信息
    data.order.forEach((value, key) => {
        data.orderInfo.orderItems.push({
            shopItem: data.shopItemList[key],
            amount: value
        })
    })
    console.log(data.orderInfo)
    ApiOrder.add4order(data.orderInfo).then(res => {
        if (res.code === 200){
            ElMessageBox.alert('下单成功', '提示', {
                confirmButtonText: '确定',
                callback: action => {
                    router.push({
                        path: '/Consumer/Order/index'
                    })
                }
            });
        } else {
            ElMessage.error(res.msg)
        }
    })
}
// 跳转到菜品详情
const toShopItemDetail = (key) => {
    // map -> json 需要先转object
    let mapObj = Object.create(null);
    for (let[k,v] of data.order) {
        mapObj[k] = v;
    }
    const mapJson = JSON.stringify(mapObj)
    // 路由跳转
    router.push({
        path: '/Consumer/shopItemDetail',
        query: {
            shopItemInfo: JSON.stringify(data.shopItemList[key]),
            order: mapJson,
            totalAmount: data.totalAmount
        }
    })
}
const handleClick = (tab: TabsPaneContext, event: Event) => {
    // console.log(tab, event)
}
</script>

<style scoped>

</style>
