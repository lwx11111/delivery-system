<template>
    <el-row>
        <h1>评价</h1>
    </el-row>
    <el-card v-for="(item, key) in data.comments"
            style="margin-bottom: 10px">
        <!--用户信息-->
        <el-row >
<!--            <el-col :span="10">-->
<!--                <el-image :src="require('src/assets/profile.png')"></el-image>-->
<!--            </el-col>-->
            <el-col :span="5">
                {{item.userName}}
            </el-col>
            <el-col :span="5">
                {{item.releaseTime}}
            </el-col>
        </el-row>
        <el-divider></el-divider>
        <!--评价信息-->
        <el-row >
            {{item.content}}
        </el-row>
    </el-card>
</template>

<script lang="ts" setup>
import { reactive, onMounted,defineProps } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import Api from '@/api/Comment/api_comment.js'
const store = useStore();
const router = useRouter()

// Props
const props = defineProps({
    shopId: {
        type: String,
        required: true
    }
})

// Data
const data = reactive({
    // 评价
    comments: [
        {
            content: "qwe",
            id: "1736777194237161473",
            orderId: "1730498833424232449",
            params:null,
            parentId: null,
            releaseTime: "2023-12-19 12:04:58",
            riderId: "1",
            shopId: null,
            userId: localStorage.getItem('userId'),
            userName: "11"
        }
    ],
    params: {
        shopId: props.shopId
    }
})

// Mounted
onMounted(() => {
    listCommentsByShopId()
})

// Methods
const listCommentsByShopId = () => {
    Api.selpage4comment(data.params).then(res => {
        console.log(res)
        if (res.code === 200) {
            data.comments = res.data.records
        }
    })

}


</script>

<style scoped>

</style>
