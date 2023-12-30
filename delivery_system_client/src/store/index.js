import {createStore } from 'vuex'

export default createStore({
    // state是用来定义数据，这里可以设置数据的默认数据，但是为了数据的安全性，通常不直接修改state数据，而是通过mutations修改
    state: {
        count: 0,
        account:{},
        user:{},
        superAdmin:false,
        token:{}
    },
    // getters用来处理数据，对state中的数据进行处理，得到自己想要的效果，当需要在多处组件使用这种数据时，gettters是你最好的选择
    getters:{
        getCount(state){
            return state.count;
        },
        getAccount(state){
            return state.account;
        },
        getUser(state){
            return state.user;
        },
        getSuperAdmin(state){
            return state.superAdmin;
        },
        getToken(state){
            return state.token;
        }
    },
    // 不能直接改变 store 中的状态。改变 store 中的状态的唯一途径就是显式地提交 (commit) mutation
    // mutations通常为修改state数据而使用，这用就可以避免直接修改state的数据
    mutations: {
        increment (state) {
            state.count++
        },
        setAccount(state,account){
            state.account = account;
        },
        setUser(state,user){
            state.user = user;
        },
        setSuperAdmin(state,superAdmin){
            state.superAdmin = superAdmin;
        },
        setToken(state,token){
            state.token = token;
        }
    },
    // actions当你的数据是需要发送请求获取时，这是非常完美的选择
    actions: {
        async setGoods(content,id){
            let res = await api.getGoods(id);
            content.commit('setGoods',res.data);
        }

        // console.log(store.state.goods);
        // console.log(store.getters['goodsData']);
        // store.commit('changeGoods',123);
        // async function abc() {
        //     await store.dispatch("setGoods", {
        //         id: 123,
        //     });
        // }
    },
    modules: {
        // tagsView,
        // side,
        // app,
        // permission
    },
})
