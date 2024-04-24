<!--地址展示card-->
<template>
    <el-card>
        <el-row style="width: 600px;">
            <el-col :span="20">
                <h4>{{props.address.detailAddress}}</h4>
            </el-col>
            <el-col :span="4" style="margin-top: 20px" v-if="props.isUpdate">
                <el-icon @click="updateAddress()"><EditPen /></el-icon>
            </el-col>
        </el-row>
        <el-row style="width: 100%">
            <el-col :span="6">
                {{props.address.contacts}}
            </el-col>
            <el-col :span="6">
                {{props.address.phone}}
            </el-col>
        </el-row>
    </el-card>
    <!--修改添加组件-->
    <AddAddress ref="addressRef"></AddAddress>
</template>

<script lang="ts" setup>
import { reactive, watch, onMounted, ref, defineProps } from 'vue'
import { useStore } from "vuex";
import { useRouter, useRoute } from 'vue-router'
import AddAddress from "../addAddress.vue";
const store = useStore();
const router = useRouter();
const route = useRoute();

// Data
const data = reactive({
    // 用户地址信息
    addressList:[],
    dialogVisible: false,
    // 单选框
    radio: ''
})

// Props
const props = defineProps({
    address: {
        type: Object,
        required: true
    },
    // 是否需要更新
    isUpdate: {
        type: Boolean,
        default: false,
        required: false
    },
    // 长 宽
    height: {
        type: String,
        default: "100px",
        required: false
    },
    width: {
        type: String,
        default: "100px",
        required: false
    }
})

// Mounted
onMounted(() => {
    console.log(props.address)
})

// Methods

/**
 * 修改地址
 */
const addressRef = ref();
const updateAddress = () => {
    addressRef.value.init(props.address);
}

</script>

<style scoped>

</style>
