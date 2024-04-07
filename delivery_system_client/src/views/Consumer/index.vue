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
                        <div @click="getShopList">搜索</div>
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
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs, inject } from 'vue'
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
        screening: '',
        name:'',
        categoryId: '',
        pageIndex: 1,
        pageSize: 10
    },
})

// Mounted
onMounted(() => {
    getAddressData();
})

// Methods

/**
 * 获取地址信息
 */
const getAddressData = () => {
    const params = {
        accountId: UserStorage.getUserId(),
    }
    ApiAddress.selpage4address(params).then(res => {
        console.log(res)
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
    delete data.address.shopId;
    delete data.address.params;
    data.params.address = JSON.stringify(data.address) ;
    console.log(data.params)
    ApiShop.selpage4shop(data.params).then(res => {
        console.log(res)
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
</script>

<style scoped>

</style>
