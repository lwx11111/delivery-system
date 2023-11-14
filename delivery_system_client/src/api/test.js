// 权限管理API  【菜单、角色、权限】
import request from '@/utils/request'
import qs from 'qs'
export default {
    test() {
        console.log("test")
        return request({
            url: '/order/order/1',
            method: 'get',
        })
    },

    testLogin(data) {
        return request({
            url: 'shop/test/testLogin',
            method: 'post',
            type: 'form',
            data: data
        })
    },

    testLogin1(data) {
        return request({
            url: 'shop/test/testLogin1',
            method: 'post',
            data: data
        })
    },

    loginWithCode(data) {
        return request({
            url: '/auth-manager-microservice-lwx/' + 'v1/account/anon/loginwithcode',
            method: 'post',
            type: 'form',
            data: data
        })
    }
}

