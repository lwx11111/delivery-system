<template>
    <div class="common-layout">
        <el-container>
            <el-header>
                <el-menu router
                         @close="close"
                         default-active="/Consumer/index"
                         @select="handleSelect"
                         @open="open"
                         mode="horizontal">
                    <el-menu-item index="/Consumer/index">外卖</el-menu-item>
<!--                    <el-sub-menu index="2">-->
<!--                        <template #title>Workspace</template>-->
<!--                        <el-menu-item index="/">会员</el-menu-item>-->
<!--                    </el-sub-menu>-->
                    <el-menu-item index="/Consumer/Order/index">订单</el-menu-item>
                    <el-menu-item index="/Consumer/Personal/index">我的</el-menu-item>
                </el-menu>
            </el-header>
            <el-container>
                <el-aside width="200px"></el-aside>
                <el-container>
                    <el-main style="background: #FFFAFA">
                        <router-view v-slot="{ Component }" :key="key" >
                            <keep-alive>
                                <component :is="Component" />
                            </keep-alive>
                        </router-view>
                    </el-main>
                </el-container>
                <el-aside width="200px"></el-aside>
            </el-container>
        </el-container>
        <el-footer class="footer">
            <h3>
                备案信息：鲁ICP备2022023259号-1
            </h3>
        </el-footer>
    </div>
</template>

<script lang="ts" setup>
import {reactive, ref, onMounted, toRefs, computed} from 'vue'
import { useStore } from "vuex";
import { useRouter, useRoute } from 'vue-router'


const store = useStore();
const router = useRouter()
const route = useRoute()


// Data
const data = reactive({

})

// Mounted
onMounted(() => {

})

// Methods
const open = (key:string, keyPath: string[]) => {
    console.log(key, keyPath);
}

const close = (key:string, keyPath: string[]) => {
    console.log(key, keyPath);
}

const handleSelect = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
    let path = keyPath[0];
    router.push({
        path: path,
    })
}

const key = computed(() => {
    return router
})

</script>

<style scoped>
.footer {
    /*position: fixed;*/
    left: 0px;
    bottom: 0px;
    width: 100%;
    height: 50px;
    z-index: 9999;
    text-align: center;
    border-top: 1px beige solid
}
</style>
