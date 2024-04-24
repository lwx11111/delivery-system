<!-- 提交订单确认页面 -->
<template>
    <el-dialog v-model="data.dialogVisible">
        <!--地址信息-->
        <el-card>
            <el-row style="width: 100%" @click="chooseAddress()">
                <el-col :span="20">
                    <h4>{{data.addressData.detailAddress}}</h4>
                </el-col>
                <el-col :span="4">
                    <h4><el-icon><ArrowRight /></el-icon></h4>
                </el-col>
            </el-row>
            <el-row style="width: 100%">
                <el-col :span="6">
                    {{data.addressData.contacts}}
                </el-col>
                <el-col :span="6">
                    {{data.addressData.phone}}
                </el-col>
            </el-row>
            <!--预计时间-->
            <el-row style="margin-top: 10px">
                <el-col :span="6">
                    预计送达时间：
                </el-col>
                <el-col :span="6">
                    {{data.orderInfo.expectedTime}}
                </el-col>
            </el-row>
        </el-card>

        <!--订单信息-->
        <el-card style="margin-top: 10px">
            <!--头部店铺信息-->
            <el-row style="margin-bottom: 10px">
                <el-col :span="8">
                    <h1>{{data.shop.name}}</h1>
                </el-col>
            </el-row>
            <!-- 物品列表 -->
            <div v-for="(item, index) in data.orderInfo.orderItems">
                <el-row style="margin-bottom: 10px">
                    <el-col :span="6"
                            style="margin-right: 5px">
                        <el-image :src="item.shopItem.picture"></el-image>
                    </el-col>
                    <el-col :span="6">
                        {{item.shopItem.name}}
                    </el-col>
                    <el-col :span="5">
                        数量：{{item.amount}}
                    </el-col>
                    <el-col style="color:red;" :span="5">
                        价格：{{item.shopItem.price}}￥
                    </el-col>
                </el-row>
            </div>
            <!--餐具和备注  -->
            <el-row style="margin-top: 10px">
                <el-col :span="12">
                    <el-form-item label="餐具数量">
                        <el-select v-model="data.orderInfo.tableware"
                                   placeholder="餐具选择">
                            <el-option label="需要" value="1"></el-option>
                            <el-option label="不需要" value="0"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="备注">
                        <el-input type="textarea"
                                  placeholder="请输入备注"
                                  v-model="data.orderInfo.remark">
                        </el-input>
                    </el-form-item>
                </el-col>
            </el-row>

<!--            <el-row style="margin-top: 10px">-->
<!--                <el-col :span="17"></el-col>-->
<!--                <el-col :span="5">-->
<!--                    打包费：<span style="color: red"> {{data.orderInfo.packingCharges}}￥</span>-->
<!--                </el-col>-->
<!--            </el-row>-->
            <el-row style="margin-top: 10px">
                <el-col :span="17"></el-col>
                <el-col :span="5">
                    配送费：<span style="color: red"> {{data.orderInfo.deliveryCharge}}￥</span>
                </el-col>
            </el-row>
            <el-row style="margin-top: 10px">
                <el-col :span="17"></el-col>
                <el-col :span="5">
                    总价：<span style="color: red">{{data.orderInfo.totalCharge}}￥</span>
                </el-col>
            </el-row>
        </el-card>
        <!--提交订单按钮-->
        <el-card style="border: 1px solid gold;margin-top: 10px">
            <el-row>
                <el-col :span="18"></el-col>
                <el-col :span="3">
                    <el-button @click="submitOrder()"
                               style="background: gold">
                        提交订单
                    </el-button>
                </el-col>
            </el-row>
        </el-card>
    </el-dialog>
    <!--选择地址-->
    <ChooseAddress @modify-address="modifyAddress" ref="chooseAddressRef"></ChooseAddress>
</template>

<script lang="ts" setup>
import { reactive, watch, onMounted, ref, defineProps } from 'vue'
import { useStore } from "vuex";
import { useRouter, useRoute } from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";
import ApiAddress from '@/api/Address/api_address.js'
import ApiOrder from '@/api/Order/api_orderinfo.js'
import UserStorage from '@/cache/userStorage.js'
import AddressStorage from '@/cache/addressStorage.js'
import ChooseAddress from "../../Address/components/chooseAddress.vue";
const store = useStore();
const router = useRouter();
const route = useRoute();

// Data
const data = reactive({
    // 用户地址信息
    addressData: AddressStorage.getAddress(),
    // 商家地州信息ID
    shopAddressId: '',
    dialogVisible: false,
    // 店铺信息
    shop: {
        id: '',
        userId : UserStorage.getUserId(),
        name : '',
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
        picture : ''
    },

    // 发送的订单信息
    orderInfo: {
        id:'',
        // 菜品信息
        orderItems:[],
        userId: UserStorage.getUserId(),
        shopId: '',
        deliveryRiderId: '',
        packingCharges: '',
        deliveryCharge: '',
        totalCharge: '',
        expectedTime: '2023-01-01 00:00:00',
        location: '1',
        deliveryService: '1',
        orderTime: '2023-01-01 00:00:00',
        status: '0',
        paymentMethod: '1',
        remark: '',
        tableware: '1',
    },
})

// Props
const props = defineProps({

})

// Mounted
onMounted(() => {

})

// Methods


/**
 * 修改地址回调函数
 * @param address
 */
const modifyAddress = (address) => {
    data.addressData = address
    ElMessage.success({
        message: "修改成功"
    })
}


const chooseAddressRef = ref();
const chooseAddress = () => {
    chooseAddressRef.value.init();
}
/**
 * 计算预计送达时间
 */
const calculateExpectedTime = () => {
    const params = {
        shopAddressId: data.shopAddressId,
        userAddressId: data.addressData.id
    }
    // 获取预计送达时间
    ApiOrder.getExpectedTime(params).then(res => {
        console.log(res)
        if (res.code === 200){
            data.orderInfo.expectedTime = res.data;
        } else {
            ElMessage.error(res.msg)
        }
    })
}



/**
 * 提交订单
 */
const submitOrder = () => {
    // 地址信息
    data.orderInfo.shopAddressId = data.shopAddressId;
    data.orderInfo.userAddressId = data.addressData.id;
    console.log(data.orderInfo)
    ApiOrder.add4orderinfo(data.orderInfo).then(res => {
        console.log(res)
        if (res.code === 200){
            const params = {
                messageId: res.data
            }
            console.log(params)
            // 主键获取
            ApiOrder.getOrderIdByMessageId(params).then(res => {
                console.log(res)
               if (res.code === 200 && res.data !== null){
                   data.orderInfo.id = res.data;
                   // 去支付
                   router.push({
                       path: '/Consumer/Order/orderPay',
                       query: {
                           orderId: data.orderInfo.id
                       }
                   })
               }
            });
        } else {
            ElMessage.error(res.msg)
        }
    })
}
const init = (shop, orderInfo, shopAddressId) => {
    // 界面初始化接收参数
    data.shop = shop;
    data.orderInfo = orderInfo;
    data.shopAddressId = shopAddressId;
    data.dialogVisible  = true;
    calculateExpectedTime();
}

//暴露方法
defineExpose({
    init,
});

</script>

<style scoped>

</style>
