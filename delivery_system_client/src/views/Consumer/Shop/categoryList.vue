<template>
    <div class="common-layout">
        <!--分类标题-->
        <el-row>
            <el-col
                :span="24"
                style="background: #DAA520; height: 50px">
                分类商铺
            </el-col>
        </el-row>
        <!-- 搜索框-->
        <el-row style="margin-top: 10px; margin-bottom: 10px;">
            <el-col :span="12">
                <el-input
                    style="width: 490px;"
                    placeholder="Please input"
                    class="input-with-select">
                    <template #prepend>
                        <el-button :icon="Search" />
                    </template>
                    <template #append>
                        <div>搜索</div>
                    </template>
                </el-input>
            </el-col>
        </el-row>
        <!--分类 todo-->
        <CategoryInfo :is-parent="data.params.isParentId" :category-id="data.params.categoryId"
                      :is-query-parent="false"></CategoryInfo>
        <!--筛选栏-->
        <ScreeningList></ScreeningList>
        <!--商品信息-->
        <ShopCardList :shop-list="data.shopList"></ShopCardList>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter, useRoute } from 'vue-router'

import ApiShop from '../../../api/api_shop.js'
const store = useStore();
const router = useRouter()
const route = useRoute()
import ScreeningList from './components/screeningList.vue'
import ShopCardList from "./components/shopCardList.vue";
import { Search } from '@element-plus/icons-vue'
import CategoryInfo from "./components/categoryInfo.vue";

// Data
const data = reactive({
    // 店铺信息
    shopList: [],
    // 查询参数
    params: {
        // 是否是父分类
        isParentId: '',
        categoryId: '',
        pageNum: 1,
        pageSize: 10
    },
})

// Mounted
onMounted(() => {
    data.params.isParentId = route.query.isParentId;
    data.params.categoryId = route.query.categoryId;
    getShopList();
})

// Methods
const getShopList = () => {
    ApiShop.listShopsByCategoryId(data.params).then(res => {
        if (res.code === 200){
            data.shopList = res.data.records;
        }
    })
}

</script>

<style scoped>

</style>
