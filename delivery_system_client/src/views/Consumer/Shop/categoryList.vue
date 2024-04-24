<template>
    <div>
        <!--外卖首页信息和地址-->
        <el-row style="text-align: center;background: #DAA520; height: 50px; margin-bottom: 10px" >
            <el-col :span="2" style="padding-top: 10px; text-align: right">
                <el-text style="color:black;" tag="b">配送至：</el-text>
            </el-col>
            <el-col :span="6" style="padding-top: 12px; text-align: left">
                <el-text truncated>{{data.address.detailAddress}}</el-text>
            </el-col>
            <el-col :span="8" style="padding-top: 12px;">
                <el-text style="color:black;" size="large" tag="b">
                    分类店铺</el-text>
            </el-col>
        </el-row>
        <!--商品信息-->
        <ShopCardList :shop-list="data.shopList"></ShopCardList>
    </div>
</template>

<script lang="ts" setup>
import { reactive, onMounted, onActivated, watch } from 'vue'
import { useStore } from "vuex";
import { useRouter, useRoute } from 'vue-router'
import ScreeningList from './components/screeningList.vue'
import ShopCardList from "./components/shopCardList.vue";
import { Search } from '@element-plus/icons-vue'
import ApiShop from '@/api/Shop/api_shop.js'
import AddressStorage from '@/cache/addressStorage.js';

const store = useStore();
const router = useRouter()
const route = useRoute()

// Data
const data = reactive({
    // 地址信息
    address:{},
    // 店铺信息
    shopList: [],
    // 查询参数
    params: {
        // 是否是父分类
        // 父子传值在mounted前就进行，所以要在setup时接受参数
        isParentId: route.query.isParentId,
        categoryId: route.query.categoryId,
        screening: '',
        name:'',
        pageNum: 1,
        pageSize: 10,
        address: JSON.stringify(AddressStorage.getAddress()),
    },
})

// Mounted
onMounted(() => {
    data.address = AddressStorage.getAddress();
    data.params.isParentId = route.query.isParentId
    data.params.categoryId = route.query.categoryId
    getShopList();
})

// 每次打开界面时，刷新界面，否则只有组件初始化时才会刷新，路由也监听不到
// keepAlive 打开时用这个
onActivated(() => {

})

// Methods
const getShopList = () => {
    ApiShop.listShopsByCategoryId(data.params).then(res => {
        if (res.code === 200){
            data.shopList = res.data.records;
        }
    })
}

/**
 * 特殊筛选
 * @param index 筛选条件的下标
 */
const getScreeningIndex = (index) => {
    data.params.screening = index;
    getShopList();
}

// Watch
// 解决路由参数变化时，页面不刷新的问题
watch(route, (to, from) => {
    // router.go(0)
})

</script>

<style scoped>

</style>
