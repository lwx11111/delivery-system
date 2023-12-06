import request from '@/utils/request'
const serverName = '/order'


export default {
  // ========================================== 订单状态

  orderPay(data) {
    return request({
      url: serverName + '/order/orderPay',
      method: 'post',
      data: data
    })
  },

  orderTaking(data) {
    return request({
        url: serverName + '/order/orderTaking',
        method: 'post',
        data: data
    })
  },

  orderUntaking(data) {
    return request({
      url: serverName + '/order/orderUntaking',
      method: 'post',
      data: data
    })
  },

  orderCancel(data) {
    return request({
      url: serverName + '/order/orderCancel',
      method: 'post',
      data: data
    })
  },

  orderRefund(data) {
    return request({
      url: serverName + '/order/orderRefund',
      method: 'post',
      data: data
    })
  },

  listOrderItemById(data) {
    return request({
      url: serverName + '/order/listOrderItemById',
      method: 'post',
      data: data
    })
  },

  // 分页查询
  selpage4orderinfo(data) {
    return request({
      url: serverName + '/order/selpage',
      method: 'post',
      data: data
    })
  },

  // 添加
  add4orderinfo(obj) {
    return request({
      url: serverName + '/order',
      method: 'post',
      data: obj
    })
  },

  // 修改
  update4orderinfo(id, obj) {
    return request({
      url: serverName + '/order/' + id,
      method: 'put',
      data: obj
    })
  },

  // 删除单条
  del4orderinfo(id) {
    return request({
      url: serverName + '/order/' + id,
      method: 'delete',
    })
  },

  // 删除多条
  dels4orderinfo(ids) {
    return request({
      url: serverName + '/order/dels',
      method: 'post',
      data: ids
    })
  },

  // 查询单条
  sel4orderinfo(id) {
    return request({
      url: serverName + '/order/' + id,
      method: 'get',
    })

  },

  // 下载Excel模板
  downloadExcelTemplate(params) {
    return request({
      url: serverName + '/orderinfo/downloadExcelTemplate',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  },

  // 导入Excel接口URL
  uploadExcelUrl() {
    return request({
      url: serverName + '/orderinfo/uploadExcel',
      type: 'form',
      method: 'post',
    })

  },
  // 导出Excel
  excelData4orderinfo(params) {
    return request({
      url: serverName + '/orderinfo/excel',
      method: 'post',
      data: params,
      responseType: 'arraybuffer'
    })
  }

}
