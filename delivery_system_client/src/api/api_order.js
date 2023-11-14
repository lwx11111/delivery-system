import request from '@/utils/request'
const serverName = '/order'

export default {
  // 分页查询
  selpage4order(data) {
    return request({
      url: serverName + '/order/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4order(obj) {
    return request({
      url: serverName + '/order',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4order(id, obj) {
    return request({
      url: serverName + '/order/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4order(id) {
    return request({
      url: serverName + '/order/' + id,
      method: 'delete',
    })
  },

  // 删除多条
  dels4order(ids) {
    return request({
      url: serverName + '/order/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4order(id) {
    return request({
      url: serverName + '/order/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: serverName + '/order/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: serverName + '/order/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4order(params) {
    return request({
      url: serverName + '/order/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  submitOrder(params) {
    return request({
      url: serverName + '/order/submitOrder',
      method: 'post',
      data: params
    })
  },

  // 申请售后
  afterSale(params) {
    return request({
      url: serverName + '/order/afterSale',
      method: 'post',
      data: params
    })
  },

}
