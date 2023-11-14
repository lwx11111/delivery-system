<template>
    <el-upload
            v-model:file-list="data.fileList"
            :action="data.minioUrl"
            :data="data.fileData"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :on-success="handleSuccess"
            :on-error="handleError"
            list-type="picture"
            :limit="props.limit">
        <el-button type="primary">点击上传</el-button>
    </el-upload>
</template>

<script lang="ts" setup>
import Api from '@/api/api_category.js'
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";

const emits = defineEmits(["uploadCallback"]);
const store = useStore();
const router = useRouter()
import type { UploadProps, UploadUserFile } from 'element-plus'
// Data
const data = reactive({
    // 上传的文件列表
    fileList: [],
    fileData: {
        groupId: '',
        groupName:''
    },
    minioUrl: "http://localhost:9999/file/sysoss/uploadOSS",
    minioServerUrl: "http://127.0.0.1:9000/"
})

// Props
const props = defineProps({
    limit: {
        type: Number,
        default: 100
    },
})

// Mounted
onMounted(() => {

})

// Methods
/**
 * 上传成功回调函数，对象存储：提示成功，并更新文件列表、groupId，同时返回 response，调用回调函数，由调用者处理
 * excel导入：只返回 response，调用回调函数，由调用者处理
 * @param response
 * @param file
 * @param fileList
 */
const handleSuccess: UploadProps['onSuccess'] = (response, file, fileList) => {
    console.log(response)
    if (response.code === 200){
        const url = data.minioServerUrl + response.data.bucket + "/" + response.data.storageFileName;
        emits("uploadCallback", response, url);
    }
    // this.uploadStatus = false
    // if (response.data.result=='true') {
    //     this.data.groupId = response.data.groupId
    //     this.getFiles()
    //     this.$notify({
    //         type: 'success',
    //         title: '成功',
    //         message: '上传成功',
    //         duration: 5000
    //     })
    //     this.$emit('changeGroupId', response.data.groupId)
    // }else if (response.data.result=='false') {
    //     this.getFiles()
    //     this.$notify({
    //         type: 'warning',
    //         title: '失败',
    //         message: response.data.message,
    //         duration: 5000
    //     })
    //
    // }
    // this.$emit('callback', 'put', true, this.data.groupId, response,this.data.groupName)
}

/**
 * 上传失败的回调函数，对象存储：提示失败，同时返回 response，调用回调函数，由调用者处理
 * excel导入：只返回 response，调用回调函数，由调用者处理
 * @param err
 * @param file
 * @param fileList
 */
const handleError: UploadProps['onError'] = (err, file, fileList) => {
    this.uploadStatus = false
    if (this.oss) {
        this.$notify.error({
            title: '错误',
            message: err.message,
            duration: 5000
        })
    }
    this.$emit('callback', 'put', false, this.data.groupId, err,this.data.groupName)
}

const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
    console.log(uploadFile, uploadFiles)
}

const handlePreview: UploadProps['onPreview'] = (file) => {
    console.log(file)
}

</script>

<style scoped>

</style>
