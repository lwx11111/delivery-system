<template>
<!--  :class="classObj"-->
    <div  class="app-wrapper" v-if="isHeader==='1'">
        <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside"/>
        <sidebar style="border: 1px solid green" class="sidebar-container"/>
        <div  :class="{hasTagsView:needTagsView}" class="main-container">
<!--            :class="{'fixed-header':fixedHeader}"-->
            <div >
<!--                <navbar id="navbar"/>-->
<!--                <tags-view id="tagsview" v-if="needTagsView"/>-->
            </div>
            <app-main style="border: 1px solid green"/>
        </div>
    </div>
    <app-main style="border: 1px solid red" v-else/>
</template>

<script>
  import { useStore } from "vuex";
  import { useRouter } from 'vue-router'
  import { mapState } from 'vuex'
  import { onBeforeRouteUpdate } from "vue-router";
  const store = useStore();
  const router = useRouter()
  import useMapState from "@/store/useMapState";
  import {AppMain, Navbar, Sidebar, TagsView } from './index'

  onBeforeRouteUpdate((val, oldVal) => {
      const query = val.query
      this.isHeader = !query.hasOwnProperty('isHeader') ? '1' : query.isHeader

  });
  export default {
      setup() {
          // 1.直接使用
          // const sidebar= computed(() => store.state.counter)

          // 2.封装
          const myState = useMapState({
              sidebar: state => state.app.sidebar,
              device: state => state.app.device,
              showSettings: state => state.settings.showSettings,
              // fixedHeader: state => state.settings.fixedHeader
          })
          return {
              ...myState
          }
      },
    name: 'Layout',
    components: {
      AppMain,
      Navbar,
      Sidebar,
      TagsView
    },
    // mixins: [ResizeMixin],
    data() {
      return {
        isHeader: '1',
        needTagsView: true
      }
    },
    computed: {
      // classObj() {
      //   return {
      //     hideSidebar: !this.sidebar.opened,
      //     openSidebar: this.sidebar.opened,
      //     withoutAnimation: this.sidebar.withoutAnimation,
      //     mobile: this.device === 'mobile'
      //   }
      // }
    },
    watch: {

    },
    mounted(){
      // if (document.getElementById('navbar')) {
      //   document.getElementById('tagsview').style.setProperty('margin-top', '50px', 'important');
      // } else {
      //   document.getElementById('tagsview').style.setProperty('margin-top', '0px', 'important');
      // }
    },
    methods: {

      handleClickOutside() {
        store.dispatch('app/closeSideBar', {withoutAnimation: false})
      }
    }
  }
</script>

<style lang="scss" scoped>
  //@import "~@/styles/mixin.scss";
  //@import "~@/styles/variables.scss";

  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;
    /*overflow: hidden;*/

    &.mobile.openSidebar {
      position: fixed;
      top: 0;
    }
  }

  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }

  .fixed-header {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 9;
    width: calc(100% - #{$sideBarWidth});
    transition: width 0.28s;
  }

  .hideSidebar .fixed-header {
    width: calc(100% - 54px)
  }

  .mobile .fixed-header {
    width: 100%;
  }
</style>
