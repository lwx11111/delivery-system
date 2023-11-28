<template>
    <el-dialog v-model="data.showDialog"
               destroy-on-close
               width="95%"
               top="5vh"
               title="物品信息">
        <el-card style="border: 1px solid gold;"
                 class="box-card"
                 shadow="never">
            <el-form label-width="120px">
                <div v-for="(item,index) in data.shopItems">
                    <el-card style="margin-bottom: 10px">
                        <el-row>
                            <el-col :span="6">
                                <el-form-item label="分类名">
                                    <el-input v-model="item.categoryName"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-button type="danger"
                                           @click="deleteShopItem(index)">
                                    删除分类
                                </el-button>
                            </el-col>
                        </el-row>
                        <el-divider></el-divider>
                        <div v-for="(it,i) in item.items">
                            <el-row>
                                <!-- todo -->
                                <!-- {{it.picture}}-->
                                <el-col :span="6">
                                    <el-form-item label="物品名">
                                        <el-input v-model="it.name"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="6">
                                    <el-form-item label="物品价格">
                                        <el-input-number v-model="it.price"></el-input-number>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="6">
                                    <el-form-item label="物品描述">
                                        <el-input v-model="it.description" type="textarea"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="6">
                                    <el-button type="danger"
                                               @click="deleteItem(index,i)">
                                        删除物品
                                    </el-button>
                                </el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-row v-if="i === item.items.length - 1 || item.items.length === 0" >
                                <el-form-item>
                                    <el-button type="primary"
                                               @click="addItem(index)">
                                        添加新物品
                                    </el-button>
                                </el-form-item>
                            </el-row>
                        </div>
                    </el-card>

                    <el-row v-if="index === data.shopItems.length - 1">
                        <el-form-item>
                            <el-button type="primary"
                                       @click="addShopItem()">
                                添加新分类
                            </el-button>
                        </el-form-item>
                    </el-row>
                </div>
            </el-form>
            <el-button type="primary"
                       @click="submitForm()">
              保存
            </el-button>
            <el-button @click="back()">
              返回
            </el-button>
        </el-card>
    </el-dialog>
</template>
<script lang="ts" setup>
    import Api from '@/api/api_shop.js'
    import { reactive, ref, onMounted } from 'vue'
    import { useStore } from "vuex";
    import { useRouter } from 'vue-router'
    import { ElMessage } from "element-plus";
    import StringUtil from '@/utils/stringUtil.js'

    const store = useStore();
    const router = useRouter()

    // Data
    const data = reactive({
        backUrl: '/name/shopitem/index',
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

    // 表单ref
    const itemForm = ref();
    const submitForm = () => {
        console.log(data.shopItems)
        // 表单验证
        for (let i = 0; i < data.shopItems.length; i++){
            if (StringUtil.isEmpty(data.shopItems[i].categoryName)){
                ElMessage({
                  message: '分类名不能为空',
                  type: 'warning',
                })
                return;
            }
            for (let j = 0; j < data.shopItems[i].items.length; j++ ){
                if (StringUtil.isEmpty(data.shopItems[i].items[j].name)){
                    ElMessage({
                      message: '物品名不能为空',
                      type: 'warning',
                    })
                    return;
                }
                if (StringUtil.isEmpty(data.shopItems[i].items[j].price)){
                    ElMessage({
                      message: '物品价格不能为空',
                      type: 'warning',
                    })
                    return;
                }
            }
        }
        // 保存或更新操作
        saveOrUpdate();
    }

    const resetForm = () => {
        // 重置按钮
        itemForm.value.resetFields();
    }

    const saveOrUpdate = () => {
        console.log(data.shopItems)
        // 保存或更新操作
        Api.saveShopItems(data.shopItems).then(res => {
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
    const addShopItem = () =>{
        const item = {
            categoryName: '1',
            items: [
                {
                    id: '',
                    shopId: data.shopId,
                    name: '',
                    picture: '',
                    description: '',
                    price: '',
                    categoryName:'',
                }
            ]
        }
        data.shopItems.push(item)
    }

    const addItem = (index) =>{
        const item = {
            id: '',
            shopId: data.shopId,
            name: '',
            picture: '',
            description: '',
            price: '',
            categoryName:'',
        }
        data.shopItems[index].items.push(item)
    }

    const deleteShopItem = (index) =>{
        if (data.shopItems.length === 1){
            ElMessage({
              message: '至少保留一个分类',
              type: 'warning',
            })
            return;
        }
        data.shopItems.splice(index,1);
    }

    const deleteItem = (index,i) =>{
        if (data.shopItems[index].items.length === 1){
            ElMessage({
              message: '至少保留一个物品',
              type: 'warning',
            })
            return;
        }
        data.shopItems[index].items.splice(i,1);
    }

    //暴露state和play方法
    defineExpose({
        init,
    });
</script>
