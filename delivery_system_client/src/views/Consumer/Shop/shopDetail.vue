<template>
    <!--店铺信息和图片-->
    <el-card style="margin-bottom: 10px">
        <el-row>
            <el-col :span="20">
                <h1>{{data.shop.name}}</h1>
            </el-col>
            <el-col :span="4">
                <el-image :src="data.shop.picture"></el-image>
            </el-col>
        </el-row>
        <el-row>
            <el-button v-if="!data.isCollected"
                       @click="addCollection()">加入收藏</el-button>
            <el-button v-else
                       @click="cancelCollection()">取消收藏</el-button>
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
        <!--选择物品界面-->
        <el-tab-pane label="点菜" name="order">
            <el-row>
<!--                &lt;!&ndash; 分类列表&ndash;&gt;-->
<!--                <el-col :span="4"-->
<!--                        style="border-right: 1px solid red; " >-->
<!--                    <el-scrollbar height="400px" style="padding: 10px">-->
<!--                        <el-space direction="vertical">-->
<!--                            <el-card style="width: 90%; border: 1px solid red"-->
<!--                                     v-for="item in data.shopItemCategoryList">-->
<!--                                {{item.name}}-->
<!--                            </el-card>-->
<!--                        </el-space>-->
<!--                    </el-scrollbar>-->
<!--                </el-col>-->

                <!--菜品列表-->
                <el-col :span="24" style="text-align: center">
                    <!--todo 高度-->
                    <el-scrollbar height="500px" style="padding: 10px">
                        <el-space direction="vertical">
                            <el-card v-for="(item, key) in data.shopItemList">
                                <el-row>
                                    <!--图片-->
                                    <el-col :span="11"
                                            @click="toShopItemDetail(key)"
                                            style="margin-right: 5px">
                                        <el-image src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"></el-image>
                                    </el-col>
                                    <!--名字 价格-->
                                    <el-col :span="12">
                                        <el-row @click="toShopItemDetail(key)"
                                                style="margin-bottom: 5px">
                                                <span style="font-size: 20px;font-weight: bold;">
                                                    {{item.name}}
                                                </span>
                                        </el-row>
                                        <el-row style="color: red; margin-bottom: 5px">
                                            {{item.price}}￥
                                        </el-row>
                                        <!--减少添加按钮-->
                                        <el-row>
                                            <el-icon size="30px"
                                                     v-if="data.order.has(item.id)"
                                                     @click="subCart(key)">
                                                <Remove />
                                            </el-icon>
                                            {{data.order.get(item.id)}}
                                            <el-icon size="30px"
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

            <!--购物车-->
            <el-card style="border: 1px solid gold ">
                <el-row>
                    <el-col :span="18">
                        总金额：<span v-text="data.totalAmount"></span>
                    </el-col>
                    <el-col :span="3">
                        <el-button @click="saveCart()"
                                   style="background: cornflowerblue">
                            加入购物车
                        </el-button>
                    </el-col>
                    <el-col :span="3">
                        <el-button @click="submitOrder()"
                                   style="background: gold">
                            提交订单
                        </el-button>
                    </el-col>
                </el-row>
            </el-card>
        </el-tab-pane>
        <el-tab-pane label="评价" name="shopComment">
            <CommentList :shop-id="data.shop.id"></CommentList>
        </el-tab-pane>
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

    <!--菜品详情组件-->
    <ShopItemDetail @set-order-info="setOrderInfo"
                    :order="data.order"
                    :total-amount="data.totalAmount"
                    ref="shopItemDetailRef">
    </ShopItemDetail>
</template>

<script lang="ts" setup>
import { reactive, watch, onMounted, ref } from 'vue'
import { useStore } from "vuex";
import { useRouter, useRoute } from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";
import ApiShop from '@/api/Shop/api_shop.js'
import ApiShopItem from '@/api/Shop/api_shop_item.js'
import ApiShopItemCategory from '@/api/Shop/api_shopItemCategory.js'
import ApiOrder from '@/api/Order/api_orderinfo.js'
import ApiCollection from '@/api/Shop/api_collection.js'
import ApiCart from '@/api/Shop/api_cart.js'
import { Search, CirclePlus, Remove } from '@element-plus/icons-vue'
import ShopItemDetail from "./shopItemDetail.vue";
import CommentList from "../Comment/commentList.vue";
const store = useStore();
const router = useRouter();
const route = useRoute();

