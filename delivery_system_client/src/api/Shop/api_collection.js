import request from '@/utils/request'
const serverName = '/shop'


export default {
  listShopByCollection(obj) {
    return request({
      url: serverName + '/collection/listShopByCollection',
      method: 'post',
      data: obj
    })
  },

  isHaveCollection(obj) {
    return request({
      url: serverName + '/collection/isHaveCollection',
      method: 'post',
      data: obj
    })
  },

  cancelCollection(obj) {
    return request({
      url: serverName + '/collection/cancelCollection',
      method: 'post',
      data: obj
    })
  },

  // 分页查询
  selpage4collection(data) {
    return request({
      url: serverName + '/collection/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4collection(obj) {
    return request({
      url: serverName + '/collection',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4collection(id, obj) {
    return request({
      url: serverName + '/collection/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4collection(id) {
    return request({
      url: serverName + '/collection/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4collection(ids) {
    return request({
      url: serverName + '/collection/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4collection(id) {
    return request({
      url: serverName + '/collection/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: serverName + '/collection/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: serverName + '//collection/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4collection(params) {
    return request({
      url: serverName + '//collection/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
