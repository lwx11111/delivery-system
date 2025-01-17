// 权限管理API  【菜单、角色、权限】
import request from '@/utils/request'
const managerUriPrefix = '/manager/'

/**
 * 权限系统API
 */
export default {
    loginWithCode(data) {
        return request({
            url: managerUriPrefix + 'v1/account/anon/loginwithcode',
            method: 'post',
            type: 'form',
            data: data
        })
    },

    register(data) {
        return request({
            url: managerUriPrefix + 'v1/account/anon/save',
            method: 'post',
            type: 'form',
            data: data
        })
    },

    riderRegister(riderId){
        return request({
            url: '/order/rider/riderRegister/'+ riderId,
            method: 'get',
        })
    },

    // 修改
    update4rider(id, obj) {
        return request({
            url:  '/order/rider/' + id,
            method: 'put',
            data: obj
        })
    },

    modifyPass(data) {
        return request({
            url: managerUriPrefix + 'v1/account/modify-password',
            method: 'post',
            type: 'form',
            data: data
        })
    },

    logout() {
        return request({
            url: managerUriPrefix + 'v1/account/logout',
            method: 'post'
        })
    },

    updateInfo(data){
        return request({
            url: managerUriPrefix + 'v1/account/updateInfo',
            method: 'post',
            data: data
        })
    }

}