// Data
const data = reactive({
    // 收藏信息
    isCollected: false,
    // 店铺信息
    shop: {
        id: '',
        userId : localStorage.getItem('userId'),
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
        userId: localStorage.getItem('userId'),
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
    // 查询参数
    params: {
        shopId: '',
        pageIndex: 1,
        pageSize: 10
    },
})

// Mounted
onMounted(() => {
    data.shop.id = route.query.shopId;
    data.params.shopId = route.query.shopId;
    getShop();
    getShopItemList();
    getShopItemCategoryList();
    isHaveCollection();
})

// Methods

const cancelCollection = () => {
    const param = {
        shopId: data.shop.id,
        userId: localStorage.getItem('userId'),
    }
    ApiCollection.cancelCollection(param).then(res => {
        if (res.code === 200 ){
            ElMessage.success('取消收藏成功')
            location.reload()
        } else {
            ElMessage.error(res.msg)
        }
    })
}

const isHaveCollection = () => {
    const param = {
        shopId: data.shop.id,
        userId: localStorage.getItem('userId'),
    }
    ApiCollection.isHaveCollection(param).then(res => {
        if (res.code === 200){
            data.isCollected = res.data;
        } else {
            ElMessage.error(res.msg)
        }
    })
}

const addCollection = () => {
    const param = {
        shopId: data.shop.id,
        userId: localStorage.getItem('userId'),
    }
    ApiCollection.add4collection(param).then(res => {
        if (res.code === 200){
            ElMessage.success('收藏成功')
            location.reload()
        } else {
            ElMessage.error(res.msg)
        }
    })
}

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
        console.log(res)
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
    if (data.order.has(id)) {
        if ((data.order.get(id) - 1) === 0){
            data.order.delete(id)
        } else {
            data.order.set(id, data.order.get(id) - 1)
        }
        //总价
        data.totalAmount -= data.shopItemList[key].price
    }
}
// 加入购物车
const saveCart = () => {
    // 是否选择物品
    if (data.order.size === 0){
        ElMessage.error('请选择物品')
        return
    }
    // 物品信息
    let shopItems = [];
    data.order.forEach((value, key) => {
        shopItems.push({
            shopItemId: data.shopItemList[key].id,
            amount: value
        })
    })
    // 封装参数
    let params = {
        shopId: data.shop.id,
        userId: localStorage.getItem('userId'),
        shopItems: shopItems
    }
    console.log(params)
    ApiCart.add4cart(params).then(res => {
        if (res.code === 200){
            ElMessageBox.alert('加入购物车成功', '提示', {
                confirmButtonText: '去支付',
                callback: action => {
                    router.push({
                        path: '/Consumer/Personal/cart'
                    })
                }
            });
        } else {
            ElMessage.error(res.msg)
        }
    })
}
// 提交订单
const submitOrder = () => {
    // 封装参数
    // data.orderInfo.packingCharges = data.shop.packingCharges
    data.orderInfo.deliveryCharge = data.shop.deliveryCharge
    data.orderInfo.shopId = data.shop.id
    data.orderInfo.totalCharge = data.totalAmount
    // 订单初始状态1
    data.orderInfo.status = '1';
    // 物品信息
    data.order.forEach((value, key) => {
        console.log(key)
        console.log(value)
        console.log(data.shopItemList[key])
        let shopItem = null;
        // key是id
        for (let i = 0; i < data.shopItemList.length; i++){
            if (data.shopItemList[i].id === key){
                shopItem = data.shopItemList[i];
                break;
            }
        }
        data.orderInfo.orderItems.push({
            shopItem: shopItem,
            amount: value
        })
    })
    console.log(data.orderInfo)
    ApiOrder.add4orderinfo(data.orderInfo).then(res => {
        if (res.code === 200){
            console.log(res.data)
            ElMessageBox.alert('创建订单成功', '提示', {
                confirmButtonText: '确定',
                callback: action => {
                    router.push({
                        path: '/Consumer/Order/orderPay',
                        query: {
                            orderId: res.data
                        }
                    })
                }
            });
        } else {
            ElMessage.error(res.msg)
        }
    })
}

// 打开菜品详情Dialog
const shopItemDetailRef = ref();
const toShopItemDetail = (key) => {
    // 物品ID
    const id = data.shopItemList[key].id
    console.log(key)
    shopItemDetailRef.value.init(id);

    // // map -> json 需要先转object
    // let mapObj = Object.create(null);
    // for (let[k,v] of data.order) {
    //     mapObj[k] = v;
    // }
    // const mapJson = JSON.stringify(mapObj)
    // // 路由跳转
    // router.push({
    //     path: '/Consumer/shopItemDetail',
    //     query: {
    //         shopItemInfo: JSON.stringify(data.shopItemList[key]),
    //         order: mapJson,
    //         totalAmount: data.totalAmount
    //     }
    // })
}

/**
 * 回调函数
 * @param order
 * @param totalAmount
 */
const setOrderInfo = (order,totalAmount) => {
    data.order = order;
    data.totalAmount = totalAmount;
}
const handleClick = (tab: TabsPaneContext, event: Event) => {
    // console.log(tab, event)
}

// Watch
// 解决路由参数变化时，页面不刷新的问题
watch(route, (to, from) => {
    router.go(0)
})
</script>

<style scoped>

</style>
