<template>
    <keep-alive>
        <div class="back">
            <div class="loginBack">
                <el-card class="login-form-content">
                    <h1 style="text-align: center">商铺注册</h1>
                    <el-form :model="data.item"
                             :rules="data.rules"
                             ref="itemForm"
                             label-width="100px">
                        <el-row v-if="false">
                            <el-col :span="6">
                                <el-form-item
                                    label="userId"
                                    prop="userId">
                                    <el-input v-model="data.item.userId">
                                    </el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-input v-model="data.item.name"
                                  class="input-item"
                                  placeholder="店铺名">
                            <template #prefix>
                                <el-icon style="color: gold"><Avatar /></el-icon>
                            </template>
                        </el-input>

                        <el-input v-model="data.item.description"
                                  class="input-item"
                                  style="margin-bottom: 30px;"
                                  placeholder="店铺描述"
                                  type="textarea">
                        </el-input>

                        <div>
                            <el-row style="margin-bottom: 10px">
                                <el-button v-if="data.address.detailAddress !== ''" type="warning"
                                           style="width: 100%;" @click="mapChoose()">
                                    修改收货地址<el-icon><Location /></el-icon>
                                </el-button>
                                <el-button  v-else type="warning" style="width: 100%;" @click="mapChoose()">
                                    选择收货地址<el-icon><Location /></el-icon>
                                </el-button>
                            </el-row>
                        </div>

                        <div v-if="data.address.detailAddress !== '' ">
                            <el-row>
                                <el-col :span="3">
                                    <el-text tag="b">选中的地址：</el-text>
                                </el-col>
                                <el-col :span="21" style="text-align: left">
                                    {{data.address.detailAddress}}
                                </el-col>
                            </el-row>

                            <el-row>
                                <el-col :span="3">
                                    <el-text tag="b">省:</el-text>
                                </el-col>
                                <el-col :span="21" style="text-align: left">
                                    {{data.address.provinceName}}
                                </el-col>
                            </el-row>

                            <el-row v-if="data.address.detailAddress !== '' ">
                                <el-col :span="3">
                                    <el-text tag="b">市:</el-text>
                                </el-col>
                                <el-col :span="21" style="text-align: left">
                                    {{data.address.cityName}}
                                </el-col>
                            </el-row>

                            <el-row v-if="data.address.detailAddress !== '' ">
                                <el-col :span="3">
                                    <el-text tag="b">县:</el-text>
                                </el-col>
                                <el-col :span="21" style="text-align: left">
                                    {{data.address.countyName}}
                                </el-col>
                            </el-row>
                        </div>

                        <el-row>
                            <el-col :span="12">
                                <el-form-item
                                    label="起送价格"
                                    class="input-item"
                                    prop="minPrice">
                                    <el-input-number v-model="data.item.minPrice"
                                                     placeholder="起送价格"
                                                     :precision="2"
                                                     :step="1">
                                    </el-input-number>
                                </el-form-item>

                            </el-col>
                            <el-col :span="12">
                                <el-form-item
                                    label="配送费"
                                    class="input-item"
                                    prop="deliveryCharge">
                                    <el-input-number v-model="data.item.deliveryCharge"
                                                     placeholder="配送费"
                                                     :precision="2"
                                                     :step="1">
                                    </el-input-number>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="12">
                                <el-form-item
                                    label="店铺图片"
                                    prop="picture">
                                    <MinioUploadNew key1="picture" @getUrl="getUrl">
                                    </MinioUploadNew>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item
                                    label="档案图片"
                                    prop="safetyFile">
                                    <MinioUploadNew key1="safetyFile" @getUrl="getUrl">
                                    </MinioUploadNew>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="12">
                                <el-form-item
                                    label="商铺类型"
                                    prop="categoryId">
                                    <el-tree-select v-model="data.item.categoryIds"
                                                    :data="data.categoryTree"
                                                    :render-after-expand="false"
                                                    show-checkbox
                                                    multiple>
                                    </el-tree-select>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-form-item>
                            <el-button type="primary"
                                       @click="submitForm()">
                                保存
                            </el-button>
                            <el-button @click="resetForm()">
                                重置
                            </el-button>
                        </el-form-item>
                    </el-form>
                </el-card>
            </div>
        </div>
    </keep-alive>
</template>

<script lang="ts" setup>
import {reactive, onMounted, ref, onActivated} from 'vue';
import ApiCategory from '@/api/Shop/api_category.js'
import ApiShop from '@/api/Shop/api_shop.js'
import ApiAddress from '@/api/Address/api_address.js'
import { useStore } from "vuex";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import UserStorage from '@/cache/userStorage.js';
import MinioUploadNew from "../components/MinioUpload.vue";

const store = useStore();
const router = useRouter()

