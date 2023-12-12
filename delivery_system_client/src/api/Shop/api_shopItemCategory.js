import request from '@/utils/request'
const serverName = '/shop'


export default {

  // 分页查询
  selpage4shopitemcategory(data) {
    return request({
      url: serverName + '/shopitemcategory/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4shopitemcategory(obj) {
    return request({
      url: serverName + '/shopitemcategory',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4shopitemcategory(id, obj) {
    return request({
      url: serverName + '/shopitemcategory/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4shopitemcategory(id) {
    return request({
      url: serverName + '/shopitemcategory/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4shopitemcategory(ids) {
    return request({
      url: serverName + '/shopitemcategory/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4shopitemcategory(id) {
    return request({
      url: serverName + '/shopitemcategory/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: serverName + '/shopitemcategory/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: serverName + '/shopitemcategory/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4shopitemcategory(params) {
    return request({
      url: serverName + '/shopitemcategory/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
