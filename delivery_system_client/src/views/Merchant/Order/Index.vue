<template>
    <div>
        <el-card style="margin: 10px; border: 1px solid gold">
            <!-- 查询条件 -->
            <el-collapse
                    accordion
                    v-model="data.activeName"
                    class="card-bg">
                <el-collapse-item name="1">
                    <template #title>
                        <div class="innerHeader">
                          点单信息管理
                        </div>
                    </template>
                    <div style="display: flex;"
                         class="card-search">
                        <el-form :inline="true"
                                 :model="data.formList"
                                 size="default"
                                 label-width="100px">
                            <el-form-item label="订单ID">
                                <el-input placeholder="请输入订单ID"
                                          v-model="data.formList.id"
                                          style="width: 200px"
                                          @keyup.enter.native="getData">
                                </el-input>
                            </el-form-item>
                            <el-form-item label="骑手ID">
                                <el-input placeholder="请输入骑手ID"
                                            v-model="data.formList.deliveryRiderId"
                                            style="width: 200px"
                                            @keyup.enter.native="getData">
                                  </el-input>
                            </el-form-item>
                            <el-form-item label="用户ID">
                                <el-input placeholder="请输入用户ID"
                                            v-model="data.formList.userId"
                                            style="width: 200px"
                                            @keyup.enter.native="getData">
                                  </el-input>
                            </el-form-item>
                        </el-form>
                    </div>
                </el-collapse-item>
            </el-collapse>
        </el-card>
        <el-card style="margin: 10px; border: 1px solid gold">
            <!-- 操作按钮区 -->
            <div style="margin:10px 0;">
                <el-button
                        type="warning"
                        icon="DocumentDelete"
                        @click="deleteDataMany()">
                  删除
                </el-button>
                <div style="float:right;">
                    <el-button
                          type="primary"
                          @click="queryData()"
                          icon="Search"
                          :loading="data.isSearch">
                    查询
                    </el-button>
                    <el-button
                          @click="resetData()"
                          icon="Close">
                    清空
                    </el-button>
                    <el-button
                          @click="excelData()">
                    导出数据
                    </el-button>
                </div>
            </div>

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
                        width="180"
                        align="center">
                </el-table-column>
                 <el-table-column
                        prop="deliveryRiderId"
                        label="配送骑手"
                        width="180"
                        align="center">
                </el-table-column>
                 <el-table-column
                        prop="userId"
                        label="用户ID"
                        width="180"
                        align="center">
                </el-table-column>
                 <el-table-column
                        prop="totalCharge"
                        label="总金额"
                        width="180"
                        align="center">
                </el-table-column>
                 <el-table-column
                        prop="expectedTime"
                        label="期望时间"
                        width="180"
                        align="center">
                </el-table-column>
                 <el-table-column
                        prop="location"
                        label="配送地址"
                        width="180"
                        align="center">
                </el-table-column>
                 <el-table-column
                        prop="deliveryService"
                        label="配送服务"
                        width="180"
                        align="center">
                </el-table-column>
                 <el-table-column
                        prop="orderTime"
                        label="下单时间"
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
                        prop="paymentMethod"
                        label="支付方式"
                        width="180"
                        align="center">
                </el-table-column>
                 <el-table-column
                        prop="remark"
                        label="备注"
                        width="180"
                        align="center">
                </el-table-column>
                 <el-table-column
                        prop="tableware"
                        label="餐具数量"
                        width="180"
                        align="center">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        label="操作"
                        align="center"
                        width="400">
                    <template #default="scope">
                        <el-link v-if="data.tableData[scope.$index].status === 2"
                                style="margin-right: 20px"
                                @click="orderTaking(scope)"
                                type="primary"
                                size="small"
                                :underline="false">
                            接单
                        </el-link>
                        <el-link v-if="data.tableData[scope.$index].status === 1"
                                 style="margin-right: 20px"
                                 @click="orderPay(scope)"
                                 type="primary"
                                 size="small"
                                 :underline="false">
                            支付
                        </el-link>
                        <el-link
                                style="margin-right: 20px"
                                @click="showOrderItemDialog(scope)"
                                type="primary"
                                size="small"
                                :underline="false">
                            查看物品
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

            <el-pagination
                      v-model:current-page="data.pageConfig.currentPage"
                      v-model:page-size="data.pageConfig.pageSize"
                      class="customPage mt-10"
                      background
                      :page-sizes="[10, 20, 50]"
                      :current-page="data.pageConfig.currentPage"
                      :page-size="data.pageConfig.pageSize"
                      :total="data.pageConfig.total"
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange"
                      layout="sizes,total, ->, prev, pager, next">
              </el-pagination>
        </el-card>
        <OrderItemDialog ref="orderItemDialog"></OrderItemDialog>
        <ItemDialog ref="itemDialog" :type="data.type"></ItemDialog>
    </div>
