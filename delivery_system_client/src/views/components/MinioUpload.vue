<template>
    <el-upload :class="{ hide_box: data.upload_btn }"
        :action="data.minioUrl"
               :file-list="fileList"
               :on-remove="handleRemove"
               list-type="picture-card"
               :on-success="handleSuccess"
               :limit="1"
               :auto-upload="true">
        <!--添加-->
        <el-icon><Plus /></el-icon>
        <!--上传文件展示-->
        <template #file="{ file }">
            <div>
                <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
                <span class="el-upload-list__item-actions">
                    <!-- 放大-->
                    <span
                            class="el-upload-list__item-preview"
                            @click="handlePictureCardPreview(file)"
                    >
                      <el-icon><zoom-in /></el-icon>
                    </span>
                    <!-- 下载-->
                    <span
                            v-if="!disabled"
                            class="el-upload-list__item-delete"
                            @click="handleDownload(file)"
                    >
                      <el-icon><Download /></el-icon>
                    </span>
                    <!-- 删除-->
                    <span
                            v-if="!disabled"
                            class="el-upload-list__item-delete"
                            @click="handleRemove(file)"
                    >
                      <el-icon><Delete /></el-icon>
                    </span>
                </span>
            </div>
        </template>
    </el-upload>

    <el-dialog style="width: 800px" v-model="dialogVisible">
        <img :src="dialogImageUrl" alt="Preview Image" />
    </el-dialog>
</template>
<script lang="ts" setup>
import { reactive, ref, onMounted } from 'vue'
import { Delete, Download, Plus, ZoomIn } from '@element-plus/icons-vue'
import type { UploadProps, UploadUserFile, UploadInstance, UploadRawFile } from 'element-plus'

import type { UploadFile } from 'element-plus'
import {ElMessageBox} from "element-plus";

const dialogImageUrl = ref('')
const dialogVisible = ref(false)
const disabled = ref(false)

// Data
const data = reactive({
    // 达到limit限制时隐藏上传按钮
    upload_btn: false,
    minioUrl: "http://localhost:6001/sysoss/uploadOSS",
    minioServerUrl: "http://127.0.0.1:9000/",
})

const fileList = ref<UploadUserFile[]>([
    // {
    //     name: '',
    //     url: '',
    // },
])
// Props
const props = defineProps({
    key1:{
        type: String,
        required: false
    },
    key2:{
        type: Number,
        required: false
    },
    // 上传的url 用来展示
    url: {
        type: String,
        required: false
    },
    // 只展示标志
    disabled: {
        type: Boolean,
        default: false,
        required: false
    },
})

// Mounted
onMounted(() => {
    console.log(props.url)
    if (props.url) {
        let obj = {
            name: 'pic',
            url: props.url
        }
        fileList.value.push(obj);
        data.upload_btn = true
    }

    if (props.disabled) {
        disabled.value = props.disabled
    }
})

const handleRemove = (file: UploadFile) => {
    console.log(file)
    // return ElMessageBox.confirm(
    //     `Cancel the transfer of file ?`
    // ).then(
    //     () => true,
    //     () => false
    // )
}

const handlePictureCardPreview = (file: UploadFile) => {
    console.log(file)
    dialogImageUrl.value = file.url!
    dialogVisible.value = true

}

const handleDownload = (file: UploadFile) => {
    console.log(file)
}

/**
 * 上传成功回调函数，对象存储：提示成功，并更新文件列表、groupId，同时返回 response，调用回调函数，由调用者处理
 * excel导入：只返回 response，调用回调函数，由调用者处理
 * @param response
 * @param file
 * @param fileList
 */
const emits = defineEmits(["getUrl"]);
const handleSuccess: UploadProps['onSuccess'] = (response, file, fileList) => {
    if (response.code === 200){
        // 封装文件信息
        const url = data.minioServerUrl + response.data.bucket + "/" + response.data.storageFileName;
        // 上传路径
        console.log(url)
        data.upload_btn = true
        // 回调
        emits("getUrl",url,props.key1,props.key2);
    }
}
</script>
<style>
    .hide_box .el-upload--picture-card {
        display: none;
    }
</style>
