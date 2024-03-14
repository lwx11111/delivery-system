<template>
    <!--用来解决没有使用框架界面的路由缓存问题-->
    <router-view v-slot="{ Component }" :key="key">
        <keep-alive>
            <component :is="Component" />
        </keep-alive>
    </router-view>
</template>

<script lang="ts" setup>
import { computed } from 'vue'
import {onMounted} from "vue";
import { useStore } from "vuex";
import { useRouter } from 'vue-router'

const store = useStore();
const router = useRouter();

const key = computed(() => {
    return router
})
</script>

<style scoped>

</style>
