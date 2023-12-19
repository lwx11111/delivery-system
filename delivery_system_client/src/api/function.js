import request from '@/utils/request'
import qs from 'qs';
// import {managerUriPrefix} from '@/api/api-url-prefix'

export function leftMenuApi(data) {
  return request({
    url: '/auth-manager-microservice-lwx/' + 'v1/function/left-menu',
    // url: '/manager/' + 'v1/function/left-menu',
    method: 'post',
    data: data
  })
}

export function pageApi(data) {
  return request({
    url: managerUriPrefix + 'v1/function/paging',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function getApi(id) {
  return request({
    url: managerUriPrefix + 'v1/function/r/' + id,
    method: 'get'
  })
}

export function saveApi(data) {
  return request({
    url: managerUriPrefix + 'v1/function/s',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function childrenApi(data) {
  return request({
    url: managerUriPrefix + 'v1/function/children',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function treeApi(appId) {
  return request({
    url: managerUriPrefix + 'v1/function/tree',
    method: 'post',
    data: qs.stringify({appId})
  })
}

export function deleteApi(id) {
  return request({
    url: managerUriPrefix + 'v1/function/d/' + id,
    method: 'post'
  })
}

export function roleCheckTreeApi(roleId) {
  return request({
    url: managerUriPrefix + 'v1/function/role-check-tree',
    method: 'post',
    data: qs.stringify({roleId})
  })
}

export function groupCheckTreeApi(groupId, appId) {
  return request({
    url: managerUriPrefix + 'v1/function/group-check-tree',
    method: 'post',
    data: qs.stringify({groupId, appId})
  })
}

export function privateCheckTreeApi(accountId, appId) {
  return request({
    url: managerUriPrefix + 'v1/function/private-check-tree',
    method: 'post',
    data: qs.stringify({accountId, appId})
  })
}

export function listFunResTypesApi(funId) {
  return request({
    url: `${managerUriPrefix}v1/function/${funId}/resTypes`,
    method: 'get'
  })
}

/**
 * 查询有没有页面权限
 * @param url
 * @returns {*}
 */
export function pagePermApi(url) {
  return request({
    url: '/auth-manager-microservice-lwx/' + 'v1/function/page-perm',
    // url: '/manager/' + 'v1/function/page-perm',
    method: 'post',
    data: qs.stringify({url})
  })
}

export function getButtonsApi() {
  return request({
    url: managerUriPrefix + 'v1/function/get-buttions',
    method: 'get'
  })
}
