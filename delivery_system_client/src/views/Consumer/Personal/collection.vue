<template>
  <!--标题-->
    <el-row>
        <h2>我的收藏</h2>
    </el-row>
  <!--商铺信息-->
    <ShopCardList :shop-list="data.shops"></ShopCardList>
</template>

<script lang="ts" setup>
import { reactive, onMounted } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import ApiCollection from "@/api/Shop/api_collection";
const store = useStore();
const router = useRouter()
import UserStorage from '@/cache/userStorage.js';
import ShopCardList from "../Shop/components/shopCardList.vue";
import {ElMessage} from "element-plus";
// Data
const data = reactive({
    //用户信息
    user: {
        id: UserStorage.getUserId(),

    },
    // 店铺信息
    shops: []
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
    }
    ApiCollection.listShopByCollection(params).then((res) => {
        console.log(res)
        if (res.code === 200) {
            data.shops = res.data
        } else {
            ElMessage.error(res.message)
        }
    })
}

</script>

<style scoped>

</style>
