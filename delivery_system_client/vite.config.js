import { defineConfig } from 'vite'
import path from 'path'

import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "src")
    }
  },
  css: {
    // css预处理器
    preprocessorOptions: {
      scss: {
        // 引入 mixin.scss 这样就可以在全局中使用 mixin.scss中预定义的变量了
        // 给导入的路径最后加上 ;
        additionalData: '@import "@/style/variables.scss";' +
            '@import "@/style/mixin.scss";'
      }
    }
  }
})
