<template>
    <el-dialog v-model="data.showDialog">
        <div style="align-items: center; text-align: center; margin-bottom: 10px">
            <div v-if="data.address.detailAddress !== '' ">
                选中的地址：{{data.address.detailAddress}}
            </div>
            <el-button v-else type="warning" style="width: 80%;" @click="mapChoose()">
                选择收货地址<el-icon><Location /></el-icon>
            </el-button>
            <el-button v-if="data.type === 'update'" type="warning" style="width: 80%;" @click="mapChoose()">
                修改收货地址<el-icon><Location /></el-icon>
            </el-button>
        </div>

        <el-form :model="data.address"
                 :rules="data.rules"
                 ref="addressForm">
            <el-form-item label="门牌号" prop="houseNumber">
                <el-input v-model="data.address.houseNumber"></el-input>
            </el-form-item>
            <el-form-item label="联系人" prop="contacts">
                <el-input v-model="data.address.contacts"></el-input>
            </el-form-item>
            <el-form-item label="联系电话" prop="phone">
                <el-input v-model="data.address.phone"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="addAddress()" style="width: 100%;">保存</el-button>
            </el-form-item>
        </el-form>

        <el-button v-if="data.type === 'update'" type="danger" style="width: 100%;" @click="deleteAddress()">
            删除
        </el-button>
    </el-dialog>
</template>

<script lang="ts" setup>
import { reactive, onMounted, ref, onActivated } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import Api from '@/api/Address/api_address.js'
import UserStorage from '@/cache/userStorage.js';
const store = useStore();
const router = useRouter()
import {ElMessage, ElMessageBox} from "element-plus";
// Data
const data = reactive({
    showDialog: false,
    // 区分update和add
    type:'',
    //地址信息
    address: {
        id: '',
        accountId: UserStorage.getUserId(),
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
        houseNumber: '',
    },
    rules: {
        houseNumber: [
            { required: true, message: '详细地址不能为空', trigger: 'blur' }
        ],
        contacts: [
            { required: true, message: '联系人不能为空', trigger: 'blur' }
        ],
        phone: [
            { required: true, message: '联系电话不能为空', trigger: 'blur' }
        ],
    },

})

// Mounted
onMounted(() => {

})

onActivated(() => {
    console.log('onShow')
    // 从地图选择的地址
    const position = JSON.parse(sessionStorage.getItem('position'))
    if (position) {
        console.log(position)
        data.address.detailAddress = position.detailAddress;
        data.address.provinceName = position.provinceName;
        data.address.provinceId = position.provinceId;
        data.address.cityName = position.cityName;
        data.address.cityId = position.cityId;
        data.address.countyName = position.countyName;
        data.address.countyId = position.countyId;
        data.address.longitude = position.longitude;
        data.address.latitude = position.latitude;
        sessionStorage.removeItem('position')
    }
})

// Methods
const mapChoose = () => {
    router.push({
        path: '/Address/mapChoose',
    })
}

const addressForm = ref();
const addAddress = () => {
    addressForm.value.validate((valid, fields) => {
        if (valid) {
            if (data.address.detailAddress === ''){
                ElMessage({
                    message: '请选择收货地址',
                    type: 'warning',
                })
                return;
            }
            console.log(data.address)
            if (data.type === 'update') {
                Api.update4address(data.address.id, data.address).then(res => {
                    console.log(res);
                    data.showDialog = false;
                    ElMessage({
                        message: '修改成功',
                        type: 'success',
                    })
                })
                return;
            } else {
                Api.add4address(data.address).then(res => {
                    console.log(res);
                    data.showDialog = false;
                    ElMessage({
                        message: '保存成功',
                        type: 'success',
                    })
                })
            }

        } else {
            ElMessage({
                message: '请校验',
                type: 'warning',
            })
        }
    })
}

const deleteAddress = () => {
    ElMessageBox.confirm(
        '确认删除？',
        '警告',
        {
            confirmButtonText: '确认删除',
            cancelButtonText: '取消',
            type: 'warning',
        }
    ).then(() => {
        Api.del4address(data.address.id).then(res => {
            console.log(res);
            data.showDialog = false;
            ElMessage({
                message: '删除成功',
                type: 'success',
            })
        })
    }).catch(() => {
        ElMessage({
            type: 'info',
            message: '已取消删除',
        })
    })
}

const init = (address) => {
    console.log(address)
    if (address !== undefined) {
        data.type = 'update';
        data.address = address;
    }
    data.showDialog = true;
}

//暴露state和play方法
defineExpose({
    init,
});
</script>

<style scoped>

</style>
