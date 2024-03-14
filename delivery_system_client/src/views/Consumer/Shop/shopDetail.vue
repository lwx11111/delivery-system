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
                                        <el-image :src="item.picture"></el-image>
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
                                        <el-row v-if="data.order.has(item.id)">
                                            <el-col :span="3"
                                                    style="width: 60px;"
                                                    v-if="data.order.has(item.id)">
                                                <el-icon size="30px"
                                                         @click="subCart(key)">
                                                    <Remove />
                                                </el-icon>
                                            </el-col>
                                            <el-col :span="3"
                                                    style="width: 60px;">
                                                {{data.order.get(item.id)}}
                                            </el-col>
                                            <el-col :span="3">
                                                <el-icon size="30px"
                                                         style="width: 60px;"
                                                         @click="addCart(key)">
                                                    <CirclePlus />
                                                </el-icon>
                                            </el-col>
                                        </el-row>
                                        <!--解决按钮显示 card宽度变大问题-->
                                        <el-row v-else>
                                            <el-col :span="3">
                                                <el-icon size="30px"
                                                         style="width: 60px;"
                                                         @click="addCart(key)">
                                                    <CirclePlus />
                                                </el-icon>
                                            </el-col>
                                            <el-col :span="3"
                                                    style="width: 60px;">
                                            </el-col>
                                            <el-col :span="3"
                                                    style="width: 60px;">
                                            </el-col>
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
        <!--评价信息-->
        <el-tab-pane label="评价" name="shopComment">
            <CommentList :score="data.shop.score"></CommentList>
        </el-tab-pane>
        <!--档案信息-->
        <el-tab-pane label="店铺" name="shopInfo">
            <el-card>
                <el-row>
                    <el-col :span="2"><el-icon><Location /></el-icon></el-col>
                    <el-col :span="10">{{data.address.detailAddress}}</el-col>
                </el-row>
                <el-divider></el-divider>
                <!--解决组件快于数据获取-->
                <el-row v-if="data.shop.safetyFile !== '' ">
                    <el-col :span="3">
                        安全档案图片
                    </el-col>
                    <el-col :span="5">
                        <MinioUpload :url="data.shop.safetyFile"
                                     :disabled="true">
                        </MinioUpload>
                    </el-col>

                </el-row>
            </el-card>

            <el-card style="margin-top: 10px">
                配送时间：{{data.shop.openTime}}-{{data.shop.closeTime}}
            </el-card>
        </el-tab-pane>
    </el-tabs>

    <!--菜品详情组件-->
    <ShopItemDetail @set-order-info="setOrderInfo"
                    :order="data.order"
                    :total-amount="data.totalAmount"
                    ref="shopItemDetailRef">
    </ShopItemDetail>

    <!--订单确认组件-->
    <OrderConfirm ref="orderConfirmDialog"></OrderConfirm>

</template>

<script lang="ts" setup>
import { reactive, watch, onMounted, ref } from 'vue'
import { useStore } from "vuex";
import { useRouter, useRoute } from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";
import ApiShop from '@/api/Shop/api_shop.js'
import ApiShopItem from '@/api/Shop/api_shop_item.js'
import ApiShopItemCategory from '@/api/Shop/api_shopItemCategory.js'
import ApiCollection from '@/api/Shop/api_collection.js'
import ApiCart from '@/api/Shop/api_cart.js'
import ApiAddress from '@/api/Address/api_address.js'
import UserStorage from '@/cache/userStorage.js'
import { Search, CirclePlus, Remove } from '@element-plus/icons-vue'
import ShopItemDetail from "./shopItemDetail.vue";
import CommentList from "../Comment/commentList.vue";
import OrderConfirm from "../Order/components/orderConfirm.vue";
import MinioUpload from "../../components/MinioUpload.vue";
const store = useStore();
const router = useRouter();
const route = useRoute();

// Data
const data = reactive({
    // 地址信息
    address:{},
    // 收藏信息
    isCollected: false,
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
        picture : '',
        duration:'',
        distanceKm:''
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
    getAddressByShopId();
    getShopItemList();
    getShopItemCategoryList();
    isHaveCollection();
})

// Methods

const getAddressByShopId = () => {
    ApiAddress.getAddressByShopId(data.shop.id).then(res => {
        console.log(res)
        if (res.code === 200){
            data.address = res.data;
        }
    })
}
/**
 * 打开订单确认Dialog
 * 传参
 */
const orderConfirmDialog = ref();
const showOrderConfirm = () => {
    orderConfirmDialog.value.init(data.shop, data.orderInfo);
}

const cancelCollection = () => {
    const param = {
        shopId: data.shop.id,
        userId: UserStorage.getUserId(),
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
        userId: UserStorage.getUserId(),
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
        userId: UserStorage.getUserId(),
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
            shopItemId: key,
            amount: value
        })
    })
    // 封装参数
    let params = {
        shopId: data.shop.id,
        userId: UserStorage.getUserId(),
        shopItems: shopItems
    }
    console.log(params)
    ApiCart.add4cart(params).then(res => {
        if (res.code === 200){
            ElMessageBox.alert('加入购物车成功', '提示', {
                confirmButtonText: '去支付',
                callback: action => {
                    router.push({
                        path: '/Consumer/Personal/shoppingCart'
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
    // 是否选择物品
    if (data.order.size === 0){
        ElMessage.error('请选择物品')
        return
    }

    // 封装参数
    // data.orderInfo.packingCharges = data.shop.packingCharges
    data.orderInfo.deliveryCharge = data.shop.deliveryCharge
    data.orderInfo.shopId = data.shop.id
    data.orderInfo.totalCharge = data.totalAmount + data.orderInfo.deliveryCharge;
    // 订单初始状态1
    data.orderInfo.status = '1';

    // 需要先清空订单物品信息，防止物品重复
    data.orderInfo.orderItems = [];

    // 物品信息
    data.order.forEach((value, key) => {
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

    // 打开订单信息界面
    showOrderConfirm();

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
    // router.go(0)
})
</script>

<style scoped>

</style>