// Data
const data = reactive({
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

    // 类型选择
    categoryTree: [],
    item: {},
    rules: {
        minPrice: [
            { required: true, message: '起送价格不能为空', trigger: 'blur' }
        ],
        deliveryCharge: [
            { required: true, message: '配送费不能为空', trigger: 'blur' }
        ],
        safetyFile: [
            { required: true, message: '安全档案图片不能为空', trigger: 'blur' }
        ],
        openTime: [
            { required: true, message: '开门时间不能为空', trigger: 'blur' }
        ],
        closeTime: [
            { required: true, message: '关门时间不能为空', trigger: 'blur' }
        ],
        categoryIds: [
            { required: true, message: '商铺类型不能为空', trigger: 'blur' }
        ],
        picture: [
            { required: true, message: '店铺图片路径不能为空', trigger: 'blur' }
        ],
    },
})

onActivated(() => {
    console.log("Activated")
    // 从地图选择的地址
    const position = JSON.parse(sessionStorage.getItem('position'))
    console.log(position)
    if (position) {
        console.log(position.detailAddress)
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
        // 向shop赋值
        data.item.province = data.address.provinceId;
        data.item.city = data.address.cityId;
        data.item.countyId = data.address.countyId;
        data.item.location = data.address.detailAddress;
    }
})

// Mounted
onMounted(() => {
    // 用户信息
    data.item.userId = UserStorage.getUserId();
    // 店铺类型
    ApiCategory.listTreeCategory().then(res => {
        console.log(res)
        if (res.code === 200){
            data.categoryTree = res.data;
        } else {
            ElMessage({
                message: '获取数据失败，请重试',
                type: 'warning',
            })
            return;
        }
    })
})

/**
 * 跳转图片
 */
const mapChoose = () => {
    router.push({
        path: '/Address/mapChoose',
    })
}

/**
 * 获取url
 * @param url
 * @param key1
 * @param key2
 */
const getUrl = (url, key1, key2) => {
    if (key1 === 'picture'){
        data.item.picture = url
    } else if (key1 === 'safetyFile'){
        data.item.safetyFile = url
    }
}

// 提交表单的方法
const itemForm = ref();
const submitForm = () => {
    // 表单验证
    itemForm.value.validate(valid => {
        if (valid) {
            if(data.item.name === ''){
                ElMessage.error('店铺名不能为空');
                return;
            }
            if (data.item.location === ''){
                ElMessage({
                    message: '请选择收货地址',
                    type: 'warning',
                })
                return;
            }
            // 表单验证通过
            ApiShop.add4shop(data.item).then(res => {
                console.log(res)
                if (res.code === 200){
                    data.item.shopId = res.data;
                    if (saveShopAddress()){
                        ElMessage({
                            message: '添加成功',
                            type: 'success',
                        })
                        router.push('/Merchant/Shop/index')
                    }
                } else {
                    ElMessage({
                        message: '添加失败，请重试',
                        type: 'warning',
                    })
                    return;
                }
            })
        }
    })
}

const saveShopAddress = () => {
    if (data.address.detailAddress === ''){
        ElMessage({
            message: '请选择地址',
            type: 'warning',
        })
        return false;
    }

    console.log(UserStorage.getUser())

    data.address.shopId =  data.item.shopId;
    data.address.accountId = UserStorage.getUserId();
    data.address.contacts = UserStorage.getUser().accountName;
    // data.address.phone = UserStorage.getUser().phone;

    ApiAddress.add4address(data.address).then(res => {
        console.log(res);
        if (res.code !== 200){
            ElMessage({
                message: '保存地址失败',
                type: 'warning',
            })
            return false;
        }
    })
    return true;
}

const resetForm = () => {
    // 重置按钮
    itemForm.value.resetFields();
}

</script>

<style scoped>
@media screen and (max-width: 1500px){
    /* 当屏幕小于1500px的时候 id为bg的元素 进行改变 */
    .back{
        background-size: contain;
        margin: 0;
        padding: 0;
    }
}

.loginBack{
    width:1000px;
    height:500px;
    MARGIN-RIGHT: auto;
    MARGIN-LEFT: auto;
}

.back {
    background-image: url('/src/assets/backgroud.jpg');
    background-size: cover;
    /* 背景图片不重复 */
    background-repeat: no-repeat;
    /* 最小宽度为100% */
    min-width: 100%;
    /* 最小高度为100vh    vh: 就等于 视窗的高度  1vh = 视窗的高度的1%*/
    min-height: 100vh;
    margin: 0;
    padding: 0;
    border: 1px white solid;
}

.login-form-content {
    text-align: center;
    margin-top: 5%;
    margin-left: 17%;
    width: 80%;
    height: 130%;
    .input-item {
        align-items: center;
        margin-bottom: 20px;
        height: 35px;
        border-radius: 20px;
    }
}
</style>
