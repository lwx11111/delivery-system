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
                        <!--外卖首页信息和地址-->
                        <el-row>
                            <el-col
                                    :span="24"
                                    style="background: #DAA520; height: 50px">
                                外卖 地址 消息
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
                        <!--菜单栏-->
                        <el-tabs v-model="data.tabName" class="demo-tabs" @tab-click="handleClick">
                            <el-tab-pane label="全部订单" name="allOrder"></el-tab-pane>
                            <el-tab-pane label="待评价" name="toComment">Role</el-tab-pane>
                            <el-tab-pane label="退款" name="refund">Role</el-tab-pane>
                        </el-tabs>
                        <!--订单信息-->
                        <div v-for="(item, key) in data.orderList"
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
import { Search } from '@element-plus/icons-vue'
// Data
const data = reactive({
    // 订单列表
    orderList: [],
    // 菜单栏名称
    tabName: 'allOrder',
    // 查询参数
    params: {
        status: '',
        pageIndex: 1,
        pageSize: 10
    },
})
// 解构抛出 直接使用
// const { type} = toRefs(data)

// Mounted
onMounted(() => {
    getOrderList(0);
})

// Methods

// 查询订单列表
const getOrderList = (status) => {
    data.params.status = status;
    console.log(status)
    ApiOrder.selpage4order(data.params).then(res => {
        console.log(res)
        if (res.code === 200){
            data.orderList = res.data.records;
        }
    })
}

// 打开订单详情界面
const toOrderDetail = (key) => {
    router.push({
        path: '/Consumer/Order/orderDetail',
        query: {
            orderId: data.orderList[key].id
        }
    })
}

// 菜单栏点击事件
const handleClick = (tab: TabsPaneContext, event: Event) => {
    switch (tab.props.name){
        case 'allOrder' :
            console.log("lwx");
            getOrderList(0);
            break;
        case 'toComment':
            getOrderList(1);
            break;
        case 'refund':
            getOrderList(2);
            break;
    }
}


</script>

<style scoped>

</style>
