package org.example.rocketMQ;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.example.domain.order.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @Author 刘文轩
 * @Date 2024/4/16 15:56
 */
@Component
public class OrderSaveProducer {

    @Autowired
    private RocketMQTemplate rocketMqTemplate;

    /**
     *
     * @param order
     * @return 消息号
     * @throws Exception
     */
    public String sendSaveOrder(OrderInfo order) throws Exception {
        // 发送创建消息
        String json = JSON.toJSONString(order);
        SendResult sendResult =  rocketMqTemplate.syncSend("saveOrderTopic",json);
        // 发送延时消息 半个小时
        SendResult delayResult = rocketMqTemplate.syncSend("orderCancelTopic",
                MessageBuilder.withPayload(sendResult.getMsgId().getBytes(StandardCharsets.UTF_8)).build(),
                3000, 16);
        return sendResult.getMsgId();
    }
}
