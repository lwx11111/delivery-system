<template>
    <div class="common-layout">
        <el-container>
            <el-aside width="200px">
                <Sidebar class="sidebar-container"></Sidebar>
            </el-aside>
            <el-container>
                <el-header style="border-bottom: 1px solid gold">
                    <Navbar style="height: 70%"></Navbar>
                    <TagsView style="height: 30%"></TagsView>
                </el-header>
                <el-main>
                    <AppMain></AppMain>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script lang="ts" setup>
    import Navbar from './Navbar.vue';
    import TagsView from './TagsView.vue';
    import Sidebar from './Sidebar/index.vue';
    import AppMain from "./AppMain.vue";
</script>


