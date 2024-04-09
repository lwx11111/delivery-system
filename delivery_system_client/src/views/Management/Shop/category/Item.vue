<template>
    <el-dialog v-model="data.showDialog"
               destroy-on-close
          width="90%"
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
                                label="分类名"
                                prop="name"
                                label-width="150px">
                            <el-input
                                  v-model="data.item.name"
                                  :disabled="data.disabled">
                            </el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row v-if="data.type !== 'add' ">
                    <el-col v-if="data.isParent === '1' " :span="6">
                        <el-form-item
                            label="父级分类名">
                            <el-input
                                v-model="data.item.parentName"
                                :disabled="true">
                            </el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row v-else>
                    <el-col :span="6">
                        <el-form-item
                                label="是否有父级分类"
                                prop="isParentId"
                                label-width="150px">
                            <!--绑定Boolean值无法渲染 Number只能渲染1次-->
                            <el-select v-model="data.isParent"
                                       placeholder="请选择"
                                       :disabled="data.disabled"
                                       size="large">
                                <el-option label="是" value="1" />
                                <el-option label="否" value="0" />
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col v-if="data.isParent === '1' " :span="6">
                        <el-form-item
                                label="选择父级分类"
                                prop="isParentId">
                            <el-select v-model="data.item.parentId" placeholder="请选择" size="large">
                                <el-option v-for="(item, index) in data.parentCategoryList"
                                           :label="item.name"
                                           :value="item.id" />
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col>
                        <el-form-item
                                label="图片"
                                prop="picture"
                                label-width="150px">
                            <MinioUpload :disabled="type === 'detail'"
                                            :url="data.item.picture"
                                            @getUrl="getUrl">
                            </MinioUpload>
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
    import Api from '@/api/Shop/api_category.js'
    import { reactive, ref, onMounted, toRefs } from 'vue'
    import { useStore } from "vuex";
    import { useRouter } from 'vue-router'
    import {ElMessage, ElMessageBox} from "element-plus";
    import StringUtil from '@/utils/StringUtil.js';
    const store = useStore();
    const router = useRouter()
    import MinioUpload from "@/views/components/MinioUpload.vue";

    // Data
    const data = reactive({
        // 是否有父级分类
        isParent: '1',
        // 父级分类
        parentCategoryList:[
            {
                id: '1',
                name: '11'
            }
        ],
        operateTitle: '新增',
        type: '',
        showBtn: true,
        disabled: false,
        id: 0,
        item: {
            id: '',
            name: '',
            parentName: '',
            picture:'',
        },
        params: {
            id: '',
            name: '',
            parentId: '',
        },
        showDialog: false,
        rules: {
            name: [
              { required: true, message: '分类名不能为空', trigger: 'blur' }
            ],
            picture: [
                { required: true, message: '图片不能为空', trigger: 'blur' }
            ],
        },
        show: true,
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
        data.item.picture = url;
    }

    /**
     * 父级分类店铺列表
     */
    const getParentCategoryList = () => {
        let param = {
            isParentId: true
        }
        Api.selpage4category(param).then(res => {
            if (res.code === 200){
                data.parentCategoryList = res.data.records;
            }
        })
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
            Api.sel4category(data.id).then(res => {
                console.log(res)
                if (res.code === 200){
                    console.log(res)
                    data.item = res.data;
                    // 界面显示
                    data.showDialog = true;
                    // 父级分类判断
                    if (StringUtil.isEmpty(res.data.parentId)){
                        data.isParent = '0';
                    } else {
                        data.isParent = '1';
                        data.item.parentId = res.data.parentId;
                        Api.sel4category(res.data.parentId).then(res => {
                            if (res.code === 200){
                                data.item.parentName = res.data.name;
                            }
                        })
                    }

                } else {
                    ElMessage({
                      message: '获取数据失败，请重试',
                      type: 'warning',
                    })
                    return;
                }
            })
        } else {
            // 界面显示
            data.showDialog = true;
            getParentCategoryList();
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
        // 表单验证
        itemForm.value.validate((valid, fields) => {
            if (valid) {
                if (data.isParent === '1'){
                    if(StringUtil.isEmpty(data.item.parentId)){
                        ElMessage({
                          message: '请选择父级分类',
                          type: 'warning',
                        })
                        return;
                    }
                }
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
            Api.update4category(data.id, data.item).then(res => {
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
            // 新增
            console.log(data.item)
            Api.add4category(data.item).then(res => {
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
    // todo 关闭弹窗时删除文件 :before-close="handleClose"
    const uploadRef = ref();
    const handleClose = () => {
        uploadRef.value.deleteFile();
        done();
    }
    //暴露state和play方法
    defineExpose({
        init,
    });
</script>
