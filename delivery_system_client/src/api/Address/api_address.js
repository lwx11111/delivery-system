import request from '@/utils/request'
const serverName = '/address'


export default {

  getAddressByShopId(shopId) {
    return request({
      url: serverName + '/address/getAddressByShopId/' + shopId,
      method: 'get',
    })
  },

  // 分页查询
  selpage4address(data) {
    return request({
      url: serverName + '/address/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4address(obj) {
    return request({
      url: serverName + '/address',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4address(id, obj) {
    return request({
      url: serverName + '/address/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4address(id) {
    return request({
      url: serverName + '/address/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4address(ids) {
    return request({
      url: serverName + '/address/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4address(id) {
    return request({
      url: serverName + '/address/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: serverName + '/address/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: serverName + '/address/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4address(params) {
    return request({
      url: serverName + '/address/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
