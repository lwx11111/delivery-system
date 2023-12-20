<template>
    <div>
        <logo></logo>
        <el-scrollbar wrap-class="scrollbar-wrapper">
            <el-menu
                default-active="1"
                :collapse="data.isCollapse"
                :unique-opened="true"
                :default-openeds="data.openeds"
                :collapse-transition="false"
                mode="vertical"
                active-text-color="#ffd04b"
                background-color="#BCD2EE"
                text-color="#111111"
            >
            <sidebar-item v-for="route in data.indexDate" :key="route.funId.toString()" :item="route" :base-path="route.url"/>
            <sidebar-item v-for="route in data.menuData"
                          :key="route.funId.toString()"
                          :item="route"
                          :base-path="route.url"/>
            </el-menu>
            <img alt="name" src="../../assets/tree.png" class="sidebar-foot">
        </el-scrollbar>

    </div>
</template>

<script lang="js" setup>
    import { useStore } from "vuex";
    import { useRouter } from 'vue-router'
    import Logo from './Logo.vue'
    import SidebarItem from './SidebarItem.vue'
    import {leftMenuApi} from '@/api/function'
    import {onMounted, reactive} from "vue";

    const store = useStore();
    const router = useRouter()

    const data = reactive({
        showLogo: true,
        isCollapse: false,
        openeds: [],
        menuData: [],
        indexDate: [
            {
                'funId': '1',
                'funName': '首页',
                'url': '/homePage',
            }
        ]
    })

    // Mounted
    onMounted(() => {
        leftMenuApi().then(response => {
            // console.log(response)
            // 不加载权限系统的
            for (let i = 0;i < response.data.length; i++){
                if (response.data[i].label !== '权限系统监控' && response.data[i].label !== '系统管理' && response.data[i].label !== '权限管理'){
                    data.menuData.push(response.data[i])
                }
            }
            // this.menuData = response.data;
            //
            // const findFun = this.menuData.find(fun => fun.funName === '');
            // if (findFun !== undefined) {
            //   this.openeds.push(findFun.funId);
            // }
        });
    })
</script>

<style>
.sidebar-foot{
    margin-left: 20%;
    width: 50%;
    height: 50%;
}
</style>
