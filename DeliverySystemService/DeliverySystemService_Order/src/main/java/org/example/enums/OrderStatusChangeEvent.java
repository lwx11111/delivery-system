package org.example.enums;

/**
 * @Author 刘文轩
 * @Date 2023/12/4 16:33
 */
/**
 * 订单状态改变事件
 */
public enum OrderStatusChangeEvent {
    //支付，商家接单，骑手配送，确认收货
    PAYED,TAKING,DELIVERY, RECEIVED,CANCEL,UNTAKING,REFUND;

}
