<template>
    <div>
        <logo v-if="showLogo" :collapse="isCollapse"/>
        <el-scrollbar wrap-class="scrollbar-wrapper">
<!--            background-color="variables.menuBg"-->
<!--            text-color="variables.menuText"-->
            <el-menu
                :default-active="activeMenu"
                :collapse="isCollapse"
                :unique-opened="true"
                :default-openeds="openeds"
                :collapse-transition="false"
                mode="vertical"
                active-text-color="#ffd04b"
                background-color="#545c64"
                text-color="#fff"
            >
              <sidebar-item v-for="route in indexDate" :key="route.funId.toString()" :item="route" :base-path="route.url"/>
              <sidebar-item v-for="route in menuData" :key="route.funId.toString()" :item="route" :base-path="route.url"/>
            </el-menu>
        </el-scrollbar>
    </div>
</template>
<script>
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import { mapState } from 'vuex'
import { onBeforeRouteUpdate } from "vue-router";
const store = useStore();
const router = useRouter()
    // import {mapGetters} from 'vuex'
    import Logo from './Logo.vue'
    import SidebarItem from './SidebarItem.vue'
    // import variables from '@/styles/variables.scss'
    import {leftMenuApi} from '@/api/function'
import useMapState from "@/store/useMapState";

    export default {
        setup() {
            // 1.直接使用
            // const sidebar= computed(() => store.state.counter)

            // 2.封装
            const myState = useMapState({
                sidebar: state => state.app.sidebar,
                appId: state => state.app.appId,
                accountId: state => state.settings.accountId,
                // fixedHeader: state => state.settings.fixedHeader
            })
            return {
                ...myState
            }
        },
        components: {SidebarItem, Logo},
        data() {
            return {
                showLogo: true,
                isCollapse: false,
                openeds: [],
                menuData: [],
                indexDate: [
                    {
                        'funId': '1',
                        'funName': '首页',
                        'url': '/homePage',
                        // 'icon': '&#xe720;'
                    },
                    {
                        'funId': '1',
                        'funName': '分类管理',
                        'url': '/Management/category/index',
                        // 'icon': '&#xe720;'
                    },
                    {
                        'funId': '1',
                        'funName': '收藏管理',
                        'url': '/Management/collection/index',
                        // 'icon': '&#xe720;'
                    },
                    {
                        'funId': '1',
                        'funName': '订单管理',
                        'url': '/Management/orderinfo/index',
                        // 'icon': '&#xe720;'
                    },
                    {
                        'funId': '1',
                        'funName': '消费端',
                        'url': '/Consumer/index',
                        // 'icon': '&#xe720;'
                    }
                ]
            }
        },
        computed: {
          // ...mapGetters([
          //   'sidebar',
          //   'appId',
          //   'accountId'
          // ]),
          activeMenu() {
            // const route = this.$route
            // const {meta, path} = route
            // if set path, the sidebar will highlight the path you set
            // if (meta.activeMenu) {
            //   return meta.activeMenu
            // }
            return '1';
          },
          // showLogo() {
          //   // return this.$store.state.settings.sidebarLogo
          // },
          variables() {
            // return variables
          },
          isCollapse() {
              return false;
            // return !this.sidebar.opened
          }
        },
        mounted() {
            leftMenuApi().then(response => {
                this.menuData = response.data;
                const findFun = this.menuData.find(fun => fun.funName === '系统管理');
                if (findFun !== undefined) {
                  this.openeds.push(findFun.funId);
                }
                //实现自动创建路由并加入到路由中
            });
        }
    }
</script>
