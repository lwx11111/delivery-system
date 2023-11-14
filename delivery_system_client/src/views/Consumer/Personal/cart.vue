<template>
    <div class="common-layout">
        <el-container>
            <el-header>
                <el-menu
                    :default-active="activeIndex"
                    class="el-menu-demo"
                    mode="horizontal"
                    @select="handleSelect">
                    <el-menu-item index="1">外卖</el-menu-item>
                    <el-menu-item index="1">会员</el-menu-item>
                    <el-menu-item index="1">订单</el-menu-item>
                    <el-menu-item index="1">我的</el-menu-item>
                </el-menu>
            </el-header>
            <el-container>
                <el-aside width="200px"></el-aside>
                <el-container>
                    <el-main style="background: #FFFAFA">
                        <!--标题-->
                        <el-row>
                            <h2>购物车</h2>
                        </el-row>
                        <!--订单信息-->
                        <div v-for="(item, key) in data.shoppingCartList"
                             @click="toOrderDetail(key)">
                            <el-card style="margin-top: 10px">
                                <!--头部店铺信息-->
                                <el-row style="margin-bottom: 10px">
                                    <el-col :span="4"
                                            style="border: 1px solid red; margin-right: 5px">
                                        <el-image src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"></el-image>
                                    </el-col>
                                    <el-col :span="8"
                                            style="border: 1px solid red">
                                        <!--                                        {{item.shop.name}}-->
                                        111
                                    </el-col>
                                    <el-col :span="4"
                                            style="border: 1px solid red">
                                        {{item.status}}
                                    </el-col>
                                </el-row>
                                <!-- 物品列表 -->
                                <el-row style="margin-bottom: 10px">
                                    <el-col :span="7"
                                            style="border: 1px solid red; margin-right: 5px">
                                        <el-image src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"></el-image>
                                    </el-col>
                                    <el-col :span="10"
                                            style="border: 1px solid red">
                                        <!--                                        {{item.itemInfo.name}}-->
                                        111
                                    </el-col>
                                    <el-col :span="7">
                                        {{item.totalCharge}}
                                    </el-col>
                                </el-row>
                                <!--操作-->
                                <el-row>
                                    <el-button>再来一单</el-button>
                                    <el-button>评价</el-button>
                                </el-row>
                            </el-card>
                        </div>
                    </el-main>
                </el-container>
                <el-aside width="200px"></el-aside>
            </el-container>
        </el-container>
        <el-footer style="border: 1px solid red">Footer</el-footer>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";
import ApiOrder from '../../../api/api_order.js'

const store = useStore();
const router = useRouter()
import {CirclePlus, Remove, Search} from '@element-plus/icons-vue'
// Data
const data = reactive({
    //用户信息
    user: {
        id: '1',
        name: 'lwx'
    },
    // 购物车
    cart: [{
        shop : {
            id: '',
            userId : '',
            name : 'name',
            province : '',
            county : '',
            location : '',
            score : '',
            salesVolume : '',
            minPrice : '',
            deliveryCharge : '',
            safetyFile : '',
            openTime : '',
            closeTime : '',
            status : '',
            description : '',
            itemCategory : '',
            picture : '',
            shopItem:[]
        }}
    ],




})

// Mounted
onMounted(() => {

})

// Methods

const toUserDetail = () => {
    router.push({
        path: '/Consumer/Personal/userDetail',
        query: {
            userId: data.user.id
        }
    })
}

const toCart = () => {
    router.push({
        path: '/Consumer/Personal/cart',
        query: {
            userId: data.user.id
        }
    })
}

const toCollection = () => {
    router.push({
        path: '/Consumer/Personal/collection',
        query: {
            userId: data.user.id
        }
    })
}

</script>

<style scoped>

</style>
