<template>
    <div v-for="(item, key) in props.shopList" @click="toShopDetail(key)">
        <el-card style="margin-bottom: 10px">
            <el-row>
                <el-col :span="11" style="margin-right: 5px">
                    <el-image :src="item.picture"></el-image>
                </el-col>
                <el-col :span="12">
                    <el-row>
                        <span style="font-size: 20px;font-weight: bold;">
                            {{item.name}}
                        </span>
                    </el-row>
                    <el-row style="margin-top: 5px">
                        <el-col :span="10" >
                            <span style="font-size: 15px;">{{item.score}}分</span>
                        </el-col>
                        <el-col :span="10">
                            <span style="font-size: 15px;">月售{{item.salesVolume}}</span>
                        </el-col>
                    </el-row>
                    <el-row style="margin-top: 5px">
                        <el-col :span="10">
                            <span style="color: red">起送 ￥{{item.minPrice}}</span>
                        </el-col>
                        <el-col :span="10">
                            <span style="color: red">配送 ￥{{item.deliveryCharge}}</span>
                        </el-col>
                    </el-row>
                    <el-row style="margin-top: 5px">
                        <el-col :span="10">
                            <span>预计时间:{{item.duration}} 分钟</span>
                        </el-col>
                        <el-col :span="10">
                            <span>距离：{{item.distanceKm}} 公里</span>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </el-card>
    </div>
</template>

<script lang="ts" setup>
import { reactive, onMounted,defineProps } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'

const store = useStore();
const router = useRouter()

// Props
const props = defineProps({
    shopList:{
        type: Array,
        required: true
    }
})

// Data
const data = reactive({

})

// Mounted
onMounted(() => {

})

// Methods

/**
 * 跳转到店铺详情
 * @param key
 */
const toShopDetail = (key) => {
    router.push({
        path: '/Consumer/Shop/shopDetail',
        query: {
            shopId: props.shopList[key].id
        }
    })
}

</script>

<style scoped>

</style>
