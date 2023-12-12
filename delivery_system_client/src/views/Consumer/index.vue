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
        <!--分类信息-->
        <CategoryInfo></CategoryInfo>
        <!--筛选栏-->
        <ScreeningList @get-screening-index="getScreeningIndex"></ScreeningList>
        <!--商品信息-->
        <ShopCardList :shop-list="data.shopList"></ShopCardList>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import ApiShop from '@/api/Shop/api_shop.js'
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
        screening: '',
        name:'',
        categoryId: '',
        pageIndex: 1,
        pageSize: 10
    },
})

// Mounted
onMounted(() => {
    getShopList();
})

// Methods
/**
 * 店铺列表
 */
const getShopList = () => {
    ApiShop.selpage4shop(data.params).then(res => {
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
