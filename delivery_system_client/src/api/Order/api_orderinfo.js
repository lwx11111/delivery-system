import request from '@/utils/request'
const serverName = '/order'


export default {
  getDataNearlySevenDays(data) {
    return request({
      url: serverName + '/order/getDataNearlySevenDays',
      method: 'post',
      data: data
    })
  },

  getExpectedTime(data) {
    return request({
      url: serverName + '/order/getExpectedTime',
      method: 'post',
      data: data
    })
  },

  getHotItemData(data) {
    return request({
      url: serverName + '/order/getHotItemData',
      method: 'post',
      data: data
    })
  },

  getEarningsData(data) {
    return request({
      url: serverName + '/order/getEarningsData',
      method: 'post',
      data: data
    })
  },

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

  orderDelivery(data) {
    console.log(data)
    return request({
        url: serverName + '/order/orderDelivery',
        method: 'post',
        data: data
    })
  },

  orderReceive(data) {
    return request({
      url: serverName + '/order/orderReceive',
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

  listOrderByRiderId(data) {
    return request({
      url: serverName + '/order/listOrderByRiderId',
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
