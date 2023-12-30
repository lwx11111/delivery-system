<template>
    <h1>我的店铺</h1>
    <el-card style="margin: 10px; border: 1px solid gold">
        <!-- 表格呈现 -->
        <el-table
                :data="data.tableData"
                :height="data.screenHeight - data.otherHeight"
                tooltip-effect="dark"
                style="width:100%"
                stripe
                size="default"
                border
                @selection-change="selectionChanged">
            <el-table-column
                    type="selection"
                    width="60">
            </el-table-column>
            <el-table-column
                    prop="id"
                    label="id"
                    width="200"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="店铺名"
                    width="180"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="province"
                    label="省"
                    width="180"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="score"
                    label="店铺评分"
                    width="180"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="salesVolume"
                    label="销量"
                    width="180"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="minPrice"
                    label="起送价格"
                    width="180"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="deliveryCharge"
                    label="配送费"
                    width="180"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="openTime"
                    label="开门时间"
                    width="180"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="closeTime"
                    label="关门时间"
                    width="180"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="status"
                    label="状态"
                    width="180"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="description"
                    label="店铺描述"
                    width="180"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="itemCategory"
                    label="物品分类"
                    width="180"
                    align="center">
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    align="center"
                    width="400">
                <template #default="scope">
                    <el-link
                            style="margin-right: 20px"
                            @click="showShopItemDialog(scope)"
                            type="primary"
                            size="small"
                            :underline="false">
                        编辑物品
                    </el-link>
                    <el-link
                            style="margin-right: 20px"
                            @click="toUpdate(scope)"
                            type="primary"
                            size="small"
                            :underline="false">
                        更新
                    </el-link>
                    <el-link
                            style="margin-right: 20px"
                            @click="toDetail(scope)"
                            type="primary"
                            size="small"
                            :underline="false">
                        查看详情
                    </el-link>
                    <el-link
                            @click="toDelete(scope)"
                            type="primary"
                            size="small"
                            :underline="false">
                        删除
                    </el-link>
                </template>
            </el-table-column>
        </el-table>
    </el-card>
    <ShopItemDialog ref="shopItemDialog"></ShopItemDialog>
    <ItemDialog ref="itemDialog" :type="data.type"></ItemDialog>
</template>
<script lang="ts" setup>
    import Api from '@/api/Shop/api_shop.js'
    import ItemDialog from './Item.vue'
    import { reactive, ref, defineProps, toRefs, onMounted} from 'vue'
    import { useStore } from "vuex";
    import { useRouter } from 'vue-router'
    import {ElMessage, ElMessageBox} from "element-plus";
    import ShopItemDialog from "@/views/components/shopItemDialog.vue";

    const store = useStore();
    const router = useRouter()

    // Data
    const data = reactive({
        screenHeight: window.innerHeight,// screenHeight:控制高度自适应-页面高度
        otherHeight: 310,// otherHeight:控制高度自适应-表格外的高度
        // tableData:表格数据
        tableData: [],
        // dialog
        type: '',
        shopId: "1",
        userId: localStorage.getItem("userId")
    })

    // Mounted
    onMounted(() => {
        getData();
    })

    // Methods
    const getData = () => {
        if (data.userId === null){
            ElMessage({
                type: 'warning',
                message: '请先登录',
            })
            router.push("/login")
        }
        let param = {
            userId: data.userId
        }
        Api.selpage4shop(param).then(res=> {
            console.log(res)
            if (res.code === 200){
                data.tableData = res.data.records;
            }
        })
    }
    // 添加记录
    const itemDialog = ref();

    // 选中行变化事件
    const selectionChanged = (val: number) => {

    }

    const toUpdate = (scope) => {
        data.type = "update"
        itemDialog.value.init(scope.row.id, data.type);
    }
    const toDetail = (scope) => {
        data.type = "detail"
        itemDialog.value.init(scope.row.id, data.type);
    }

    const toDelete = (scope) => {
        ElMessageBox.confirm(
                '确认删除？',
                '警告',
                {
                    confirmButtonText: '确认删除',
                    cancelButtonText: '取消',
                    type: 'warning',
                }
        ).then(() => {
            console.log(scope.row.id)
            Api.del4shop(scope.row.id).then(res => {
                console.log(res)
                if (res.code === 200){
                    ElMessage({
                        type: 'success',
                        message: '删除成功',
                    })
                    getData()
                } else {
                    ElMessage({
                        type: 'warning',
                        message: '删除失败',
                    })
                }
            })
        }).catch(() => {
            ElMessage({
                type: 'info',
                message: '已取消删除',
            })
        })
    }

    const shopItemDialog = ref();
    const showShopItemDialog = (scope) => {
        shopItemDialog.value.init(scope.row.id);
    }


</script>
<style lang="css" scoped>

</style>
