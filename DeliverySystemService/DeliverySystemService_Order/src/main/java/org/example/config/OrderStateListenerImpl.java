package org.example.config;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.algorithm.RiderOrderAllocation;
import org.example.dao.OrderInfoMapper;
import org.example.dao.OrderStatusMapper;
import org.example.domain.OrderStatusDomain;
import org.example.domain.order.OrderInfo;
import org.example.enums.OrderStatus;
import org.example.enums.OrderStatusChangeEvent;
import org.example.feign.ShopFeignApi;
import org.example.service.impl.OrderInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author 刘文轩
 * @Date 2023/12/4 16:37
 * 订单状态监听器实现类
 */
@Component("orderStateListener")
@WithStateMachine(name = "orderStateMachine")
@Slf4j
public class OrderStateListenerImpl{
    @Autowired
    private RiderOrderAllocation riderOrderAllocation;

    /**
     * 订单基本信息数据库操作
     */
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    /**
     * 订单状态数据库操作
     */
    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private ShopFeignApi shopFeignApi;

    /**
     * 支付
     * @param message
     * @return
     */
    @OnTransition(source = "WAIT_PAYMENT", target = "WAIT_TAKING")
    public boolean payTransition(Message<OrderStatusChangeEvent> message) {
        OrderInfo order = (OrderInfo) message.getHeaders().get("order");
        try {
            // 修改状态
            order.setStatus(OrderStatus.WAIT_TAKING.getValue());
            order.setStatusName("消费者支付");
            orderInfoMapper.updateById(order);
            // 保存订单状态流转信息
            OrderStatusDomain orderStatus = new OrderStatusDomain();
            orderStatus.setStatus(OrderStatus.WAIT_TAKING.getValue());
            orderStatus.setName("消费者支付");
            orderStatus.setStatusTime(LocalDateTime.now());
            orderStatus.setOrderId(order.getId());
            orderStatusMapper.insert(orderStatus);
            System.out.println("支付，状态机反馈信息：" + message.getHeaders().toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 商家接单
     * @param message
     * @return
     */
    @OnTransition(source = "WAIT_TAKING", target = "WAIT_DELIVER")
    public boolean takingTransition(Message<OrderStatusChangeEvent> message) {
        OrderInfo order = (OrderInfo) message.getHeaders().get("order");
        try {
            // 分配给附近骑手
            String riderId = riderOrderAllocation.pollingAllocation(order);
            order.setRiderId(riderId);
            // 修改骑手订单数量

            // 修改状态
            order.setStatus(OrderStatus.WAIT_DELIVER.getValue());
            order.setStatusName("商家接单");
            orderInfoMapper.updateById(order);
            // 保存订单状态流转信息
            OrderStatusDomain orderStatus = new OrderStatusDomain();
            orderStatus.setStatus(OrderStatus.WAIT_DELIVER.getValue());
            orderStatus.setName("商家接单");
            orderStatus.setStatusTime(LocalDateTime.now());
            orderStatus.setOrderId(order.getId());
            orderStatusMapper.insert(orderStatus);
            return true;
        } catch (Exception e) {
            System.out.println("lwxxxxxxxxxxxxxxxxxx");
            e.printStackTrace();
            return false;
        }
    }

    @OnTransition(source = "WAIT_DELIVER", target = "WAIT_RECEIVE")
    public boolean deliverTransition(Message<OrderStatusChangeEvent> message) {
        OrderInfo order = (OrderInfo) message.getHeaders().get("order");
        try {
            // 修改状态
            order.setStatus(OrderStatus.WAIT_RECEIVE.getValue());
            order.setStatusName("骑手配送");
            orderInfoMapper.updateById(order);

            // 保存订单状态流转信息
            OrderStatusDomain orderStatus = new OrderStatusDomain();
            orderStatus.setStatus(OrderStatus.WAIT_RECEIVE.getValue());
            orderStatus.setName("骑手配送");
            orderStatus.setStatusTime(LocalDateTime.now());
            orderStatus.setOrderId(order.getId());
            orderStatusMapper.insert(orderStatus);
            System.out.println("支付，状态机反馈信息：" + message.getHeaders().toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @OnTransition(source = "WAIT_RECEIVE", target = "FINISH")
    public boolean receiveTransition(Message<OrderStatusChangeEvent> message){
        System.out.println("收货了11================");
        OrderInfo order = (OrderInfo) message.getHeaders().get("order");
        try {
            // 销量加一
            shopFeignApi.salesVolumePlus(order.getShopId());
            // 修改状态
            order.setStatus(OrderStatus.FINISH.getValue());
            order.setStatusName("消费者收货");
            orderInfoMapper.updateById(order);
            System.out.println("收货了================");
            // 保存订单状态流转信息
            OrderStatusDomain orderStatus = new OrderStatusDomain();
            orderStatus.setStatus(OrderStatus.FINISH.getValue());
            orderStatus.setName("消费者收货");
            orderStatus.setStatusTime(LocalDateTime.now());
            orderStatus.setOrderId(order.getId());
            orderStatusMapper.insert(orderStatus);
            System.out.println("支付，状态机反馈信息：" + message.getHeaders().toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @OnTransition(source = "WAIT_TAKING", target = "SHOP_CANCEL")
    public boolean untakingTransition(Message<OrderStatusChangeEvent> message){
        OrderInfo order = (OrderInfo) message.getHeaders().get("order");
        try {
            // 修改状态
            order.setStatus(OrderStatus.SHOP_CANCEL.getValue());
            order.setStatusName("商家拒绝接单");
            orderInfoMapper.updateById(order);
            // 保存订单状态流转信息
            OrderStatusDomain orderStatus = new OrderStatusDomain();
            orderStatus.setStatus(OrderStatus.SHOP_CANCEL.getValue());
            orderStatus.setName("商家拒绝接单");
            orderStatus.setStatusTime(LocalDateTime.now());
            orderStatus.setOrderId(order.getId());
            orderStatusMapper.insert(orderStatus);
            System.out.println("支付，状态机反馈信息：" + message.getHeaders().toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @OnTransition(source = {"WAIT_PAYMENT","WAIT_TAKING","WAIT_DELIVER","WAIT_RECEIVE"},target = "CONSUMER_CANCEL")
    public boolean cancelTransition(Message<OrderStatusChangeEvent> message){
        OrderInfo order = (OrderInfo) message.getHeaders().get("order");
        try {
            // 修改状态
            order.setStatus(OrderStatus.CONSUMER_CANCEL.getValue());
            order.setStatusName("消费者取消订单");
            orderInfoMapper.updateById(order);
            // 保存订单状态流转信息
            OrderStatusDomain orderStatus = new OrderStatusDomain();
            orderStatus.setStatus(OrderStatus.CONSUMER_CANCEL.getValue());
            orderStatus.setName("消费者取消订单");
            orderStatus.setStatusTime(LocalDateTime.now());
            orderStatus.setOrderId(order.getId());
            orderStatusMapper.insert(orderStatus);
            System.out.println("支付，状态机反馈信息：" + message.getHeaders().toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @OnTransition(source = "FINISH",target = "REFUND")
    public boolean refundTransition(Message<OrderStatusChangeEvent> message){
        OrderInfo order = (OrderInfo) message.getHeaders().get("order");
        try {
            // 修改状态
            order.setStatus(OrderStatus.REFUND.getValue());
            order.setStatusName("消费者退款");
            orderInfoMapper.updateById(order);
            // 保存订单状态流转信息
            OrderStatusDomain orderStatus = new OrderStatusDomain();
            orderStatus.setStatus(OrderStatus.REFUND.getValue());
            orderStatus.setName("消费者退款");
            orderStatus.setStatusTime(LocalDateTime.now());
            orderStatus.setOrderId(order.getId());
            orderStatusMapper.insert(orderStatus);
            System.out.println("支付，状态机反馈信息：" + message.getHeaders().toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @OnTransition(source = "FINISH",target = "COMMENT")
    public boolean commentTransition(Message<OrderStatusChangeEvent> message){
        OrderInfo order = (OrderInfo) message.getHeaders().get("order");
        try {
            // 修改状态
            order.setStatus(OrderStatus.COMMENT.getValue());
            order.setStatusName("消费者评论");
            orderInfoMapper.updateById(order);
            // 保存订单状态流转信息
            OrderStatusDomain orderStatus = new OrderStatusDomain();
            orderStatus.setStatus(OrderStatus.COMMENT.getValue());
            orderStatus.setName("消费者评论");
            orderStatus.setStatusTime(LocalDateTime.now());
            orderStatus.setOrderId(order.getId());
            orderStatusMapper.insert(orderStatus);
            System.out.println("支付，状态机反馈信息：" + message.getHeaders().toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

