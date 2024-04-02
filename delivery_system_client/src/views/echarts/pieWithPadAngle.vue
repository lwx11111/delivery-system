<template>
    <el-card style="width: 600px; height: 500px">
        <div id="pieWithPadAngle" style="width: 500px; height: 500px"></div>
    </el-card>
</template>

<script lang="ts" setup>
import { onMounted, reactive } from 'vue'
import * as echarts from 'echarts';
import ApiOrder from '@/api/Order/api_orderinfo.js'
// Data
const data = reactive({
    option: {
        title: {
            text: '热销物品',
            left: 'center',
        },
        // 提示框组件
        tooltip: {
            // 数据项图形触发
            trigger: 'item'
        },
        // 图例组件
        legend: {
            top: '5%',
            left: 'center'
        },
        series: [
            {
                name: '热销物品',
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                padAngle: 5,
                itemStyle: {
                    borderRadius: 10
                },
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: 40,
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: [
                    { value: 1048, name: '1' },
                    { value: 735, name: '2' },
                    { value: 580, name: '3' },
                    { value: 484, name: '4' },
                    { value: 300, name: '5' },
                    { value: 300, name: '6' }
                ]
            }
        ]
    }
})

// Mounted
onMounted(() => {
    // 热销数据
    const params = {
        shopId: localStorage.getItem("shopId")
    }
    ApiOrder.getHotItemData(params).then(res => {
        console.log(res)
        if (res.code === 200) {
            console.log(data.option)
            data.option.series[0].data = res.data;
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('pieWithPadAngle'));
            // 绘制图表
            myChart.setOption(data.option);
        }
    })

})

</script>

<style scoped>

</style>
