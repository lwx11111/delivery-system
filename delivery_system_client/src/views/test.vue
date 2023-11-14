<template>

</template>

<script>
export default {
    name: "test",
    mounted() {
        this.$axios.get("https://www.baidu.com").then(res => {
            this.obj= res.data[0];
            console.log(res.data);
        })
    }
}
</script>

<style scoped>

</style>
