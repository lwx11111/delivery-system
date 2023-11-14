<template>
<!--  <el-scrollbar ref="scrollContainer" :vertical="false" class="scroll-container" @wheel.native.prevent="handleScroll">-->
<!--    <slot />-->
<!--  </el-scrollbar>-->

<!--    <el-scrollbar>-->
<!--        <div class="scrollbar-flex-content">-->
<!--            <p v-for="item in 50" :key="item" class="scrollbar-demo-item">-->
<!--&lt;!&ndash;                v-for="tag in visitedViews"&ndash;&gt;-->
<!--&lt;!&ndash;                ref="tag"&ndash;&gt;-->
<!--&lt;!&ndash;                :key="tag.path"&ndash;&gt;-->
<!--&lt;!&ndash;                :class="isActive(tag)?'active':''"&ndash;&gt;-->
<!--&lt;!&ndash;                :to="{ path: tag.path, query: tag.query, fullPath: tag.fullPath }"&ndash;&gt;-->
<!--&lt;!&ndash;                tag="span"&ndash;&gt;-->
<!--&lt;!&ndash;                class="tags-view-item"&ndash;&gt;-->
<!--&lt;!&ndash;                @click.middle.native="closeSelectedTag(tag)"&ndash;&gt;-->
<!--&lt;!&ndash;                @contextmenu.prevent.native="openMenu(tag,$event)"&ndash;&gt;-->
<!--                <router-link to="/dashboard"-->

<!--                >-->
<!--&lt;!&ndash;                    {{ tag.title }}&ndash;&gt;-->
<!--                    1111-->
<!--&lt;!&ndash;                    <span v-if="!tag.meta.affix" class="el-icon-close" @click.prevent.stop="closeSelectedTag(tag)" />&ndash;&gt;-->
<!--                </router-link>-->
<!--            </p>-->
<!--        </div>-->
<!--    </el-scrollbar>-->
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
const store = useStore();
const router = useRouter()
const tagAndTagSpacing = 4 // tagAndTagSpacing

export default {
  name: 'ScrollPane',
  data() {
    return {
      left: 0
    }
  },
  computed: {
    scrollWrapper() {
      return this.$refs.scrollContainer.$refs.wrap
    }
  },
  methods: {
    // handleScroll(e) {
    //   const eventDelta = e.wheelDelta || -e.deltaY * 40
    //   const $scrollWrapper = this.scrollWrapper
    //   $scrollWrapper.scrollLeft = $scrollWrapper.scrollLeft + eventDelta / 4
    // },
    // moveToTarget(currentTag) {
    //   const $container = this.$refs.scrollContainer.$el
    //   const $containerWidth = $container.offsetWidth
    //   const $scrollWrapper = this.scrollWrapper
    //   const tagList = this.$parent.$refs.tag
    //
    //   let firstTag = null
    //   let lastTag = null
    //
    //   // find first tag and last tag
    //   if (tagList.length > 0) {
    //     firstTag = tagList[0]
    //     lastTag = tagList[tagList.length - 1]
    //   }
    //
    //   if (firstTag === currentTag) {
    //     $scrollWrapper.scrollLeft = 0
    //   } else if (lastTag === currentTag) {
    //     $scrollWrapper.scrollLeft = $scrollWrapper.scrollWidth - $containerWidth
    //   } else {
    //     // find preTag and nextTag
    //     const currentIndex = tagList.findIndex(item => item === currentTag)
    //     const prevTag = tagList[currentIndex - 1]
    //     const nextTag = tagList[currentIndex + 1]
    //
    //     // the tag's offsetLeft after of nextTag
    //     const afterNextTagOffsetLeft = nextTag.$el.offsetLeft + nextTag.$el.offsetWidth + tagAndTagSpacing
    //
    //     // the tag's offsetLeft before of prevTag
    //     const beforePrevTagOffsetLeft = prevTag.$el.offsetLeft - tagAndTagSpacing
    //
    //     if (afterNextTagOffsetLeft > $scrollWrapper.scrollLeft + $containerWidth) {
    //       $scrollWrapper.scrollLeft = afterNextTagOffsetLeft - $containerWidth
    //     } else if (beforePrevTagOffsetLeft < $scrollWrapper.scrollLeft) {
    //       $scrollWrapper.scrollLeft = beforePrevTagOffsetLeft
    //     }
    //   }
    // }
  }
}
</script>
<style lang="scss" scoped>
.scroll-container {
  white-space: nowrap;
  position: relative;
  overflow: hidden;
  width: 100%;
  v-deep {
    .el-scrollbar__bar {
      bottom: 0px;
    }
    .el-scrollbar__wrap {
      height: 49px;
    }
  }
}
</style>
