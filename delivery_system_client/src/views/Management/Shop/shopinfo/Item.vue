<template>
    <el-dialog v-model="data.showDialog"
               destroy-on-close
               width="95%"
               top="5vh"
               style="height: 95%"
               :title="data.operateTitle">
        <el-card style="border: 1px solid gold;"
                 class="box-card"
                 shadow="never">
            <el-form
                    :model="data.item"
                    :rules="data.rules"
                    ref="itemForm"
                    label-width="100px">
                <el-row>
                    <el-col :span="6">
                        <el-form-item
                                label="店铺名"
                                prop="name">
                            <el-input
                                    v-model="data.item.name"
                                    :disabled="data.disabled">
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item
                                label="店铺描述"
                                prop="description">
                            <el-input :disabled="data.disabled"
                                      v-model="data.item.description"
                                      type="textarea" />
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="6">
                        <el-form-item
                                label="具体地址"
                                prop="location">
                            <el-input v-model="data.item.location"
                                    :disabled="data.disabled">
                            </el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="6">
                        <el-form-item
                                label="起送价格"
                                prop="minPrice">
                            <el-input-number v-model="data.item.minPrice"
                                             :disabled="data.disabled"
                                             :precision="2"
                                             :step="1">-->
                            </el-input-number>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item
                                label="配送费"
                                prop="deliveryCharge">
                            <el-input-number v-model="data.item.deliveryCharge"
                                             :disabled="data.disabled"
                                             :precision="2" :step="1">
                            </el-input-number>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
<!--                    <el-col :span="6">-->
<!--                        <el-form-item-->
<!--                                label="开门时间"-->
<!--                                prop="openTime">-->
<!--                            <el-time-picker-->
<!--                                    :disabled="data.disabled"-->
<!--                                    format="HH:mm:ss"-->
<!--                                    value-format="HH:mm:ss"-->
<!--                                    v-model="data.item.openTime"-->
<!--                                    placeholder="请选择开门时间">-->
<!--                            </el-time-picker>-->
<!--                        </el-form-item>-->
<!--                    </el-col>-->
<!--                    <el-col :span="6">-->
<!--                        <el-form-item-->
<!--                                label="关门时间"-->
<!--                                prop="closeTime">-->
<!--                            <el-time-picker-->
<!--                                    :disabled="data.disabled"-->
<!--                                    format="HH:mm:ss"-->
<!--                                    value-format="HH:mm:ss"-->
<!--                                    v-model="data.item.closeTime"-->
<!--                                    placeholder="请选择开门时间">-->
<!--                            </el-time-picker>-->
<!--                        </el-form-item>-->
<!--                    </el-col>-->
                    <el-col :span="6">
                        <el-form-item label="状态">
                            <el-select v-model="data.item.status"
                                       :disabled="data.disabled"
                                       placeholder="请选择">
                                <el-option value="1" label="正常"></el-option>
                                <el-option value="0" label="关闭"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="6">
                        <el-form-item
                                label="店铺图片"
                                prop="picture"
                                label-width="150px">
                            <MinioUpload :disabled="type === 'detail'"
                                         key1="picture"
                                         :url="data.item.picture"
                                         @getUrl="getUrl">
                            </MinioUpload>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item
                                label="安全档案图片"
                                prop="safetyFile"
                                label-width="150px">
                            <MinioUpload :disabled="type === 'detail'"
                                         key1="safetyFile"
                                         :url="data.item.safetyFile"
                                         @getUrl="getUrl">
                            </MinioUpload>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="6">
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
                    <el-button
                            v-show="data.showBtn"
                            type="primary"
                            @click="submitForm('itemForm')">
                      保存
                    </el-button>
                    <el-button
                            v-show="data.showBtn"
                            @click="resetForm()">
                      重置
                    </el-button>
                    <el-button @click="back()">
                      返回
                    </el-button>
                </el-form-item>
              </el-form>
        </el-card>
    </el-dialog>

