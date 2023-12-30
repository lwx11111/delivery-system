<template>
    <div>
        <el-row>
            <!--父级分类-->
            <el-col v-if="props.categoryId === '0' "
                    @click="toCategoryList(key, true)"
                    :span="4"
                    style="margin: 10px;"
                    v-for="(item,key) in data.parentCategoryList">
                <el-image style="height: 60px;border-radius: 50%"
                          :src="item.picture"></el-image>
                <div>{{item.name}}</div>
            </el-col>
        </el-row>
        <el-row>
            <!--子级分类-->
            <el-col v-if="props.categoryId === '0' "
                    @click="toCategoryList(key, false)"
                    :span="4"
                    style="margin: 10px"
                    v-for="(item,key) in data.categoryList">
                <el-image style="height: 40px;border-radius: 50%"
                          :src="item.picture"></el-image>
                <div>{{item.name}}</div>
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts" setup>
import { reactive, onMounted } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import { defineProps } from 'vue'
import ApiCategory from '@/api/Shop/api_category.js'

const store = useStore();
const router = useRouter()

// Props 没用
const props = defineProps({
    // 传0默认全部，传值则显示对应的分类
    categoryId: {
        type: String,
        default: '0',
        required: false
    },
})

// Data
const data = reactive({
    // 父级分类
    parentCategoryList: [],
    // 小分类
    categoryList: [],
})

// Mounted
onMounted(() => {
    getCategoryList()
    getParentCategoryList();
})

// Methods
/**
 * 父级分类店铺列表
 */
const getParentCategoryList = () => {
    let param = {
        isParentId: true
    }
    ApiCategory.selpage4category(param).then(res => {
        if (res.code === 200){
            data.parentCategoryList = res.data.records;
        }
    })
}

/**
 * 子级分类店铺列表
 */
const getCategoryList = () => {
    let param = {
        isParentId: false
    }
    ApiCategory.selpage4category(param).then(res => {
        if (res.code === 200){
            data.categoryList = res.data.records;
        }
    })
}

/**
 * 跳转到分类店铺列表
 */
const toCategoryList = (key, isParent) => {
    let id = '';
    if (isParent){
        id = data.parentCategoryList[key].id
    } else {
        id = data.categoryList[key].id
    }
    router.push({
        path: '/Consumer/Shop/categoryList',
        query: {
            categoryId: id,
            isParentId: isParent
        }
    })
}
</script>

<style scoped>

</style>
