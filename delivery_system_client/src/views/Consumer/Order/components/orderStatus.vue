<!-- 订单状态流转信息 -->
<template>
    <el-dialog v-model="data.dialogVisible" title="订单跟踪">
        <el-timeline style="max-width: 600px">
            <el-timeline-item
                v-for="(item, index) in data.orderStatus"
                :key="index"
                :timestamp="item.statusTime"
            >
                {{ item.name }}
            </el-timeline-item>
        </el-timeline>
    </el-dialog>

</template>

<script lang="ts" setup>
import { reactive, watch, onMounted, ref, defineProps } from 'vue'
import { useStore } from "vuex";
import { useRouter, useRoute } from 'vue-router'

const store = useStore();
const router = useRouter();
const route = useRoute();

// Data
const data = reactive({
    orderStatus: [],
    dialogVisible: false,
})

// Props
const props = defineProps({

})

// Mounted
onMounted(() => {

})

// Methods

const init = (orderStatus) => {
    // 界面初始化接收参数
    data.orderStatus = orderStatus;
    data.dialogVisible  = true;
}

//暴露方法
defineExpose({
    init,
});

</script>

<style scoped>

</style>
