package org.example.rocketMQ.order;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.example.domain.order.OrderInfo;
import org.example.enums.OrderStatus;
import org.example.service.impl.OrderInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 刘文轩
 * @Date 2024/4/16 19:29
 * 订单超时未支付延时队列
 */
@Component
@RocketMQMessageListener(consumerGroup = ("orderCancelConsumer"), topic = ("orderCancelTopic"))
public class OrderCancelConsumer implements RocketMQListener<MessageExt> {

    @Autowired
    private OrderInfoServiceImpl orderInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(MessageExt messageExt) {
        try{
            System.out.println("延时消息队列");
            // 获取消息
            byte[] body = messageExt.getBody();
            String messageId = new String(body);
            // 封装json参数
            Map<String,String> params = new HashMap<>();
            params.put("messageId", messageId);
            String paramsStr = JSON.toJSONString(params);
            System.out.println(paramsStr);
            // 获取主键
            String id = orderInfoService.getOrderIdByMessageId(paramsStr);
            // 判断状态
            OrderInfo orderInfo = orderInfoService.getById(id);
            if (orderInfo.getStatus().equals(OrderStatus.WAIT_PAYMENT.getValue())) {
                LambdaUpdateWrapper<OrderInfo> updateWrapper = new LambdaUpdateWrapper<OrderInfo>()
                        .eq(OrderInfo::getId, id)
                        .set(OrderInfo::getStatus, OrderStatus.SYSTEM_CANCEL.getValue())
                        .set(OrderInfo::getStatusName, OrderStatus.SYSTEM_CANCEL.getName());
                orderInfoService.update(updateWrapper);
            }
        }catch (Exception e){
            e.printStackTrace();

        }
    }
}
