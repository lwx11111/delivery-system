<template>
    <div class="common-layout">
        <el-container>
            <el-header style="border: 1px solid red">
                <!--     default-active="/Consumer/index"           -->
                <el-menu router
                         @close="close"
                         default-active="/Consumer/index"
                         @select="handleSelect"
                         @open="open"
                         mode="horizontal">
                    <el-menu-item index="/Consumer/index">订单</el-menu-item>
                    <el-menu-item index="/Consumer/Personal/index">我的</el-menu-item>
                </el-menu>
            </el-header>
            <el-container>
                <el-aside width="200px"></el-aside>
                <el-container>
                    <el-main style="background: #FFFAFA">
                        <router-view v-slot="{ Component }" :key="key" >
                            <transition name="fade-transform" mode="out-in">
                                <keep-alive :include="cachedViews">
                                    <component :is="Component" />
                                </keep-alive>
                            </transition>
                        </router-view>
                    </el-main>
                </el-container>
                <el-aside width="200px"></el-aside>
            </el-container>
        </el-container>
        <el-footer style="border: 1px solid red">Footer</el-footer>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, toRefs } from 'vue'
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
}

</script>

<style scoped>

</style>
