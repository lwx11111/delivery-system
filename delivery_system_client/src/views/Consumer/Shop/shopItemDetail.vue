<template>
    <el-dialog v-model="data.showDialog"
               destroy-on-close
               width="95%"
               top="5vh">
        <!--物品图片-->
        <el-row class="div-class">
            <el-image class="el-image"
                      style="margin-left:500px; height: 300px; width: 300px"
                      :src="data.shopItemInfo.picture">
            </el-image>
        </el-row>
        <!--物品描述-->
        <el-card>
            <el-row>
                <h1>{{data.shopItemInfo.name}}</h1>
            </el-row>
            <el-row>
                <el-col style="color: red" :span="8">
                    <h1>{{data.shopItemInfo.price}}￥</h1>
                </el-col>
                <!--价格和加购-->
                <el-col style="padding-top: 13px" :span="8">
                    <el-row>
                        <el-col :span="3">
                            <el-icon size="30px"
                                     v-if="data.order.has(data.shopItemInfo.id)"
                                     @click="subCart(data.shopItemInfo.id)">
                                <Remove />
                            </el-icon>
                        </el-col>
                        <el-col :span="3"
                                style="width: 60px;">
                            {{data.order.get(data.shopItemInfo.id)}}
                        </el-col>
                        <el-col :span="3">
                            <el-icon size="30px"
                                     @click="addCart(data.shopItemInfo.id)">
                                <CirclePlus />
                            </el-icon>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </el-card>
        <!--物品内容-->
        <el-card style="margin-top: 10px">
            <el-tabs v-model="data.tabName" class="demo-tabs" @tab-click="handleClick">
                <el-tab-pane label="详情" name="detail">
                    <el-row>
                        主料: {{data.shopItemInfo.mainIngredient}}
                    </el-row>
                    <el-row>
                        辅料: {{data.shopItemInfo.viceIngredient}}
                    </el-row>
                </el-tab-pane>
            </el-tabs>
        </el-card>
        <!--购物车-->
        <el-card style="margin-top: 10px; border: 1px solid gold ">
            <div>
                总金额：<span style="color: red" v-text="data.totalAmount + '￥'"></span>
            </div>
        </el-card>
    </el-dialog>

</template>

<script lang="ts" setup>
import { reactive, onMounted, defineProps } from 'vue'
import { useStore } from "vuex";
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from "element-plus";
import ApiShopItem from '@/api/Shop/api_shop_item.js'
import { CirclePlus, Remove } from '@element-plus/icons-vue'

const store = useStore();
const router = useRouter();
const route = useRoute();

// Data
const data = reactive({
    showDialog: false,
    // 物品信息
    shopItemInfo: {
        categoryName: "",
        description: "",
        id: "",
        name: "",
        params: "",
        picture: "",
        price: 0,
        shopId: ""
    },
    // 总价
    totalAmount: 0,
    // 订单 key为shopItemId, value为数量
    order: new Map(),
    // el-tab 选中的name
    tabName: 'detail',
})

// Props 只读
const props = defineProps({
    // 订单 key为shopItemId, value为数量
    order:{
        type: Map,
        default: null,
        required: true
    },
    totalAmount: {
        type: Number,
        default: 0,
        required: true
    }
})

// Mounted
onMounted(() => {
    // json -> map
    // let mapJson = route.query.order;
    // const obj = JSON.parse(<string>mapJson)
    // let map = new Map();
    // for (let k of Object.keys(obj)) {
    //     map.set(k,obj[k]);
    // }
    // data.order = map
    // data.shopItemInfo = JSON.parse(<string>route.query.shopItemInfo)
    // data.totalAmount = parseFloat(<string>route.query.totalAmount)
    //
    // console.log(data.totalAmount)
})

// Methods
const init = (id) => {
    // 赋值
    data.shopItemInfo.id = id;
    data.order = props.order;
    data.totalAmount = props.totalAmount;
    // 获取物品信息
    getShopItemById();
    // 界面显示
    data.showDialog = true;
}

const getShopItemById = () => {
    ApiShopItem.sel4shopitem(data.shopItemInfo.id).then(res => {
        console.log(res)
        if (res.code === 200){
            data.shopItemInfo = res.data;
        } else {
            ElMessage.error(res.msg)
        }
    })
}

// 添加物品到购物车
const addCart = (id) => {
    // 维护order值
    if (data.order.has(id)){
        data.order.set(id, data.order.get(id) + 1)
    } else {
        data.order.set(id, 1)
    }
    //总价
    data.totalAmount += data.shopItemInfo.price
    setOrderInfo();
}
// 减少物品到购物车
const subCart = (id) => {
    // 维护order值
    if (data.order.has(id)) {
        if ((data.order.get(id) - 1) === 0){
            data.order.delete(id)
        } else {
            data.order.set(id, data.order.get(id) - 1)
        }
        //总价
        data.totalAmount -= data.shopItemInfo.price
    }
    setOrderInfo();
}


const handleClick = (tab: TabsPaneContext, event: Event) => {
    console.log(tab, event)
}

/**
 * 通知父组件更新order和totalAmount
 */
const emit = defineEmits(['setOrderInfo'])
const setOrderInfo = () => {
    emit('setOrderInfo',data.order, data.totalAmount)
}

//暴露state和play方法
defineExpose({
    init,
});
</script>

<style scoped>
.div-class {
    position: relative;
}

.div-class .el-image {
    width: 100%;
    height: 20%;
}
</style>
