<template>
    <!--标题-->
    <el-row>
        <h2>购物车</h2>
    </el-row>
    <div v-for="(item, key) in data.shoppingCarts">
        <el-card style="margin-top: 10px">
            <!--头部店铺信息-->
            <el-row style="margin-bottom: 10px"
                    @click="toShopDetail(key)">
                <el-col :span="4"
                        style="margin-right: 5px">
                    <el-image src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"></el-image>
                </el-col>
                <el-col :span="8">
                    <h1>{{item.shopName}}</h1>
                </el-col>
            </el-row>
            <el-divider></el-divider>
            <!-- 物品列表 -->
            <div v-for="(it,i) in item.carts"
                 @click="toShopDetail(key)">
                <el-row style="margin-bottom: 10px">
                    <el-col :span="6"
                            style="margin-right: 5px">
                        <el-image src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"></el-image>
                    </el-col>
                    <el-col :span="4">
                        {{it.shopItemName}}
                    </el-col>
                    <el-col :span="4">
                        数量：{{it.amount}}
                    </el-col>
                    <el-col :span="4">
                        价格：{{it.price}}￥
                    </el-col>
                </el-row>
            </div>
            <!--操作-->
            <el-row>
                <el-button>去结算</el-button>
            </el-row>
        </el-card>
    </div>
</template>

<script lang="ts" setup>
import { reactive, onMounted } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import ApiShop from '@/api/Shop/api_shop.js'
import ApiCart from '@/api/Shop/api_cart.js'
import {ElMessage} from "element-plus";

const store = useStore();
const router = useRouter()

// Data
const data = reactive({
    //用户信息
    user: {
        id: '1',
        name: 'lwx'
    },
    // 购物车信息
    shoppingCarts:[
        {
            carts:[
                {
                    amount: 1,
                    id: "1",
                    price: 1,
                    shopId:"1",
                    shopItemId: "1",
                    shopItemName: "物品1",
                    shopName: "店铺1",
                }
            ],
            shopId:"1",
            shopName:"店铺1",
        },
    ]
})

// Mounted
onMounted(() => {
    // listShopWithShopItemByUserId()
    listCartsByUserId();
})

// Methods
const listCartsByUserId = () =>{
    if (data.user.id === null) {
        ElMessage.error('请先登录')
        return
    }
    const params = {
        userId: data.user.id,
    }
    ApiCart.listCartsByUserId(params).then(res => {
        console.log(res)
        if (res.code === 200) {
            data.shoppingCarts = res.data
        } else {
            ElMessage.error(res.message)
        }
    })
}

const toShopDetail = (key) => {
    router.push({
        path: '/Consumer/Shop/shopDetail',
        query: {
            shopId: data.shoppingCarts[key].shopId
        }
    })
}
const listShopWithShopItemByUserId = () => {
    if (data.user.id === null) {
        ElMessage.error('请先登录')
        return
    }
    const params = {
        userId: data.user.id,
        type: 0
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
