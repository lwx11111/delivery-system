<template>

    <div class="sidebar-logo-container" :class="{'collapse':true}">
        <transition name="sidebarLogoFade">
          <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
            <img alt="name" v-if="logo" src="../../assets/icon-logo-small.png" class="sidebar-logo">
            <h1 v-else class="sidebar-title">{{ appName ==='' ? 'LWX' : appName }} </h1>
          </router-link>
          <router-link v-else key="expand" class="sidebar-logo-link" to="/">
              <img alt="name" src="../../assets/icon-logo-small.png" class="sidebar-logo">
              <div style="float: left">
                <h1 class="sidebar-title"> {{appName}} </h1>
                <div class="sub-title">{{ appName }}</div>
              </div>
          </router-link>
        </transition>
    </div>
</template>

<script>
  // import {mapGetters} from 'vuex'

  export default {
    name: 'SidebarLogo',
    props: {
      collapse: {
        type: Boolean,
        required: true
      }
    },
    data() {
      return {
        appName: '',
        logo: 'guo'
      }
    },
    // computed: {
    //   // ...mapGetters([
    //     'appId',
    //     'appName'
    //   ])
    // },
    created() {
        this.appName = this.APP_NAME;
      // getApi(getActiveAppId()).then(response => {
      //   this.app = response.data;
      // this.$store.commit('account/SET_ACCOUNTID', this.account.accountId);
      // })
    }
  }
</script>

<style lang="scss" scoped>
  //@import "../../style/variables.scss";

  .sidebarLogoFade-enter-active {
    transition: opacity 1.5s;
  }

  .sidebarLogoFade-enter,
  .sidebarLogoFade-leave-to {
    opacity: 0;
  }

  .sidebar-logo-container {
    position: relative;
    width: 100%;
    height: 50px;
    /*line-height: 50px;*/
    background: $menuBg;
    /*text-align: center;*/
    overflow: hidden;
    margin-left: 10px;
    margin-top: 10px;

    & .sidebar-logo-link {
      height: 100%;
      width: 100%;

      & .sidebar-logo {
        width: 32px;
        height: 40px;
        vertical-align: bottom;
        float: left;
      }

      & .sidebar-title {
        display: inline-block;
        margin: 0px 0px 0px 10px;
        color: #fff;
        font-weight: 600;
        line-height: 28px;
        font-size: 16px;
        font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
        vertical-align: middle;
      }

      & .sub-title {
        color: rgba(255, 255, 255, 0.65);
        font-size: 13px;
        text-align: center;
        height: 18px;
        line-height: 18px;
        font-style: italic;
        padding-left: 17px;
      }
    }

    &.collapse {
      .sidebar-logo {
        margin-right: 0px;
      }
    }
  }
</style>
