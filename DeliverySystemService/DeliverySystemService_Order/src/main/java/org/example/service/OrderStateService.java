package org.example.service;

import org.example.domain.order.OrderInfo;

/**
 * @Author 刘文轩
 * @Date 2023/12/4 18:37
 */
public interface OrderStateService {

    Boolean comment(OrderInfo orderInfo);

    //发起支付
    Boolean pay(OrderInfo orderInfo);

    Boolean taking(OrderInfo orderInfo) throws Exception;

    //订单发货
    Boolean deliver(OrderInfo orderInfo);

    //订单收货
    Boolean receive(OrderInfo orderInfo);
    Boolean orderUntaking(OrderInfo orderInfo);
    Boolean orderCancel(OrderInfo orderInfo);
    Boolean orderRefund(OrderInfo orderInfo);

}
