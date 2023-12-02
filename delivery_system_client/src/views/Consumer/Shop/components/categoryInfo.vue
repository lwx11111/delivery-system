<template>
    <div>
        <el-row>
            <!--父级分类-->
            <el-col @click="toCategoryList(key, true)"
                    :span="4"
                    style="margin: 10px;"
                    v-for="(item,key) in data.parentCategoryList">
                <el-image style="height: 60px;border-radius: 50%"
                          src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"></el-image>
                <div>{{item.name}}</div>
            </el-col>
        </el-row>
        <el-row v-if="props.isQuery">
            <!--子级分类-->
            <el-col
                    @click="toCategoryList(key, false)"
                    :span="4"
                    style="margin: 10px"
                    v-for="(item,key) in data.categoryList">
                <el-image style="height: 40px;border-radius: 50%"
                          src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"></el-image>
                <div>{{item.name}}</div>
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
const store = useStore();
const router = useRouter()
import { defineProps } from 'vue'
import ApiCategory from '../../../../api/api_category.js'

// Props
const props = defineProps({
    parentId: {
        type: Number,
        default: 0,
        required: false
    },
    // 不传值默认全部，传值则显示对应的分类
    categoryId: {
        type: Number,
        default: 0,
        required: false
    },
    // 是否是父分类
    isParent: {
        type: Boolean,
        default: false,
        required: false
    },
    // 是否查询父级
    isQueryParent: {
        type: Boolean,
        default: true,
        required: false
    },
    // 是否查询子级
    isQuery: {
        type: Boolean,
        default: true,
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
    if (props.parentId) {
        getCategoryList();
    } else {
        getParentCategoryList();
        getCategoryList();
    }
    /**
     * 默认大小分类都展示
         * 如果点击子分类，只展示对应店铺
         * 如果点击父分类，展示子分类列表和对应店铺
     * 展示子分类列表，需要传入父分类id
     */
    if (props.isQueryParent) {
        getParentCategoryList();
    }

    if (props.isQuery) {
        getCategoryList();
    }
})

// Methods
/**
 * 父级分类店铺列表
 */
const getParentCategoryList = () => {
    let param = {
        isParentId: true
    }
    if (props.parentId){
        param.categoryId = props.categoryId
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
    if (props.isParentId){
        param.categoryId = props.categoryId
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
const toCategoryList = (key, isParentId) => {
    let id = data.categoryList[key].id
    if (isParentId){
        id = data.parentCategoryList[key].id
    }
    router.push({
        path: '/Consumer/Shop/categoryList',
        query: {
            categoryId: id,
            isParentId: isParentId
        }
    })
}


</script>

<style scoped>

</style>
