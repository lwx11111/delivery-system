<template>
    <div>
        <!-- 一级分类 -->
        <el-sub-menu
            class="myMenu"
            v-if="item.children && item.children.length > 0"
            :key="item.funId"
            :index="item.funId.toString()"
            teleported>
            <template #title>
                <el-icon><document /></el-icon>
                <span>{{item.funName}}</span>
            </template>
            <router-link v-for="(citem,cindex) in item.children" :key="cindex" :to="resolvePath(citem.url)">
                <el-menu-item-group :index="citem.url">
                    <template #title>
                        <el-icon><location /></el-icon>
                        {{ citem.funName }}
                    </template>
                </el-menu-item-group>
            </router-link>
        </el-sub-menu>
        <router-link
                class="myMenu"
                v-else-if="item.children==null || item.children.length == 0"
                :key="item.funId.toString()"
                :to="resolvePath(item.url)"
                :index="item.funId.toString()">
            <el-menu-item :index="item.funId.toString()">
                <template #title>
                    <el-icon><document /></el-icon>
                    <span>{{item.funName}}</span>
                </template>
            </el-menu-item>
        </router-link>
    </div>
</template>

<script>
  // import path from 'path'
  // import {isExternal} from '@/utils/validate'
  // import Item from './Item'
  // import AppLink from './Link.vue'
  import {
      Document,
      Menu as IconMenu,
      Location,
      Setting,
  } from '@element-plus/icons-vue'
  // import FixiOSBug from './FixiOSBug'

  export default {
    name: 'SidebarItem',
    // components: {AppLink},
    // mixins: [FixiOSBug],
    props: {
      // route object
      item: {
        type: Object,
        required: true
      },
      isNest: {
        type: Boolean,
        default: false
      },
      basePath: {
        type: String,
        default: ''
      }
    },
    data() {
      // To fix https://github.com/PanJiaChen/vue-admin-template/issues/237
      // TODO: refactor with render function
      this.onlyOneChild = null
      return {
        faxIcon: ''
      }
    },
      mounted() {

      },
      methods: {
      hasOneShowingChild(children = [], parent) {
        const showingChildren = children.filter(item => {
          if (item.hidden) {
            return false
          } else {
            // Temp set(will be used if only has one showing child)
            this.onlyOneChild = item
            return true
          }
        })

        // When there is only one child router, the child router is displayed by default
        if (showingChildren.length === 1) {
          return true
        }

        // Show parent if there are no child router to display
        if (showingChildren.length === 0) {
          this.onlyOneChild = {...parent, path: '', noShowingChildren: true}
          return true
        }

        return false
      },
      resolvePath(routePath) {
          return routePath;
        // if (isExternal(routePath)) {
        //   return routePath
        // }
        // if (isExternal(this.basePath)) {
        //   return this.basePath
        // }
        // return path.resolve(this.basePath, routePath)
      }
    }
  }
</script>
<style scoped lang="scss">
    a {
        text-decoration: none;
    }

    .router-link-active {
    text-decoration: none;
    }

    .fa-margin {
    margin-right: 10px;
    }

    .myMenu v-deep .el-menu-item {
    white-space: pre;
    height: 50px;
    line-height: 50px;
    padding: 0 10px;
    }

    .myMenu v-deep .el-submenu__title {
    height: 50px;
    line-height: 50px;
    padding: 0 10px;
    }
</style>
