import request from '@/utils/request'
const serverName = '/basic'


export default {
  getProvinces(){
    return request({
      url: serverName + '/area/getProvinces',
      method: 'get',
    })
  },

  getCity(parentId){
    return request({
      url: serverName + '/area/getCity/' + parentId,
      method: 'get',
    })
  },

  getCounty(parentId){
    return request({
      url: serverName + '/area/getCounty/' + parentId,
      method: 'get',
    })
  },

  // 分页查询
  selpage4sysdict(data) {
    return request({
      url: serverName + '/sysdict/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4sysdict(obj) {
    return request({
      url: serverName + '/sysdict',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4sysdict(id, obj) {
    return request({
      url: serverName + '//sysdict/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4sysdict(id) {
    return request({
      url: serverName + '//sysdict/' + id,
      method: 'delete',
    })
  },


  // 删除多条
  dels4sysdict(ids) {
    return request({
      url: serverName + '//sysdict/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4sysdict(id) {
    return request({
      url: serverName + '//sysdict/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: serverName + '//sysdict/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: serverName + '//sysdict/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4sysdict(params) {
    return request({
      url: serverName + '//sysdict/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
