<template>
    <div>
        <!--外卖首页信息和地址-->
        <el-row>
            <el-col :span="24"
                style="background: #DAA520; height: 50px">
                <h1>分类店铺</h1>
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
        <!--筛选栏-->
        <ScreeningList @get-screening-index="getScreeningIndex"></ScreeningList>
        <!--商品信息-->
        <ShopCardList :shop-list="data.shopList"></ShopCardList>
    </div>
</template>

<script lang="ts" setup>
import { reactive, onMounted, watch } from 'vue'
import { useStore } from "vuex";
import { useRouter, useRoute } from 'vue-router'
import ScreeningList from './components/screeningList.vue'
import ShopCardList from "./components/shopCardList.vue";
import { Search } from '@element-plus/icons-vue'
import ApiShop from '@/api/Shop/api_shop.js'

const store = useStore();
const router = useRouter()
const route = useRoute()

// Data
const data = reactive({
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
        pageSize: 10
    },
})

// Mounted
onMounted(() => {
    getShopList();
})

// Methods
const getShopList = () => {
    ApiShop.listShopsByCategoryId(data.params).then(res => {
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

// Watch
// 解决路由参数变化时，页面不刷新的问题
watch(route, (to, from) => {
    router.go(0)
})

</script>

<style scoped>

</style>
