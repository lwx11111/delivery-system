import {createStore } from 'vuex'
import tagsView from "@/store/modules/tagsView";
import side from "@/store/modules/side";
import app from "@/store/modules/app";
import permission from "@/store/modules/permission";
export default createStore({
    state: {
        count: 0
    },
    getters:{
        getCount(state){
            return state.count;
        }
    },
    // 不能直接改变 store 中的状态。改变 store 中的状态的唯一途径就是显式地提交 (commit) mutation
    mutations: {
        increment (state) {
            state.count++
        }
    },
    actions: {
        increment (context) {
            context.commit('increment')
        }
    },
    modules: {
        tagsView,
        side,
        app,
        permission
    },

    // modules,
    // sider

})
