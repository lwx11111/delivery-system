package org.example.rocketMQ.order;


import com.alibaba.fastjson2.JSON;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.example.domain.order.OrderInfo;
import org.example.service.impl.OrderInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author 刘文轩
 * @Date 2024/4/16 15:56
 */
@Component
@RocketMQMessageListener(consumerGroup = ("saveOrderConsumer"), topic = ("saveOrderTopic"))
public class OrderSaveConsumer implements RocketMQListener<MessageExt> {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderInfoServiceImpl orderInfoService;

    @Override
    public void onMessage(MessageExt messageExt) {
        try{
            // 获取消息
            byte[] body = messageExt.getBody();
            OrderInfo orderInfo = JSON.parseObject(new String(body), OrderInfo.class);
            System.out.println(orderInfo);
            // 保存逻辑
            String id = orderInfoService.saveByParam(orderInfo, null);
            // redis保存
            System.out.println("消费者" + messageExt.getMsgId());
            redisTemplate.opsForHash().put("HashKey", messageExt.getMsgId(), id);
            System.out.println(id);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
