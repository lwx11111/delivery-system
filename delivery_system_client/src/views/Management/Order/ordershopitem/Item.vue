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
                    label="orderId"
                    prop="orderId">
              <el-input
                      v-model="data.item.orderId"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="shopItemId"
                    prop="shopItemId">
              <el-input
                      v-model="data.item.shopItemId"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="数量"
                    prop="amount">
              <el-input
                      v-model="data.item.amount"
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
  import Api from '../../api/api_ordershopitem.js'
  import { reactive, ref, onMounted, toRefs } from 'vue'
  import { useStore } from "vuex";
  import { useRouter } from 'vue-router'
  import {ElMessage, ElMessageBox} from "element-plus";

  const store = useStore();
  const router = useRouter()

  // Data
  const data = reactive({
    operateTitle: '新增',
    backUrl: '/name/ordershopitem/index',
    type: '',
    showBtn: true,
    disabled: false,
    id: 0,
    item: {},
    params: {
        id: '',
        orderId: '',
        shopItemId: '',
        amount: ''
    },
    OperatorLogParam: {
      operateContent: '',
      operateFeatures: '',
      operateState: '',
      operateType: ''
    },
    showDialog: false,
    rules: {
        orderId: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        shopItemId: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        amount: [
          { required: true, message: '数量不能为空', trigger: 'blur' }
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
      Api.sel4ordershopitem(data.id).then(res => {
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
      Api.update4ordershopitem(data.id, data.item).then(res => {
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
      Api.add4ordershopitem(data.item).then(res => {
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
