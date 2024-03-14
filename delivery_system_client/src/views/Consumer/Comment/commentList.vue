<template>
    <el-card style="margin-bottom: 10px">
        店铺评分
        <el-rate
                v-model="props.score"
                size="large"
                disabled
                show-score
                text-color="#ff9900"
                score-template="{value} 分">

        </el-rate>
    </el-card>

    <el-card v-for="(item, key) in data.comments"
            style="margin-bottom: 10px">
        <!--用户信息-->
        <el-row>
            <el-col :span="3">
                <el-image style="width: 100px;height: 100px" src="http://127.0.0.1:9000/test/038915195d79a495ab8ed3db4aa7f8c5_.jpg"></el-image>
            </el-col>
            <el-col :span="10">
                <el-row>
                    <el-text style="color:black; font-size: 20px" size="large" tag="b">
                        {{item.userName}}</el-text>
                </el-row>
                <el-row>
                    {{item.releaseTime}}
                </el-row>
            </el-col>
        </el-row>
        <!--评分信息-->
        <el-row>
            <el-rate
                v-model="props.score"
                disabled
                show-score
                text-color="#ff9900"
                score-template="{value} 分">

            </el-rate>
        </el-row>
        <!--评价信息-->
        <el-row >
            {{item.content}}
        </el-row>
        <el-divider></el-divider>
    </el-card>
</template>

<script lang="ts" setup>
import { reactive, onMounted,defineProps } from 'vue'
import { useStore } from "vuex";
import {useRoute, useRouter} from 'vue-router'
import Api from '@/api/Comment/api_comment.js'
import UserStorage from '@/cache/userStorage.js'
const store = useStore();
const router = useRouter()
const route = useRoute();
// Props
const props = defineProps({
    // 店铺id 子组件先mounted 所以这里获取不到 要路由获取
    shopId: {
        type: String,
        required: false,
    },
    // 评分
    score: {
        type: Number,
        required: true,
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
            userId: UserStorage.getUserId(),
            userName: "11"
        }
    ],
    params: {
        shopId: ''
    }
})

// Mounted
onMounted(() => {
    data.params.shopId = route.query.shopId
    listCommentsByShopId()
})

// Methods
const listCommentsByShopId = () => {
    console.log(data.params)
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
