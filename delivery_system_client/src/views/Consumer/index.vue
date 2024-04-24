<template>
    <div>
        <!--外卖首页信息和地址-->
        <el-row style="text-align: center;background: #DAA520; height: 50px" >
            <el-col :span="2" style="padding-top: 10px; text-align: right">
                <el-text style="color:black;" tag="b">配送至：</el-text>
            </el-col>
            <el-col :span="6" style="padding-top: 12px; text-align: left">
                <el-text truncated v-if="data.address.detailAddress !== undefined">{{data.address.detailAddress}}</el-text>
            </el-col>
            <el-col :span="8" style="padding-top: 12px;">
                <el-text style="color:black;" size="large" tag="b">
                    外卖</el-text>
            </el-col>
        </el-row>
        <!-- 搜索框-->
        <el-row style="margin-top: 10px; margin-bottom: 10px;">
            <el-col :span="24">
                <el-input v-model="data.params.name"
                          placeholder="请输入">
                    <template #prepend>
                        <el-button :icon="Search" />
                    </template>
                    <template #append>
                        <div @click="getShopListBySearch">搜索</div>
                    </template>
                </el-input>
            </el-col>
        </el-row>
        <!--分类信息-->
        <CategoryInfo></CategoryInfo>
        <!--筛选栏-->
        <ScreeningList @get-screening-index="getScreeningIndex"></ScreeningList>
        <!--商品信息-->
        <ShopCardList :shop-list="data.shopList"></ShopCardList>
        <el-empty v-if="!data.isHaveData" :image-size="50" description="没有数据了" />
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs, inject, onUnmounted, watch } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import ApiShop from '@/api/Shop/api_shop.js'
import ApiAddress from '@/api/Address/api_address.js'
import { Search } from '@element-plus/icons-vue'
import ShopCardList from "./Shop/components/shopCardList.vue";
import ScreeningList from "./Shop/components/screeningList.vue";
import CategoryInfo from "./Shop/components/categoryInfo.vue";
import UserStorage from '@/cache/userStorage.js';
import AddressStorage from '@/cache/addressStorage.js';
import {ElMessage, ElMessageBox} from "element-plus";

const store = useStore();
const router = useRouter()
// Data
const data = reactive({
    //地址信息
    address: {
        id: '',
        accountId: UserStorage.getUserId(),
        longitude: '',
        latitude: '',
        cityName: '',
        cityId: '',
        provinceName: '',
        provinceId: '',
        countyName: '',
        countyId: '',
        detailAddress: '',
        contacts: '',
        phone: '',
        houseNumber: '',
    },
    // 店铺信息
    shopList: [],
    // 查询参数
    params: {
        status: 1,
        screening: '',
        name:'',
        categoryId: '',
        pageIndex: 1,
        pageSize: 5
    },
    // 是否还有数据
    isHaveData: true,
})

// Mounted
onMounted(() => {
    // 初始化数据
    data.shopList = [];
    data.isHaveData = true;
    // 挂载dom后注册onBottom事件
    window.addEventListener('scroll', onBottom)
    getAddressData();
})

// 页面销毁移除scroll事件
onUnmounted(() => {
    window.removeEventListener('scroll', onBottom)
})

// Methods

/**
 * 监听触底
 */
const onBottom = () => {
    const scrollHeight = document.documentElement.scrollHeight // 可滚动区域的高
    const scrollTop = document.documentElement.scrollTop // 已经滚动区域的高
    const clientHeight = document.documentElement.clientHeight // 可视区高度
    // 以滚动高度 + 当前视口高度  >= 可滚动高度 = 触底
    if (clientHeight + scrollTop >= scrollHeight) {
        // 返回该页面时 没数据就会自动到第二页
        if (data.shopList.length !== 0){
            if (data.isHaveData){
                data.params.pageIndex = data.params.pageIndex + 1;
                getShopList();
            }
        }
    }
}

/**
 * 获取地址信息
 */
const getAddressData = () => {
    const params = {
        accountId: UserStorage.getUserId(),
    }
    ApiAddress.selpage4address(params).then(res => {
        if (res.code === 200){
            // 没有地址信息
            if (res.data.records.length === 0){
                ElMessageBox.confirm(
                    '请先添加地址信息',
                    '提示',
                    {
                        confirmButtonText: '确认',
                        cancelButtonText: '取消',
                        type: 'success',
                    }
                ).then(() => {
                    router.push({
                        path: '/Consumer/Address/index',
                    })
                }).catch(() => {
                    ElMessage({
                        type: 'success',
                        message: '取消你也得添加嘻嘻',
                    })
                    router.push({
                        path: '/Consumer/Address/index',
                    })
                })
            } else {
                data.address = res.data.records[0];
                AddressStorage.setAddress(data.address);
                getShopList();
            }
        }
    })
}

/**
 * 店铺列表
 */
const getShopList = () => {
    // 删除key
    delete data.address.shopId;
    delete data.address.params;
    data.params.address = JSON.stringify(data.address) ;
    ApiShop.selpage4shop(data.params).then(res => {
        if (res.code === 200){
            if (res.data.records.length === 0){
                data.isHaveData = false;
                return;
            }
            for ( let i = 0; i < res.data.records.length; i++ ){
                data.shopList.push(res.data.records[i]);
            }
        }
    })
}

/**
 * 搜索
 */
const getShopListBySearch = () => {
    delete data.address.shopId;
    delete data.address.params;
    data.params.address = JSON.stringify(data.address) ;
    ApiShop.selpage4shop(data.params).then(res => {
        if (res.code === 200){
            data.shopList = res.data.records;
            data.isHaveData = false;
            return;
        }
    })
}

/**
 * 特殊筛选
 * @param index 筛选条件的下标
 */
const getScreeningIndex = (index) => {
    data.shopList = [];
    data.params.screening = index;

    delete data.address.shopId;
    delete data.address.params;
    data.params.address = JSON.stringify(data.address) ;
    ApiShop.selpage4shop(data.params).then(res => {
        if (res.code === 200){
            data.shopList = res.data.records;
            data.isHaveData = false;
            return;
        }
    })
}
</script>

<style scoped>

</style>
