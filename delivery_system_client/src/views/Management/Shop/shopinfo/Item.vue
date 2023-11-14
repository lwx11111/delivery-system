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
                    label="userId"
                    prop="userId">
              <el-input
                      v-model="data.item.userId"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="店铺名"
                    prop="name">
              <el-input
                      v-model="data.item.name"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="省"
                    prop="province">
              <el-input
                      v-model="data.item.province"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="县"
                    prop="county">
              <el-input
                      v-model="data.item.county"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item
                    label="具体地址"
                    prop="location">
              <el-input
                      v-model="data.item.location"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="店铺评分"
                    prop="score">
              <el-input
                      v-model="data.item.score"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="销量"
                    prop="salesVolume">
              <el-input
                      v-model="data.item.salesVolume"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="起送价格"
                    prop="minPrice">
              <el-input
                      v-model="data.item.minPrice"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
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
                    label="安全档案图片"
                    prop="safetyFile">
              <el-input
                      v-model="data.item.safetyFile"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="开门时间"
                    prop="openTime">
              <el-input
                      v-model="data.item.openTime"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="关门时间"
                    prop="closeTime">
              <el-input
                      v-model="data.item.closeTime"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
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
          <el-col :span="6">
            <el-form-item
                    label="店铺描述"
                    prop="description">
              <el-input
                      v-model="data.item.description"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="物品分类"
                    prop="itemCategory">
              <el-input
                      v-model="data.item.itemCategory"
                      :disabled="data.disabled">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
                    label="店铺图片路径"
                    prop="picture">
              <el-input
                      v-model="data.item.picture"
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
  import Api from '../../api/api_shop.js'
  import { reactive, ref, onMounted, toRefs } from 'vue'
  import { useStore } from "vuex";
  import { useRouter } from 'vue-router'
  import {ElMessage, ElMessageBox} from "element-plus";

  const store = useStore();
  const router = useRouter()

  // Data
  const data = reactive({
    operateTitle: '新增',
    backUrl: '/name/shop/index',
    type: '',
    showBtn: true,
    disabled: false,
    id: 0,
    item: {},
    params: {
        id: '',
        userId: '',
        name: '',
        province: '',
        county: '',
        location: '',
        score: '',
        salesVolume: '',
        minPrice: '',
        deliveryCharge: '',
        safetyFile: '',
        openTime: '',
        closeTime: '',
        status: '',
        description: '',
        itemCategory: '',
        picture: ''
    },
    OperatorLogParam: {
      operateContent: '',
      operateFeatures: '',
      operateState: '',
      operateType: ''
    },
    showDialog: false,
    rules: {
        userId: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '店铺名不能为空', trigger: 'blur' }
        ],
        province: [
          { required: true, message: '省不能为空', trigger: 'blur' }
        ],
        county: [
          { required: true, message: '县不能为空', trigger: 'blur' }
        ],
        location: [
          { required: true, message: '具体地址不能为空', trigger: 'blur' }
        ],
        score: [
          { required: true, message: '店铺评分不能为空', trigger: 'blur' }
        ],
        salesVolume: [
          { required: true, message: '销量不能为空', trigger: 'blur' }
        ],
        minPrice: [
          { required: true, message: '起送价格不能为空', trigger: 'blur' }
        ],
        deliveryCharge: [
          { required: true, message: '配送费不能为空', trigger: 'blur' }
        ],
        safetyFile: [
          { required: true, message: '安全档案图片不能为空', trigger: 'blur' }
        ],
        openTime: [
          { required: true, message: '开门时间不能为空', trigger: 'blur' }
        ],
        closeTime: [
          { required: true, message: '关门时间不能为空', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '状态不能为空', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '店铺描述不能为空', trigger: 'blur' }
        ],
        itemCategory: [
          { required: true, message: '物品分类不能为空', trigger: 'blur' }
        ],
        picture: [
          { required: true, message: '店铺图片路径不能为空', trigger: 'blur' }
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
      Api.sel4shop(data.id).then(res => {
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
      Api.update4shop(data.id, data.item).then(res => {
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
      Api.add4shop(data.item).then(res => {
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
