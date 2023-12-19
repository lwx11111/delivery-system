import request from '@/utils/request'
const serverName = '/shop'


export default {
  listCartsByUserId(data) {
    return request({
      url: serverName + '/cart/listCartsByUserId',
      method: 'post',
      data: data
    })
  },
  // 分页查询
  selpage4cart(data) {
    return request({
      url: serverName + '/cart/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4cart(obj) {
    return request({
      url: serverName + '/cart',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4cart(id, obj) {
    return request({
      url: serverName + '//cart/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4cart(id) {
    return request({
      url: serverName + '//cart/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4cart(ids) {
    return request({
      url: serverName + '//cart/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4cart(id) {
    return request({
      url: serverName + '//cart/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: serverName + '//cart/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: serverName + '//cart/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4cart(params) {
    return request({
      url: serverName + '//cart/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
