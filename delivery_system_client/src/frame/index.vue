<template>
    <el-container>
        <el-aside width="200px">
            <Sidebar class="sidebar-container"></Sidebar>
        </el-aside>
        <el-container>
            <el-header style="border-bottom: 1px solid gold">
                <Navbar></Navbar>
            </el-header>
            <el-main>
                <AppMain></AppMain>
            </el-main>
        </el-container>
    </el-container>
</template>

<script lang="ts" setup>
    import Navbar from './Navbar.vue';
    import Sidebar from './Sidebar/index.vue';
    import AppMain from "./AppMain.vue";
</script>

<style>
.sidebar-container {
    transition: width 0.28s;
    width: 210px;
    background-color: #BCD2EE;
    height: 100%;
    position: fixed;
    font-size: 0px;
    top: 0;
    bottom: 0;
    left: 0;
    z-index: 1001;
    overflow: hidden;
}
</style>




