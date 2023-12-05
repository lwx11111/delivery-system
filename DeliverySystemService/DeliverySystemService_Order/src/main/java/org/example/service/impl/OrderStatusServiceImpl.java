package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.example.dao.OrderInfoMapper;
import org.example.domain.order.OrderInfo;
import org.example.enums.OrderStatus;
import org.example.enums.OrderStatusChangeEvent;
import org.example.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author 刘文轩
 * @Date 2023/12/4 16:40
 * 订单状态修改类 采用设计模式
 */
@Service
public class OrderStatusServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderStatusService {

    @Resource
    private StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine;

    @Autowired
    private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, OrderInfo> persister;

    /**
     * 订单状态初始化
     * @param order
     * @return
     */
    @Override
    public OrderInfo create(OrderInfo order) {
        order.setStatus(OrderStatus.WAIT_PAYMENT.getValue());
        return order;
    }

    /**
     * 发送订单支付事件
     */
    @Override
    public OrderInfo pay(String orderId) {
        // 获得订单信息
        OrderInfo order = baseMapper.selectById(orderId);
        order.setOrderStatus(OrderStatus.WAIT_PAYMENT);
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试支付，订单号：" + orderId);
        // 发送支付事件
        Message message = MessageBuilder.withPayload(OrderStatusChangeEvent.PAYED).
                setHeader("order", order).build();
        if (!sendEvent(message, order)) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 支付失败, 状态异常，订单号：" + orderId);
        }
        return order;
    }

    /**
     * 商家接单
     * @param order
     * @return
     */
    @Override
    public OrderInfo taking(OrderInfo order) throws Exception {
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试商家接单，订单号：" + order.getId());
        // 发送接单事件
        Message message = MessageBuilder.withPayload(OrderStatusChangeEvent.TAKING).
                setHeader("order", order).build();
        if (!sendEvent(message, order)) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 接单失败, 状态异常，订单号：" + order.getId());
        }
        return order;
    }

    /**
     * 骑手配送
     * @param order
     * @return
     */
    @Override
    public OrderInfo deliver(OrderInfo order) {
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试发货，订单号：" + order.getId());
        Message message = MessageBuilder.withPayload(OrderStatusChangeEvent.DELIVERY).
                setHeader("order", order).build();
        if (!sendEvent(message, order)) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 发货失败，状态异常，订单号：" + order.getId());
        }
        return order;
    }

    @Override
    public OrderInfo receive(OrderInfo order) {
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试收货，订单号：" +  order.getId());
        Message message = MessageBuilder.withPayload(OrderStatusChangeEvent.TAKING).
                setHeader("order", order).build();
        if (!sendEvent(message, order)) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 收货失败，状态异常，订单号：" + order.getId());
        }
        return order;
    }


    /**
     * 发送订单状态转换事件
     *
     * @param message
     * @param order
     * @return
     */
    private synchronized boolean sendEvent(Message<OrderStatusChangeEvent> message, OrderInfo order) {
        boolean result = false;
        System.out.println(message);
        try {
            orderStateMachine.start();
//            //尝试恢复状态机状态
            persister.restore(orderStateMachine, order);
            //添加延迟用于线程安全测试
//            Thread.sleep(1000);
            System.out.println("llllllllll");
            result = orderStateMachine.sendEvent(message);
            //持久化状态机状态
            persister.persist(orderStateMachine, order);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            orderStateMachine.stop();
        }
        return result;
    }
}
