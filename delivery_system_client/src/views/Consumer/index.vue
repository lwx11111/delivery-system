<template>
    <div>
        <!--外卖首页信息和地址-->
        <el-row>
            <el-col
                :span="24"
                style="background: #DAA520; height: 50px">
                <h1>外卖</h1>
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
        <!--todo 分类-->
        <CategoryInfo></CategoryInfo>
        <!-- 筛选 todo 背景变色-->
        <ScreeningList></ScreeningList>
        <!--商品信息-->
        <ShopCardList :shop-list="data.shopList"></ShopCardList>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import ApiShop from '../../api/api_shop.js'
import { Search } from '@element-plus/icons-vue'
import ShopCardList from "./Shop/components/shopCardList.vue";
import ScreeningList from "./Shop/components/screeningList.vue";
import CategoryInfo from "./Shop/components/categoryInfo.vue";

const store = useStore();
const router = useRouter()
// Data
const data = reactive({
    // 店铺信息
    shopList: [],
    // 查询参数
    params: {
        screeningType: '',
        name:'',
        categoryId: '',
        pageIndex: 1,
        pageSize: 10
    },
})
// 解构抛出 直接使用
// const { type} = toRefs(data)

// Mounted
onMounted(() => {

    getShopList();
})

// Methods
/**
 * todo 根据筛选条件查询店铺
 * @param index
 */
const getShopListByScreening = (index) => {
    data.params.screeningType = index;
}
const handleSelect = (key, keyPath) => {
    console.log(key, keyPath);
}

/**
 * 店铺列表
 */
const getShopList = () => {
    ApiShop.selpage4shop(data.params).then(res => {
        // console.log(res)
        if (res.code === 200){
            data.shopList = res.data.records;
        }
    })
}

/**
 * 跳转到店铺详情
 * @param key
 */
const toShopDetail = (key) => {
    router.push({
        path: '/Consumer/Shop/shopDetail',
        query: {
            shopId: data.shopList[key].id
        }
    })
}
</script>

<style scoped>
.scrollbar-flex-content {
    display: flex;
}
.scrollbar-demo-item {
    flex-shrink: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100px;
    height: 50px;
    margin: 10px;
    text-align: center;
    border-radius: 4px;
    background: white;
    color: var(--el-color-danger);
}
</style>
