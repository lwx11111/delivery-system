<template>
  <div>
      <h1>
          Home Page
      </h1>
  </div>
</template>

<script>
import { useStore } from "vuex";

export default {

    name: "homePage",

    mounted() {
        // State
        const store = useStore();
        console.log(store.state.count)
        store.commit('increment')
        console.log(store.state.count)

        // Getter
        console.log(store.getters['getCount']);

        // Action
        store.dispatch('increment')
    }
}
</script>

<style scoped>

</style>