</template>
<script lang="ts" setup>
    import Api from '@/api/Shop/api_shop.js'
    import ApiCategory from '@/api/Shop/api_category.js'
    import { reactive, ref, onMounted, toRefs } from 'vue'
    import { useStore } from "vuex";
    import { useRouter } from 'vue-router'
    import {ElMessage, ElMessageBox} from "element-plus";
    import MinioUpload from "@/views/components/MinioUpload.vue";
    import UserStorage from '@/cache/userStorage.js';
    const store = useStore();
    const router = useRouter()

    // Data
    const data = reactive({
        // 图片
        fileList1: [],
        fileList2: [],
        show: true,
        // todo
        provinces: [
            {
                name:"北京市",
                id:'1'
            },
            {
                name: "天津市",
                id: "2"
            }
        ],
        counties: [
            {
                name:"北京市",
                id:'1'
            },
            {
                name: "天津市",
                id: "2"
            }
        ],
        operateTitle: '新增',
        type: '',
        showBtn: true,
        disabled: false,
        id: 0,
        item: {},
        params: {
            id: '',
            userId: '',
            name: '',
            province: '',
            county: '',
            location: '',
            score: '',
            salesVolume: '',
            minPrice: '',
            deliveryCharge: '',
            safetyFile: '',
            openTime: '',
            closeTime: '',
            status: '',
            description: '',
            itemCategory: '',
            picture: '',
            // 选择的类型
            categoryIds:[],
        },
        showDialog: false,
        // 类型选择
        categoryTree: [],
        rules: {
            name: [
                { required: true, message: '店铺名不能为空', trigger: 'blur' }
            ],
            location: [
                { required: true, message: '具体地址不能为空', trigger: 'blur' }
            ],
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
            // description: [
            //     { required: true, message: '店铺描述不能为空', trigger: 'blur' }
            // ],
            picture: [
                { required: true, message: '店铺图片路径不能为空', trigger: 'blur' }
            ]
        },
    })

    // Props
    const props = defineProps({
        //子组件接收父组件传递过来的值
        type: {
            type: String,
            default: '新增'
        },
    })

    // Mounted
    onMounted(() => {
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

    // Methods

    /**
     * 获取url
     * @param url
     * @param key1
     * @param key2
     */
    const getUrl = (url, key1, key2) => {
        console.log(url)
        if (key1 === 'picture'){
            data.item.picture = url
        } else if (key1 === 'safetyFile'){
            data.item.safetyFile = url
        }
    }

    const uploadCallbackPicture = (response, url) => {
        data.item.picture = url
    }
    const uploadCallbackSafetyFile = (response, url) => {
        data.item.safetyFile = url
    }

    const init = (id, type) => {
        // 界面初始化接收参数
        data.type = type;
        switch (data.type) {
            case 'add':
                data.operateTitle = '新增'
                data.showBtn = true
                data.disabled = false
                break
            case 'detail':
                data.operateTitle = '详情'
                data.showBtn = false
                data.disabled = true
                break
            case 'update':
                data.operateTitle = '修改'
                data.showBtn = true
                data.disabled = false
                break
        }
        if (data.type === 'detail'){
            data.show = true;
        }
        if (data.type === 'update' || data.type === 'add'){
            data.show = false;
        }
        // 获取数据
        if (data.type === 'detail' || data.type === 'update') {
            // ID校验
            if (id != null && id != '') {
                data.id = id
            } else {
                ElMessage({
                  message: '数据ID丢失，请重试',
                  type: 'warning',
                })
                return;
            }
            // 发送请求
            Api.sel4shop(data.id).then(res => {
                console.log(res)
                if (res.code === 200){
                    data.item = res.data;

                    // 界面显示
                    data.showDialog = true;
                } else {
                    ElMessage({
                      message: '获取数据失败，请重试',
                      type: 'warning',
                    })
                    return;
                }
            })
            // 图片
            let item1 = {
                url: data.item.picture
            }
            console.log(data.item.picture)
            data.fileList1 = [];
            data.fileList1.push(item1)
            let item2 = {
                url: data.item.safetyFile
            }
            data.fileList2 = [];
            data.fileList2.push(item2)
            console.log(data.fileList1)
        } else {
            // 界面显示
            data.showDialog = true;
        }

    }
    const back = () => {
        // 返回操作
        data.showDialog = false;
        location.reload()
    }

    // 表单ref
    const itemForm = ref();
    const submitForm = (formName) => {
        console.log(data.item.categoryIds)
        // 表单验证
        itemForm.value.validate((valid, fields) => {
            if (valid) {
                saveOrUpdate();
            } else {
                ElMessage({
                  message: '请校验',
                  type: 'warning',
                })
            }
        })
    }

    const resetForm = () => {
        // 重置按钮
        itemForm.value.resetFields();
    }

    const saveOrUpdate = () => {
        // 保存或更新操作
        if (data.type === 'update') {
            Api.update4shop(data.id, data.item).then(res => {
                if (res.code === 200){
                    ElMessage({
                      message: '修改成功',
                      type: 'success',
                    })
                    back()
                } else {
                    ElMessage({
                      message: '修改失败',
                      type: 'warning',
                    })
                }
            })
        } else if (data.type === 'add') {
            console.log(data.item)
            data.item.userId = UserStorage.getUserId();
            Api.add4shop(data.item).then(res => {
                console.log(res)
                if (res.code === 200){
                    ElMessage({
                      message: '保存成功',
                      type: 'success',
                    })
                    back();
                } else {
                    ElMessage({
                      message: '保存失败',
                      type: 'warning',
                    })
                }
            })
        }
    }

    //暴露state和play方法
    defineExpose({
        init,
    });
</script>
