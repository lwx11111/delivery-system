<template>
  <div>
      <el-card style="margin: 10px; border: 1px solid gold">
          <h1>
              404
          </h1>
      </el-card>
      <el-card style="margin: 10px; border: 1px solid gold"></el-card>
  </div>
</template>

<script>
import { useStore } from "vuex";
import ItemDialog from "@/views/Merchant/Order/Item.vue";

export default {

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
