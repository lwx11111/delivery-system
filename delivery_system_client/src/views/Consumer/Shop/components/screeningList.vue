<template>
    <div>
        <el-row>
            <el-scrollbar>
                <div class="scrollbar-flex-content">
                    <p v-for="(item, index) in data.screeningList"
                       ref="myElement"
                       class="scrollbar-demo-item"
                       @click="getShopListByScreening(index)">
                        {{ item }}
                    </p>
                </div>
            </el-scrollbar>
        </el-row>
    </div>
</template>

<script lang="ts" setup>
import { reactive, onMounted, ref } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'

const store = useStore();
const router = useRouter()

// Data
const data = reactive({
    // 筛选条件 todo 字典配置
    screeningList: ["高分好评","月售最多","起送价最低","配送费最低","速度最快","距离最近","人均高到低","人均低到高"],
})

// Mounted
onMounted(() => {

})

// Methods
/**
 * todo 根据筛选条件查询店铺
 * @param index
 */
const emit = defineEmits(['getScreeningIndex'])
const myElement = ref()
const getShopListByScreening = (index) => {
    emit('getScreeningIndex',index)
    console.log(myElement)
    // 改变背景颜色
    for (let i = 0; i < myElement.value.length; i++) {
        if (i !== index){
            myElement.value[i].style.backgroundColor = 'white'
        } else {
            myElement.value[index].style.backgroundColor = 'grey'
        }
    }

}


</script>

<style scoped>
.scrollbar-flex-content {
    display: flex;
}
.scrollbar-demo-item {
    flex-shrink: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100px;
    height: 50px;
    margin: 10px;
    text-align: center;
    border-radius: 4px;
    background: white;
    color: var(--el-color-danger);
}
</style>
