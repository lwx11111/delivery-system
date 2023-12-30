<template>
<!--    :limit="props.limit"-->
<!--    :on-exceed="handleExceed"-->
    <div>
        <div v-if="props.show" v-for="(item,key) in props.fileList">
            <el-image style="width: 100px; height: 100px"
                      :src="item.url">
            </el-image>
        </div>
        <el-upload v-else
                   ref="upload"
                   :file-list="data.fileList"
                   :auto-upload="true"
                   list-type="picture"

                   :action="data.minioUrl"
                   :data="data.fileData"
                   :on-preview="handlePreview"
                   :on-remove="handleRemove"
                   :on-success="handleSuccess"
                   :on-error="handleError"
                   :before-upload="handleBeforeUpload"

                   :on-change="handleChange">
            <el-button type="primary">点击上传</el-button>
            <template #tip>
                <div class="el-upload__tip text-red">
                    文件大小不能超过2MB!
                </div>
            </template>
        </el-upload>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";
import ApiOss from "@/api/api_sysoss";

const emits = defineEmits(["uploadCallback"]);
const store = useStore();
const router = useRouter()
import type { UploadProps, UploadUserFile, UploadInstance, UploadRawFile } from 'element-plus'
import { genFileId } from 'element-plus'
// Data
const data = reactive({
    fileData: {
        groupId: '',
        groupName:''
    },
    minioUrl: "http://localhost:6001/sysoss/uploadOSS",
    minioServerUrl: "http://127.0.0.1:9000/",
    fileList: [],
})

// Props
const props = defineProps({
    limit: {
        type: Number,
        default: 100
    },
    // 上传的文件列表
    fileList: {
        type: Array,
        default: [],
        required: false
    },
    show: {
        type: Boolean,
        default: false,
        required: false
    },
    // 指定上传的文件列表下标
    i: {
        type: Number,
        default: -1,
        required: false
    },
    j: {
        type: Number,
        default: -1,
        required: false
    }

})

// Mounted
onMounted(() => {
    data.fileList = props.fileList;
    console.log(props.show)
    console.log(data.fileList)
})

// Methods

/**
 *  限制只能上传一个文件，再次上传则覆盖之前的文件
 */
const upload = ref<UploadInstance>()
const handleExceed: UploadProps['onExceed'] = (files) => {
    ElMessageBox.confirm('重复上传会覆盖之前的材料，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        // 先从oss删除之前的文件
        console.log(data.fileData)
        // if (data.fileData != null){
        //     deleteFile();
        // }
        // 删除fileList中的文件
        data.fileList = [];
        upload.value!.clearFiles()
        const file = files[0] as UploadRawFile
        file.uid = genFileId()
        upload.value!.handleStart(file)
        console.log(file)
        upload.value!.submit()
        console.log(upload.value!)
        console.log(file)
    })
}

/**
 * 删除目前的文件
 */
const deleteFile = () => {
    ApiOss.deleteFileByStorageFileName(data.fileData.storageFileName).then(res => {
        console.log(res);
        if (res.code === 200){
            data.fileData = null;
        }
    })
}
/**
 *
 * @param rawFile
 */
const handleBeforeUpload: UploadProps['beforeUpload'] = (rawFile) => {
    //文件类型和大小限制
    // if (rawFile.type !== 'image/jpeg') {
    //     ElMessage.error('图片必须是JPG类型')
    //     return false
    // } else if (rawFile.size / 1024 / 1024 > 2) {
    //     ElMessage.error('图片大小不能超过2MB!')
    //     return false
    // }

    // 文件数量限制
    if (data.fileList.length >= props.limit) {
        ElMessage.error(`最多只能上传${props.limit}个文件`)
        return false
    }
    return true
}

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
        // 封装文件信息
        data.fileData = response.data;
        const url = data.minioServerUrl + response.data.bucket + "/" + response.data.storageFileName;
        // 上传路径
        console.log(url)
        let item = {
            url: url
        }
        data.fileList.push(item)
        if (props.i !== -1 && props.j !== -1){
            emits("uploadCallback", response, url,props.i,props.j);
        } else {
            emits("uploadCallback", response, url);
        }


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
    console.log(file)
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

/**
 *
 * @param uploadFile
 * @param uploadFiles
 */
const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
    console.log(uploadFile)
    return ElMessageBox.confirm(
        `Cancel the transfer of ${uploadFile.name} ?`
    ).then(
        () => true,
        () => false
    )
}

/**
 *
 * @param file
 */
const handlePreview: UploadProps['onPreview'] = (file) => {
    console.log(file)
}

/**
 * 文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用
 * @param file
 */
const handleChange: UploadProps['onChange'] = (file) => {
    console.log(file)
    console.log(data.fileList)
    // if (data.fileList.length > 0) {
    //     data.fileList = [data.fileList[data.fileList.length - 1]]//这一步，是 展示最后一次选择文件
    // }
}

defineExpose({
    deleteFile,
});

</script>

<style scoped>

</style>
