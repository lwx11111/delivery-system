<template>
    <el-dialog v-model="data.showDialog"
               destroy-on-close
               width="95%"
               top="5vh"
               title="物品信息">
        <el-card style="border: 1px solid gold;"
                 class="box-card"
                 shadow="never">
            <div v-for="(item,index) in data.shopItems[0].items">
                <el-row>
                    <el-col :span="3">
                        <el-image style="height: 100px" :src="item.picture"></el-image>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="物品名">
                            <el-input :disabled="true" v-model="item.name"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="物品价格">
                            <el-input-number :disabled="true" v-model="item.price"></el-input-number>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="物品描述">
                            <el-input :disabled="true" v-model="item.description" type="textarea"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </div>
            <el-button @click="back()">
              返回
            </el-button>
        </el-card>
    </el-dialog>
</template>
<script lang="ts" setup>
    import Api from '@/api/Shop/api_shop.js'
    import { reactive, ref, onMounted } from 'vue'
    import { useStore } from "vuex";
    import { useRouter } from 'vue-router'
    import { ElMessage } from "element-plus";

    const store = useStore();
    const router = useRouter()

    // Data
    const data = reactive({
        shopId: 0,
        shopItems: [
            {
                categoryName: '',
                items: [
                    {
                        id: '',
                        shopId: '',
                        name: '',
                        picture: '',
                        description: '',
                        price: '',
                        categoryName:'',
                    }
                ]
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
        data.shopId = id;
        // 发送请求
        Api.listShopItemsByShopId(data.shopId).then(res => {
            console.log(res)
            if (res.code === 200){
                if (res.data.length === 0){
                    data.shopItems[0].items[0].shopId = id;
                } else {
                    data.shopItems = res.data;
                }
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
    const back = () => {
        // 返回操作
        data.showDialog = false;
        location.reload();
    }

    //暴露state和play方法
    defineExpose({
        init,
    });
</script>
