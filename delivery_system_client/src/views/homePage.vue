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

<script lang="ts" setup>
import {reactive, onMounted, ref, onActivated,onUnmounted} from 'vue';
import AuthStorage from '@/cache/authStorage.js';
import UserStorage from '@/cache/userStorage.js';
import { useRouter} from "vue-router";
const router = useRouter()

// Data
const data = reactive({

})

onMounted(() => {
    // 已经登录
    if (AuthStorage.getToken() !== null && UserStorage.getUser() !== null){
        let user = UserStorage.getUser();
        console.log(user)
        if (user.customAccountId === 'consumer'){
            router.push({
                path: "/Consumer/index"
            })
        }
    }
})





</script>

<style scoped>

</style>