</template>
<script lang="ts" setup>
    import Api from '@/api/Order/api_orderinfo.js'
    import ItemDialog from './Item.vue'
    import { reactive, ref, onMounted} from 'vue'
    import { useStore } from "vuex";
    import { useRouter } from 'vue-router'
    import {ElMessage, ElMessageBox} from "element-plus";
    import OrderItemDialog from "@/views/components/orderItemDialog.vue";

    const store = useStore();
    const router = useRouter()

    // Data
    const data = reactive({
        screenHeight: window.innerHeight,// screenHeight:控制高度自适应-页面高度
        otherHeight: 310,// otherHeight:控制高度自适应-表格外的高度
        isSearch: false, // isSearch:控制搜索状态
        // formList:搜索条件对象 分页控制对象
        formList: {
            id:'',
            shopId: '',
            deliveryRiderId: '',
            userId: '',
            shopItem: '',
            packingCharges: '',
            deliveryCharge: '',
            totalCharge: '',
            expectedTime: '',
            location: '',
            deliveryService: '',
            orderTime: '',
            status: '',
            paymentMethod: '',
            remark: '',
            tableware: ''
        },
        // tableData:表格数据
        tableData: [],
        activeName: '1',
        // 分页配置
        pageConfig: {
            currentPage: 1,
            pageSize: 10,
            total: 1000
        },
        // dialog
        type: '',
    })
    // 解构抛出 直接使用
    // const { type} = toRefs(data)

    // Mounted
    onMounted(() => {
        getData();
    })

    // Methods
    const orderTaking = (scope) => {
        const param = {
            orderId: scope.row.id
        }
        Api.orderTaking(param).then(res => {
            if (res.code === 200){
                ElMessage({
                    type: 'success',
                    message: '接单成功',
                })
            } else {
                ElMessage({
                    type: 'warning',
                    message: '接单失败，请重试',
                })
            }
        })
    }

    const orderPay = (scope) => {
        const param = {
            orderId: scope.row.id
        }
        Api.orderPay(param).then(res => {
            if (res.code === 200){
                ElMessage({
                    type: 'success',
                    message: '支付成功',
                })
            } else {
                ElMessage({
                    type: 'warning',
                    message: '支付失败，请重试',
                })
            }
        })
    }
    const getData = () => {
        // 查询方法
        // 查询参数
        const params = {
            shopId : "1",
            deliveryRiderId : data.formList.deliveryRiderId,
            userId : data.formList.userId,
            shopItem : data.formList.shopItem,
            packingCharges : data.formList.packingCharges,
            deliveryCharge : data.formList.deliveryCharge,
            totalCharge : data.formList.totalCharge,
            expectedTime : data.formList.expectedTime,
            location : data.formList.location,
            deliveryService : data.formList.deliveryService,
            orderTime : data.formList.orderTime,
            status : data.formList.status,
            paymentMethod : data.formList.paymentMethod,
            remark : data.formList.remark,
            tableware : data.formList.tableware,
            pageIndex : data.pageConfig.currentPage,
            pageSize : data.pageConfig.pageSize
        }
        // 后台请求
        Api.selpage4orderinfo(params).then(res=> {
            if (res.code === 200){
                data.tableData = res.data.records
                data.pageConfig.total = res.data.total
                data.isSearch = false
            }
        })
    }
    // 添加记录
    const itemDialog = ref();
    const addData = () => {
        data.type = "add";
        itemDialog.value.init(null,data.type);
    }
    // 下载模板
    const downloadExcelTemplate = () => {
        const params = {}
        Api.downloadExcelTemplate(params).then(data => {
            console.log(data)
            const blob = new Blob([data], { type: 'application/vnd.ms-excel' })
            const blobUrl = URL.createObjectURL(blob)
            const a = document.createElement('a')
            a.href = blobUrl
            a.download = '点单信息.xls'
            a.click()
            window.URL.revokeObjectURL(blobUrl)
        })
    }

    // 导入数据
    const uploadExcelRef = ref();
    const uploadExcel = () => {
        // const uploadExcelUrl = Api.uploadExcelUrl();
        uploadExcelRef.value.init(this.SHOP_SERVER + '/orderinfo/uploadExcel');
    }

    const deleteDataMany = () => {
        // 删除多条记录
        const datas = data.selectedRows
        const dataids = []
        if (!datas || !datas.length || datas.length === 0) {
            ElMessage({
                message: '请选择数据',
                type: 'warning',
            })
        } else {
            for (const i of datas) {
                dataids.push(i.id)
            }
            ElMessageBox.confirm(
                    '确认删除？',
                    '警告',
                    {
                        confirmButtonText: '确认删除',
                        cancelButtonText: '取消',
                        type: 'warning',
                    }
            ).then(() => {
                Api.dels4orderinfo(dataids).then(res => {
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

                    // 日志记录
                    // this.OperatorLogParam.operateContent = JSON.stringify(dataids)
                    // this.OperatorLogParam.operateFeatures = '删除记录'
                    // this.OperatorLogParam.operateType = LogType.Query
                    // this.OperatorLogParam.operateState = '成功'
                    // OperatorLog.setOperationLog(this.OperatorLogParam)
                })
            }).catch(() => {
                ElMessage({
                    type: 'info',
                    message: '已取消删除',
                })
            })
        }
    }

    const queryData = () => {
        data.isSearch = true
        data.pageConfig.currentPage = 1
        getData()
    }

    const resetData = () => {
        // 重置事件
        data.isSearch = false
        for (const key in data.formList) {
            data.formList[key] = ''
        }
    }

    const excelData = () => {
        const params = {}
        Api.excelData4orderinfo(params).then(data => {
            const blob = new Blob([data], { type: 'application/vnd.ms-excel' })
            const blobUrl = URL.createObjectURL(blob)
            const a = document.createElement('a')
            a.href = blobUrl
            a.download = '点单信息.xls'
            a.click()
            window.URL.revokeObjectURL(blobUrl)
        })
    }
    /**
     导入后的回调方法
     @param action: put/delete， put=上传，delete=删除
     @param status: true/false，true=成功，false=失败
     @param groupId: 附件组ID
     @param response: 响应内容
     **/
    const uploadExcelCallback = (action, status, groupId, response) =>{
        console.log("Success")
        // if (action === 'put' && status) {
        //     this.$notify({
        //         type: 'success',
        //         title: '导入成功',
        //         message: response.message,
        //         duration: 5000
        //     })
        // } else {
        //     this.$notify({
        //         type: 'error',
        //         title: '导入失败',
        //         message: response.message,
        //         duration: 5000
        //     })
        // }
    }
    const selectionChanged = (val: number) => {
        // 选中行变化事件
        data.selectedRows = val
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
            Api.del4orderinfo(scope.row.id).then(res => {
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

            // 日志记录
            // this.OperatorLogParam.operateContent = JSON.stringify(dataids)
            // this.OperatorLogParam.operateFeatures = '删除记录'
            // this.OperatorLogParam.operateType = LogType.Query
            // this.OperatorLogParam.operateState = '成功'
            // OperatorLog.setOperationLog(this.OperatorLogParam)
        }).catch(() => {
            ElMessage({
                type: 'info',
                message: '已取消删除',
            })
        })
    }

    const handleCurrentChange = (val: number) => {
        // 页码切换事件
        data.pageConfig.currentPage = val
        getData()
    }

    const handleSizeChange = (val: number) => {
        // 页面条数变化事件
        data.pageConfig.pageSize = val
        getData()
    }

    const orderItemDialog = ref();
    const showOrderItemDialog = (scope) => {
        orderItemDialog.value.init(scope.row.id);
    }


    const activated = () => {
        getData()
    }

</script>
<style lang="css" scoped>
/* 单页面样式 */
>>>.el-table .cell {
  white-space: nowrap
}
</style>
