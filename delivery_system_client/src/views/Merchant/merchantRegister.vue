<template>
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

                    <el-input v-model="data.item.location"
                              class="input-item"
                              placeholder="具体地址">
                    </el-input>

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
                        <el-col :span="24">
                            <el-form-item
                                label="店铺图片"
                                prop="picture">
                                <MinioUpload ref="uploadRef"
                                             :file-list="data.fileList1"
                                             @uploadCallback="uploadCallbackPicture"
                                             :limit="1">
                                </MinioUpload>
                            </el-form-item>
                        </el-col>

                    </el-row>

                    <el-row>
                        <el-col :span="24">
                            <el-form-item
                                label="档案图片"
                                prop="safetyFile">
                                <MinioUpload ref="uploadRef"
                                             :file-list="data.fileList2"
                                             @uploadCallback="uploadCallbackSafetyFile"
                                             :limit="1">
                                </MinioUpload>
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
</template>

<script lang="ts" setup>
import {reactive, onMounted, ref } from 'vue';
import ApiCategory from '@/api/Shop/api_category.js'
import ApiShop from '@/api/Shop/api_shop.js'
import ApiDict from '@/api/Common/api_sysdict.js'
import { useStore } from "vuex";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import MinioUpload from "../components/MinioUpload.vue";

const store = useStore();
const router = useRouter()

// Data
const data = reactive({
    // 图片
    fileList1: [],
    fileList2: [],
    // todo
    provinces: [
        {
            name:"北京市",
            areaId:'1'
        },
    ],
    cities: [],
    counties: [],
    // 类型选择
    categoryTree: [],
    item: {},
    rules: {
        // province: [
        //     { required: true, message: '省不能为空', trigger: 'blur' }
        // ],
        // city: [
        //     { required: true, message: '市不能为空', trigger: 'blur' }
        // ],
        // county: [
        //     { required: true, message: '县不能为空', trigger: 'blur' }
        // ],

        minPrice: [
            { required: true, message: '起送价格不能为空', trigger: 'blur' }
        ],
        deliveryCharge: [
            { required: true, message: '配送费不能为空', trigger: 'blur' }
        ],
        safetyFile: [
            { required: true, message: '安全档案图片不能为空', trigger: 'blur' }
        ],
        // openTime: [
        //     { required: true, message: '开门时间不能为空', trigger: 'blur' }
        // ],
        // closeTime: [
        //     { required: true, message: '关门时间不能为空', trigger: 'blur' }
        // ],
        categoryIds: [
            { required: true, message: '商铺类型不能为空', trigger: 'blur' }
        ],
        picture: [
            { required: true, message: '店铺图片路径不能为空', trigger: 'blur' }
        ],
    },
})


// Mounted
const getProvinces = () => {
    // 省市县信息
    ApiDict.getProvinces().then(res => {
        console.log(res)
        data.provinces = res.data
    })
}

const getCity = () => {
    if (data.item.province === undefined){
        return;
    }
    // 省市县信息
    ApiDict.getCity(data.item.province).then(res => {
        console.log(res)
        data.cities = res.data
    })
}

const getCounty = () => {
    if (data.item.city === undefined){
        return;
    }
    // 省市县信息
    ApiDict.getCounty(data.item.city).then(res => {
        console.log(res)
        data.counties = res.data
    })
}
onMounted(() => {
    getProvinces();
    // 用户信息
    data.item.userId = localStorage.getItem("userId");
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
                ElMessage.error('具体地址不能为空');
                return;
            }
            // 表单验证通过
            ApiShop.add4shop(data.item).then(res => {
                if (res.code === 200){
                    ElMessage({
                        message: '添加成功',
                        type: 'success',
                    })
                    router.push('/Merchant/Shop/index')
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

const resetForm = () => {
    // 重置按钮
    itemForm.value.resetFields();
}

const uploadCallbackPicture = (response, url) => {
    data.item.picture = url
    // 图片
    let item1 = {
        url: data.item.picture
    }
    data.fileList1 = [];
    data.fileList1.push(item1)

}
const uploadCallbackSafetyFile = (response, url) => {
    data.item.safetyFile = url
    let item2 = {
        url: data.item.safetyFile
    }
    data.fileList2 = [];
    data.fileList2.push(item2)
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
    height: 170%;
    .input-item {
        align-items: center;
        margin-bottom: 20px;
        height: 35px;
        border-radius: 20px;
    }
}
</style>
