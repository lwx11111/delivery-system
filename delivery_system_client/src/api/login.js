/** @format */

import Vue from 'vue'

/**
 * 获取登录的用户信息
 * @param {*} token
 */
export function login ({ userId, password, verify, uuid, mobile }) {
  if (Vue.prototype.$config.PERMISSIONSYSTEM_ENABLE) {
    // const paw = allEncrypt(password)
    const url = Vue.prototype.$config.PERMISSIONSYSTEM.URL_LOGIN
    const params = {
      username: userId,
      password: password,
      appCode: Vue.prototype.$config.PERMISSIONSYSTEM.CONF_APPCODE,
      verification: Vue.prototype.$config.VERIFICATION,
      mobile: mobile,
      textCode: verify,
      uuid: uuid,
      singleLogin: false
    }
    return Vue.axios.post(url, params)
  } else {
    return Promise.resolve({
      token: { accessToken: 'inmsToken' },
      account: {
        loginName: userId,
        accountId: userId
      },
      user: { provinceId: '100000' }
    })
  }
}

/**
 * 第三方登录
 */
export function thirdLogin (token) {
  const url = Vue.prototype.$config.PERMISSIONSYSTEM_THIRDLOGIN
  const params = {
    token: token,
    type: 'csj&onepoint',
    appCode: Vue.prototype.$config.PERMISSIONSYSTEM.CONF_APPCODE
  }
  return Vue.axios.post(url, params)
}

// export function loginWithCode(data) {
//   return request({
//     url: managerUriPrefix + 'v1/account/anon/loginwithcode',
//     method: 'post',
//     data: qs.stringify(data)
//   })
// }

/**
 * 续约
 */
export function renewToken () {
  if (Vue.prototype.$config.PERMISSIONSYSTEM_ENABLE) {
    const url = Vue.prototype.$config.PERMISSIONSYSTEM.URL_RENEW
    return Vue.axios.post(url)
  } else {
    const result = {}
    return result
  }
}

/**
 * 退出时调用
 */
export function logout () {
  if (Vue.prototype.$config.PERMISSIONSYSTEM_ENABLE) {
    const url = Vue.prototype.$config.PERMISSIONSYSTEM.URL_LOGOUT
    return Vue.axios.post(url)
  } else {
    return Promise.resolve({ logout: true })
  }
}
/**
 * 修改密码
 */
export default {
  modifyUserPassword (params) {
    return Vue.axios.post('/sys-auth/api/v1/account/modifyUserPassword', params)
  }
}
/**
 * 根据token获取用户信息
 */
export function getUserByToken () {
  if (Vue.prototype.$config.PERMISSIONSYSTEM_ENABLE) {
    const url = Vue.prototype.$config.PERMISSIONSYSTEM.URL_USERINFO
    return Vue.axios.get(url)
  } else {
    const username = localStorage.getItem('userId') || 'admin'
    const result = { account: { loginName: username, accountId: username }, user: { provinceId: '100000' } }
    return Promise.resolve(result)
  }
}
