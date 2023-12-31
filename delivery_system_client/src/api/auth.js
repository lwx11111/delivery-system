// 权限管理API  【菜单、角色、权限】
import request from '@/utils/request'

export default {
    loginWithCode(data) {
        return request({
            url: '/auth-manager-microservice-lwx/' + 'v1/account/anon/loginwithcode',
            method: 'post',
            type: 'form',
            data: data
        })
    },

    register(data) {
        return request({
            url: '/auth-manager-microservice-lwx/' + 'v1/account/anon/save',
            method: 'post',
            type: 'form',
            data: data
        })
    },

    modifyPass(data) {
        return request({
            url: '/auth-manager-microservice-lwx/' + 'v1/account/modify-password',
            method: 'post',
            type: 'form',
            data: data
        })
    },

    logout() {
        return request({
            url: '/auth-manager-microservice-lwx/' + 'v1/account/logout',
            method: 'post'
        })
    },




}

