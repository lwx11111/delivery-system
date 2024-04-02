<template>
    <el-card style="width: 600px; height: 500px">
        <div id="mixedLineAndBar" style="width: 500px; height: 500px"></div>
    </el-card>
</template>

<script lang="ts" setup>
import {onMounted, reactive} from 'vue'
import * as echarts from 'echarts';
import ApiOrder from '@/api/Order/api_orderinfo.js'
// Data
const data = reactive({
    option:{
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        legend: {

            data: ['订单数量', '当日收入']
        },
        xAxis: [
            {
                type: 'category',
                data: [],
                axisPointer: {
                    type: 'shadow'
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '订单数量',
                min: 0,
                max: 10,
                interval: 2,
                axisLabel: {
                    formatter: '{value} 单'
                }
            },
            {
                type: 'value',
                name: '当日收入',
                min: 0,
                max: 30,
                interval: 6,
                axisLabel: {
                    formatter: '{value} 元'
                }
            }
        ],
        series: [
            {
                name: '订单数量',
                type: 'bar',
                tooltip: {
                    valueFormatter: function (value) {
                        return value as number + ' 单';
                    }
                },
                data: []
            },
            {
                name: '当日收入',
                type: 'bar',
                tooltip: {
                    valueFormatter: function (value) {
                        return value as number + ' 元';
                    }
                },
                data: []
            },
            // {
            //     name: 'Temperature',
            //     type: 'line',
            //     yAxisIndex: 1,
            //     tooltip: {
            //         valueFormatter: function (value) {
            //             return value as number + ' °C';
            //         }
            //     },
            //     data: [2.6, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
            // }
        ]
    }
})

// Mounted
onMounted(() => {
    // 七天内数据
    const params = {
        shopId: localStorage.getItem("shopId")
    }
    ApiOrder.getDataNearlySevenDays(params).then(res => {
        console.log(res)
        if (res.code === 200) {
            // 日期数组
            let dateArr = res.data.map(item => item.date.slice(5))
            data.option.xAxis[0].data = dateArr;

            // 收入数组
            data.option.series[1].data = res.data.map(item => item.total);

            // 订单数量数组
            let orderNumArr = res.data.map(item => item.num)
            data.option.series[0].data = orderNumArr;

            // 基于准备好的dom，初始化echarts实例,多个组件 id必须区分
            var myChart = echarts.init(document.getElementById('mixedLineAndBar'));
            // 绘制图表
            myChart.setOption(data.option);
        }
    })
})

</script>

<style scoped>

</style>
