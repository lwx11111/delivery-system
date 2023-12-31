<template>
  <div>
      <el-card style="margin: 10px; border: 1px solid gold">
          <h1>
              首页
          </h1>
          作者：刘文轩
          联系方式：13806436774
      </el-card>
  </div>
</template>

<script>
import { useStore } from "vuex";
import ItemDialog from "@/views/Merchant/Order/Item.vue";

export default {

    name: "homePage",
    components: {ItemDialog},

    mounted() {
        // // State
        // const store = useStore();
        // console.log(store.state.count)
        // store.commit('increment')
        // console.log(store.state.count)
        //
        // // Getter
        // console.log(store.getters['getCount']);
        //
        // // Action
        // store.dispatch('increment')
    }
}
</script>

<style scoped>

</style>
