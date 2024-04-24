<!-- 订单确认界面的选择地址Dialog -->
<template>
    <el-dialog v-model="data.dialogVisible">
        <h4 style="align-items: center; text-align: center">选择收货地址</h4>
        <div>
            <el-radio-group v-model="data.radio" type="vertical">
                <el-radio :label="index" style="width: 100%; height: 120px" size="large" v-for="(item,index) in data.addressList">
                    <AddressCard  :address="item" :is-update=true></AddressCard>
                </el-radio>
            </el-radio-group>
        </div>
        <el-button style="width: 100%; margin-bottom: 10px; margin-top: 10px" type="warning" @click="submit()">确定</el-button>
        <!---1px为了冲齐-->
        <el-button style="width: 100%; margin-left: -1px" @click="addAddress()" type="primary">新增收货地址</el-button>
    </el-dialog>
    <!--添加地址-->
    <AddAddress ref="addressRef"></AddAddress>
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
import {User} from "@element-plus/icons-vue";
import AddressCard from "./addressCard.vue";
import AddAddress from "../addAddress.vue";
const store = useStore();
const router = useRouter();
const route = useRoute();

// Data
const data = reactive({
    // 用户地址信息
    addressList:[],
    dialogVisible: false,
    // 单选框
    radio: ''

})


// Props
const props = defineProps({

})

// Mounted
onMounted(() => {

})

// Methods

/**
 * 添加地址
 */
const addressRef = ref();
const addAddress = () => {
    addressRef.value.init();
}

/**
 * 选中地址
 */
const emit = defineEmits(['modifyAddress'])
const submit = () => {
    emit('modifyAddress',data.addressList[data.radio]);
    data.dialogVisible = false;
}

const init = () => {
    data.dialogVisible = true;
    // 获取用户id
    let id = UserStorage.getUserId();
    // 获取地址列表
    const params = {
        accountId: id
    }
    ApiAddress.selpage4address(params).then(res => {
        if (res.code === 200){
            data.addressList = res.data.records;
        }
    })
}

//暴露方法
defineExpose({
    init,
});

</script>

<style scoped>

</style>
