<template>
    <el-dialog v-model="data.showDialog"
               destroy-on-close
               width="95%"
               top="5vh"
               title="物品信息">
        <el-card style="border: 1px solid gold;"
                 class="box-card"
                 shadow="never">
            <div v-for="(item,index) in data.orderItems">
                <el-row>
                    <el-col :span="3">
                        <el-image style="height: 100px" :src="item.shopItem.picture"></el-image>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="物品名">
                            <el-input v-model="item.shopItem.name"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="物品描述">
                            <el-input v-model="item.shopItem.description" type="textarea"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="物品数量">
                            <el-input v-model="item.amount"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </div>
        </el-card>
    </el-dialog>
</template>
<script lang="ts" setup>
    import Api from '@/api/Order/api_orderinfo.js'
    import { reactive, ref, onMounted } from 'vue'
    import { useStore } from "vuex";
    import { useRouter } from 'vue-router'
    import { ElMessage } from "element-plus";

    const store = useStore();
    const router = useRouter()

    // Data
    const data = reactive({
        orderId: 0,
        orderItems: [
            {
                amount:'',
                shopItem: {
                    id: '',
                    shopId: '',
                    name: '',
                    picture: '',
                    description: '',
                    price: '',
                    categoryName:'',
                }
            }
        ],
        showDialog: false,
    })

    // Mounted
    onMounted(() => {

    })

    // Methods
    const init = (id) => {
        // 界面初始化接收参数
        data.orderId = id;
        const param = {
            id: id
        }
        Api.listOrderItemById(param).then(res => {
            console.log(res)
            if (res.code === 200){
                data.orderItems = res.data;
            } else {
                ElMessage({
                  message: '获取数据失败，请重试',
                  type: 'warning',
                })
                return;
            }
        })

        // 界面显示
        data.showDialog = true;
    }

    //暴露state和play方法
    defineExpose({
        init,
    });
</script>
