<template>
    <!-- 头像名字-->
    <el-card style="margin-bottom: 10px">
        <el-row>
            <el-col style="margin-right: 10px"
                    :span="4">
                <el-image :src="data.shop.picture"></el-image>
            </el-col>
            <el-col :span="10">
                <h1>{{data.shop.name}}</h1>
            </el-col>
        </el-row>
    </el-card>
    <el-card style="margin-bottom: 10px">
        <el-row>
            <el-col :span="12">
                <h1>收益：</h1>
            </el-col>
            <el-col :span="12">
                <h1 style="color: red">{{ data.shop.earnings }} ￥</h1>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <h1>完成订单数：</h1>
            </el-col>
            <el-col :span="12">
                <h1>{{data.shop.num}}</h1>
            </el-col>
        </el-row>
    </el-card>

</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import {ElMessage} from "element-plus";
import Api from '@/api/Shop/api_shop.js'

const store = useStore();
const router = useRouter()

// Data
const data = reactive({
    //用户信息
    user: {
        id: localStorage.getItem("userId"),
        name: localStorage.getItem("userName"),
    },
    // 店铺信息
    shop:{
        picture:'',
        name:'',
        num:'',
        earnings:''
    }

})

// Mounted
onMounted(() => {
    getData()
})

// Methods
const getData = () => {
    if (data.user.id === null){
        ElMessage({
            type: 'warning',
            message: '请先登录',
        })
        router.push("/login")
    }
    let param = {
        userId: data.user.id
    }
    Api.selpage4shop(param).then(res=> {
        console.log(res)
        if (res.code === 200){
            data.shop = res.data.records[0];
        }
    })
}
</script>

<style scoped>

</style>
