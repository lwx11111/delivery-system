<template>
  <!--标题-->
    <el-row>
        <h2>我的收藏</h2>
    </el-row>
  <!--商铺信息-->
    <ShopItemList :shops="data.shops"></ShopItemList>
  <!--操作-->
    <el-row>
        <el-button>再来一单</el-button>
        <el-button>评价</el-button>
    </el-row>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import ApiShop from '@/api/Shop/api_shop.js'
const store = useStore();
const router = useRouter()

import ShopItemList from "../Shop/components/shopItemList.vue";
import {ElMessage} from "element-plus";
// Data
const data = reactive({
    //用户信息
    user: {
        id: '1',
        name: 'lwx'
    },
    // 店铺信息
    shops: [
        {
            shop:{
                id: '',
                name : '',
                picture : '',
            },
            shopItems: [
                {
                    description: "1",
                    name: "物品1",
                    picture: "1",
                    price: 1,
                    amount: 1,

                },
            ]
        },
    ]
})

// Mounted
onMounted(() => {
    getCollections()
})

// Methods
const getCollections = () => {
    if (data.user.id === null) {
        ElMessage.error('请先登录')
        return
    }
    const params = {
        userId: data.user.id,
        type : 1
    }
    ApiShop.listShopWithShopItemByUserId(params).then((res) => {
        if (res.code === 200) {
            data.shops = res.data
            console.log(data.shops)
        } else {
            ElMessage.error(res.message)
        }
    })
}

</script>

<style scoped>

</style>
