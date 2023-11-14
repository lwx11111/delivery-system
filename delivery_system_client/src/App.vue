<template>
    <div id="app">
        <router-view></router-view>
<!--        <button><router-link to="itproject/index">去文件一</router-link></button>-->
<!--        <router-link to="/login">去文件</router-link>-->
<!--        <router-link to="/re">去文件</router-link>-->

    </div>
</template>
<script>

import Api from "./api/test";

    export default {

        name: "app",
        components: {

        },
        mounted() {
            // axios.get("https://www.baidu.com").then(res => {
            //     this.obj= res.data[0];
            //     console.log(res.data);
            // })
            // // 原生
            // this.$axios.get("http://localhost:9000/order/order/1").then(res => {
            //     this.obj= res.data[0];
            //     console.log(res.data);
            // })
            // // 封装
            // Api.test().then(res => {
            //     console.log(res);
            // })
        }
    }
</script>



<style scoped>

</style>
