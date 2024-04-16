package org.example.rocketMQ.order;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.example.domain.order.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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
        // 发送消息
        String json = JSON.toJSONString(order);
        SendResult sendResult =  rocketMqTemplate.syncSend("saveOrderTopic",json);
        return sendResult.getMsgId();
    }
}
