package org.example.config;

import org.example.dao.OrderInfoMapper;
import org.example.domain.order.OrderInfo;
import org.example.enums.OrderStatus;
import org.example.enums.OrderStatusChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

/**
 * @Author 刘文轩
 * @Date 2023/12/4 16:37
 * 订单状态监听器实现类
 */
@Component("orderStateListener")
@WithStateMachine(name = "orderStateMachine")
public class OrderStateListenerImpl{

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    /**
     * 支付
     * @param message
     * @return
     */
    @OnTransition(source = "WAIT_PAYMENT", target = "WAIT_TAKING")
    public boolean payTransition(Message<OrderStatusChangeEvent> message) {
        System.out.println("lwxxxxxxxxxxxxxxxxxxx");
        OrderInfo order = (OrderInfo) message.getHeaders().get("order");
        order.setStatus(OrderStatus.WAIT_TAKING.getValue());
        order.setOrderStatus(OrderStatus.WAIT_TAKING);
        orderInfoMapper.updateById(order);
        System.out.println("支付，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    /**
     * 商家接单
     * @param message
     * @return
     */
    @OnTransition(source = "WAIT_TAKING", target = "WAIT_DELIVER")
    public boolean takingTransition(Message<OrderStatusChangeEvent> message) {
        System.out.println("lwxxxxxxxxxxxxxxxxxxx");
        OrderInfo order = (OrderInfo) message.getHeaders().get("order");
        order.setStatus(OrderStatus.WAIT_DELIVER.getValue());
        System.out.println("支付，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "WAIT_DELIVER", target = "WAIT_RECEIVE")
    public boolean deliverTransition(Message<OrderStatusChangeEvent> message) {
        OrderInfo order = (OrderInfo) message.getHeaders().get("order");
        order.setStatus(OrderStatus.WAIT_RECEIVE.getValue());
        System.out.println("发货，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "WAIT_RECEIVE", target = "FINISH")
    public boolean receiveTransition(Message<OrderStatusChangeEvent> message){
        OrderInfo order = (OrderInfo) message.getHeaders().get("order");
        order.setStatus(OrderStatus.FINISH.getValue());
        System.out.println("收货，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }
}

