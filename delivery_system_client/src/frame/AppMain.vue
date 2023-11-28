<template>
    <router-view v-slot="{ Component }" :key="key">
        <transition name="fade-transform" mode="out-in">
            <keep-alive :include="cachedViews">
                <component :is="Component" />
            </keep-alive>
        </transition>
    </router-view>
</template>

<script lang="ts" setup>
import {ref, computed, reactive} from 'vue'
import {onMounted} from "vue";
import { useStore } from "vuex";
import { useRouter } from 'vue-router'

const store = useStore();
const router = useRouter();

// const data = reactive({
//     cachedViews: []
// })

// Mounted
onMounted(() => {

   //  console.log(store)
   // data.cachedViews = store.state.tagsView.cachedViews
})

const cachedViews = computed(() => {
    return store.state.tagsView.cachedViews
})

const key = computed(() => {
    return router
})
// export default {
//
//   name: 'AppMain',
//     computed: {
//
//     // cachedViews() {
//     //     console.log(store)
//     //   console.log(store.state.tagsView.cachedViews)
//     //   return store.state.tagsView.cachedViews
//     // },
//     key() {
//       console.log(router)
//       return router
//     }
//   }
// }
</script>

<style lang="scss" scoped>
.app-main {
  /* 50= navbar  50  */
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
}

.fixed-header+.app-main {
  padding-top: 50px;
}

.hasTagsView {
  .app-main {
    /* 84 = navbar + tags-view = 50 + 34 */
    min-height: calc(100vh - 84px);
  }

  .fixed-header+.app-main {
    padding-top: 84px;
  }
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 15px;
  }
}
</style>
