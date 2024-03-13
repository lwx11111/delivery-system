<template>
    <!--商铺物品信息-->
    <div v-for="(item, key) in props.shops"
         @click="toShopDetail(key)">
        <el-card style="margin-top: 10px">
            <!--头部店铺信息-->
            <el-row style="margin-bottom: 10px">
                <el-col :span="4"
                        style="margin-right: 5px">
                    <el-image :src="item.shop.picture"></el-image>
                </el-col>
                <el-col :span="8">
                    {{item.shop.name}}
                </el-col>
            </el-row>
            <el-divider></el-divider>
            <!-- 物品列表 -->
            <div v-for="(it,i) in item.shopItems">
                <el-row style="margin-bottom: 10px">
                    <el-col :span="7"
                            style="margin-right: 5px">
                        <el-image :src="it.picture"></el-image>
                    </el-col>
                    <el-col :span="10">
                        {{it.name}}
                    </el-col>
                    <el-col :span="7">
                        {{it.price}}
                    </el-col>
                </el-row>
            </div>
        </el-card>
    </div>
</template>

<script lang="ts" setup>
import { reactive, defineProps, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter, useRoute } from 'vue-router'
import {ElMessage} from "element-plus";

const store = useStore();
const router = useRouter();
const route = useRoute();

// Data
const data = reactive({
    // 店铺信息
    shops: [
        {
            shop:{
                id: '',
                name : '',
                picture : '',
            },
            shopItems: [
                {
                    description: "1",
                    name: "物品1",
                    picture: "1",
                    price: 1,
                    amount: 1,

                },
            ]
        },
    ]
})
// Props
const props = defineProps({
    shops:{
        type: Array,
        default: null,
        required: true
    }
})
// Mounted
onMounted(() => {

})

// Methods

const toShopDetail = (key) => {
    router.push({
        path: '/shopDetail',
        query: {
            shopId: key
        }
    })
}

</script>

<style scoped>

</style>
