<template>
    <el-row style="margin-bottom: 10px">
        <h5 @click="addAddress()">添加地址</h5>
    </el-row>
    <el-card>
        <el-row v-for="(item, index) in data.addressData">
            <el-row style="width: 100%">
                <el-col :span="15">
                    <h4>{{item.detailAddress}}</h4>
                </el-col>
                <el-col :span="4" style="margin-top: 20px">
                    <el-icon @click="updateAddress(index)"><EditPen /></el-icon>
                </el-col>
            </el-row>
            <el-row style="width: 100%">
                <el-col :span="6">
                    {{item.contacts}}
                </el-col>
                <el-col :span="6">
                    {{item.phone}}
                </el-col>
            </el-row>
            <el-divider></el-divider>
        </el-row>

    </el-card>
    <AddAddress ref="addressRef"></AddAddress>

</template>

<script lang="ts" setup>
import { reactive, onMounted, ref } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import Api from '@/api/Address/api_address.js'
import UserStorage from '@/cache/userStorage.js';
import AddAddress from "./addAddress.vue";
const store = useStore();
const router = useRouter()

// Data
const data = reactive({
    // 地址信息
    addressData: [
        {
            id: '',
            accountId: '',
            longitude: '',
            latitude: '',
            cityName: '',
            cityId: '',
            provinceName: '',
            provinceId: '',
            countyName: '',
            countyId: '',
            detailAddress: '',
            contacts: '',
            phone: '',
        }
    ],
    params: {
        accountId: UserStorage.getUserId(),
    },

})

// Mounted
onMounted(() => {
    listAddressData();
})

// Methods
const addressRef = ref();
const addAddress = () => {
    addressRef.value.init();
}

const updateAddress = (index) => {
    addressRef.value.init(data.addressData[index]);
}
const listAddressData = () => {
    const params = {
        accountId: UserStorage.getUserId(),
    }
    Api.selpage4address(data.params).then(res => {
        console.log(res);
        data.addressData = res.data.records;
    })
}


</script>

<style scoped>

</style>
