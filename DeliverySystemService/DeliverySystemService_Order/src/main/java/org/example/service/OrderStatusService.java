package org.example.service;

import org.example.domain.order.OrderInfo;

import java.util.Map;

/**
 * @Author 刘文轩
 * @Date 2023/12/4 18:37
 */
public interface OrderStatusService {

    //创建新订单
    OrderInfo create(OrderInfo orderInfo);

    //发起支付
    OrderInfo pay(String orderId);

    OrderInfo taking(OrderInfo orderInfo) throws Exception;

    //订单发货
    OrderInfo deliver(OrderInfo orderInfo);

    //订单收货
    OrderInfo receive(OrderInfo orderInfo);

}
