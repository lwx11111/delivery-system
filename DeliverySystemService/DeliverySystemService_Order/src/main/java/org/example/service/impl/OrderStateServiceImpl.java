package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.dao.OrderInfoMapper;
import org.example.domain.order.OrderInfo;
import org.example.enums.OrderStatus;
import org.example.enums.OrderStatusChangeEvent;
import org.example.service.OrderStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;


/**
 * @Author 刘文轩
 * @Date 2023/12/4 16:40
 * 订单状态修改类 采用设计模式
 */
@Service
public class OrderStateServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderStateService {

    @Resource
    private StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine;

    @Autowired
    private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, OrderInfo> persister;

    @Override
    public Boolean comment(OrderInfo orderInfo) {
        // 获得订单信息
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试支付，订单号：" + orderInfo.getId());
        // 发送支付事件
        Boolean result = sendEvent(OrderStatusChangeEvent.COMMENT, orderInfo);
        if (!result) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 接单失败, 状态异常，订单号：" + orderInfo.getId());
        }
        return result;
    }

    /**
     * 发送订单支付事件
     */
    @Override
    public Boolean pay(OrderInfo orderInfo) {
        // 获得订单信息
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试支付，订单号：" + orderInfo.getId());
        // 发送支付事件
        Boolean result = sendEvent(OrderStatusChangeEvent.PAYED, orderInfo);
        if (!result) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 接单失败, 状态异常，订单号：" + orderInfo.getId());
        }
        return result;
    }

    /**
     * 商家接单
     * @param order
     * @return
     */
    @Override
    public Boolean taking(OrderInfo order) throws Exception {
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试商家接单，订单号：" + order.getId());
        // 发送接单事件
        Boolean result = sendEvent(OrderStatusChangeEvent.TAKING, order);
        if (!result) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 接单失败, 状态异常，订单号：" + order.getId());
        }
        return result;
    }

    /**
     * 骑手配送
     * @param order
     * @return
     */
    @Override
    public Boolean deliver(OrderInfo order) {
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试发货，订单号：" + order.getId());
        Boolean result = sendEvent(OrderStatusChangeEvent.DELIVERY, order);
        if (!result) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 接单失败, 状态异常，订单号：" + order.getId());
        }
        return result;
    }

    @Override
    public Boolean receive(OrderInfo order) {
        System.out.println("lwxxxxxxxxxxx==========");
        System.out.println(order);
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试收货，订单号：" +  order.getId());
        Boolean result = sendEvent(OrderStatusChangeEvent.RECEIVED, order);
        if (!result) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 接单失败, 状态异常，订单号：" + order.getId());
        }
        return result;
    }

    @Override
    public Boolean orderUntaking(OrderInfo order) {
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试收货，订单号：" +  order.getId());
        Boolean result = sendEvent(OrderStatusChangeEvent.UNTAKING, order);
        if (!result) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 接单失败, 状态异常，订单号：" + order.getId());
        }
        return result;
    }

    @Override
    public Boolean orderCancel(OrderInfo order) {
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试收货，订单号：" +  order.getId());
        Boolean result = sendEvent(OrderStatusChangeEvent.CANCEL, order);
        if (!result) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 接单失败, 状态异常，订单号：" + order.getId());
        }
        return result;
    }

    @Override
    public Boolean orderRefund(OrderInfo order) {
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试收货，订单号：" +  order.getId());
        Boolean result = sendEvent(OrderStatusChangeEvent.REFUND, order);
        if (!result) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 接单失败, 状态异常，订单号：" + order.getId());
        }
        return result;
    }

    /**
     * 发送订单状态转换事件
     *
     * @param event
     * @param order
     * @return
     */
    private synchronized boolean sendEvent(OrderStatusChangeEvent event, OrderInfo order) {
        boolean result = false;
        Message<OrderStatusChangeEvent>  message = MessageBuilder.withPayload(event).
                setHeader("order", order).build();
        try {
            orderStateMachine.start();
//            //尝试恢复状态机状态
            persister.restore(orderStateMachine, order);
            //添加延迟用于线程安全测试
//            Thread.sleep(1000);
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
