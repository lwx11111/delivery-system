<template>
  <el-dialog
          v-model="data.showDialog"
          width="90%"
          :title="data.operateTitle">
    <el-card style="border: 1px solid gold;"
             class="box-card"
             shadow="never">
      <el-form
              :model="data.item"
              :rules="data.rules"
              ref="itemForm"
              label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item
                    label="店铺ID"
                    prop="shopId">
              <el-input
                      v-model="data.item.shopId"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="配送骑手"
                    prop="deliveryRiderId">
              <el-input
                      v-model="data.item.deliveryRiderId"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="用户ID"
                    prop="userId">
              <el-input
                      v-model="data.item.userId"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="物品JSON"
                    prop="shopItem">
              <el-input
                      v-model="data.item.shopItem"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item
                    label="打包费"
                    prop="packingCharges">
              <el-input
                      v-model="data.item.packingCharges"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="配送费"
                    prop="deliveryCharge">
              <el-input
                      v-model="data.item.deliveryCharge"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="总金额"
                    prop="totalCharge">
              <el-input
                      v-model="data.item.totalCharge"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="期望时间"
                    prop="expectedTime">
              <el-input
                      v-model="data.item.expectedTime"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item
                    label="配送地址"
                    prop="location">
              <el-input
                      v-model="data.item.location"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="配送服务"
                    prop="deliveryService">
              <el-input
                      v-model="data.item.deliveryService"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="下单时间"
                    prop="orderTime">
              <el-input
                      v-model="data.item.orderTime"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="状态"
                    prop="status">
              <el-input
                      v-model="data.item.status"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item
                    label="支付方式"
                    prop="paymentMethod">
              <el-input
                      v-model="data.item.paymentMethod"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="备注"
                    prop="remark">
              <el-input
                      v-model="data.item.remark"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="餐具数量"
                    prop="tableware">
              <el-input
                      v-model="data.item.tableware"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item>
          <el-button
                  v-show="data.showBtn"
                  type="primary"
                  @click="submitForm('itemForm')">
            保存
          </el-button>
          <el-button
                  v-show="data.showBtn"
                  @click="resetForm()">
            重置
          </el-button>
          <el-button @click="back()">
            返回
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </el-dialog>
</template>
<script lang="ts" setup>
  import Api from '@/api/api_orderinfo.js'
  import { reactive, ref, onMounted, toRefs } from 'vue'
  import { useStore } from "vuex";
  import { useRouter } from 'vue-router'
  import {ElMessage, ElMessageBox} from "element-plus";

  const store = useStore();
  const router = useRouter()

  // Data
  const data = reactive({
    operateTitle: '新增',
    backUrl: '/name/orderinfo/index',
    type: '',
    showBtn: true,
    disabled: false,
    id: 0,
    item: {},
    params: {
        id: '',
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
    OperatorLogParam: {
      operateContent: '',
      operateFeatures: '',
      operateState: '',
      operateType: ''
    },
    showDialog: false,
    rules: {
        shopId: [
          { required: true, message: '店铺ID不能为空', trigger: 'blur' }
        ],
        deliveryRiderId: [
          { required: true, message: '配送骑手不能为空', trigger: 'blur' }
        ],
        userId: [
          { required: true, message: '用户ID不能为空', trigger: 'blur' }
        ],
        shopItem: [
          { required: true, message: '物品JSON不能为空', trigger: 'blur' }
        ],
        packingCharges: [
          { required: true, message: '打包费不能为空', trigger: 'blur' }
        ],
        deliveryCharge: [
          { required: true, message: '配送费不能为空', trigger: 'blur' }
        ],
        totalCharge: [
          { required: true, message: '总金额不能为空', trigger: 'blur' }
        ],
        expectedTime: [
          { required: true, message: '期望时间不能为空', trigger: 'blur' }
        ],
        location: [
          { required: true, message: '配送地址不能为空', trigger: 'blur' }
        ],
        deliveryService: [
          { required: true, message: '配送服务不能为空', trigger: 'blur' }
        ],
        orderTime: [
          { required: true, message: '下单时间不能为空', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '状态不能为空', trigger: 'blur' }
        ],
        paymentMethod: [
          { required: true, message: '支付方式不能为空', trigger: 'blur' }
        ],
        remark: [
          { required: true, message: '备注不能为空', trigger: 'blur' }
        ],
        tableware: [
          { required: true, message: '餐具数量不能为空', trigger: 'blur' }
        ]
    },
  })

  // Props
  const props = defineProps({
    //子组件接收父组件传递过来的值
    type: {
      type: String,
      default: '新增'
    },
  })

  // Mounted
  onMounted(() => {

  })

  // Methods
  const init = (id, type) => {
    // 界面初始化接收参数
    data.type = type;
    switch (data.type) {
      case 'add':
        data.operateTitle = '新增'
        break
      case 'detail':
        data.operateTitle = '详情'
        data.showBtn = false
        data.disabled = true
        break
      case 'update':
        data.operateTitle = '修改'
        break
    }

    // 获取数据
    if (data.type === 'detail' || data.type === 'update') {
      // ID校验
      if (id != null && id != '') {
        data.id = id
      } else {
        ElMessage({
          message: '数据ID丢失，请重试',
          type: 'warning',
        })
        return;
      }
      // 发送请求
      Api.sel4orderinfo(data.id).then(res => {
        console.log(res)
        if (res.code === 200){
          data.item = res.data;
          // 界面显示
          data.showDialog = true;
        } else {
          ElMessage({
            message: '获取数据失败，请重试',
            type: 'warning',
          })
          return;
        }
      })
    } else {
      // 界面显示
      data.showDialog = true;
    }

    //菜单界面生成时日志记录
    // const islog = Vue.prototype.$config.ISLOG;
    // if (true==islog){
    //     data.OperatorLogParam.operateFeatures = '详情表单'
    //     data.OperatorLogParam.operateType = LogType.Query
    //     data.OperatorLogParam.operateState = '成功'
    //     OperatorLog.setOperationLog(data.OperatorLogParam)
    // }

  }
  const back = () => {
    // 返回操作
    data.showDialog = false;
    location.reload()
    // router.push("/logs/account-change-pass-log");
  }

  // 表单ref
  const itemForm = ref();
  const submitForm = (formName) => {
    // 表单验证
    itemForm.value.validate((valid, fields) => {
      if (valid) {
        saveOrUpdate();
      } else {
        ElMessage({
          message: '请校验',
          type: 'warning',
        })
      }
    })
  }

  const resetForm = () => {
    // 重置按钮
    itemForm.value.resetFields();
  }

  const saveOrUpdate = () => {
    // 保存或更新操作
    if (data.type === 'update') {
      Api.update4orderinfo(data.id, data.item).then(res => {
        if (res.code === 200){
          ElMessage({
            message: '修改成功',
            type: 'success',
          })
          back()
        } else {
          ElMessage({
            message: '修改失败',
            type: 'warning',
          })
        }
      })
    } else if (data.type === 'add') {
      Api.add4orderinfo(data.item).then(res => {
        console.log(res)
        if (res.code === 200){
          ElMessage({
            message: '保存成功',
            type: 'success',
          })
          back();
        } else {
          ElMessage({
            message: '保存失败',
            type: 'warning',
          })
        }
      })
    }
  }

  //暴露state和play方法
  defineExpose({
    init,
  });
</script>
