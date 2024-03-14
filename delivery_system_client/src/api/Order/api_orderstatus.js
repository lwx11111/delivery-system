import request from '@/utils/request'
const serverName = 'order'


export default {

  // 分页查询
  selpage4orderstatus(data) {
    return request({
      url: serverName + '/orderstatus/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4orderstatus(obj) {
    return request({
      url: serverName + '/orderstatus',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4orderstatus(id, obj) {
    return request({
      url: serverName + '/orderstatus/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4orderstatus(id) {
    return request({
      url: serverName + '/orderstatus/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4orderstatus(ids) {
    return request({
      url: serverName + '/orderstatus/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4orderstatus(id) {
    return request({
      url: serverName + '/orderstatus/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: serverName + '/orderstatus/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: serverName + '/orderstatus/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4orderstatus(params) {
    return request({
      url: serverName + '/orderstatus/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
