import request from '@/utils/request'
const serverName = '/shop'


export default {

  // 分页查询
  selpage4shop(data) {
    return request({
      url: serverName + '/shop/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4shop(obj) {
    return request({
      url: serverName + '/shop',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4shop(id, obj) {
    return request({
      url: serverName + '/shop/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4shop(id) {
    return request({
      url: serverName + '/shop/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4shop(ids) {
    return request({
      url: serverName + '/shop/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4shop(id) {
    return request({
      url: serverName + '/shop/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: serverName + '/shop/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: serverName + '/shop/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4shop(params) {
    return request({
      url: serverName + '/shop/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },
  // 查询所有店铺根据分类ID
  listShopsByCategoryId(params) {
    return request({
      url: serverName + '/shop/listShopsByCategoryId',
      method: 'post',
      data: params,
    })
  }

}
