package org.example.rocketMQ;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author 刘文轩
 * @Date 2024/4/13 21:10
 *
 */
public class PullConsumer {

    public static void main(String[] args) {
        fun();
    }

    /**
     * 随机queue获取消息
     */
    public static void fun1(){
        try {
            DefaultLitePullConsumer consumer = new DefaultLitePullConsumer("pullConsumer");
            consumer.setNamesrvAddr("127.0.0.1:9876");
            consumer.subscribe("TopicTest", "*");
            consumer.start();
            while(true){
                List<MessageExt> messages = consumer.poll();
                for (MessageExt msg : messages) {
                    System.out.println(new String(msg.getBody()));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 指定queue方式
     */
    public static void fun(){
        try {
            DefaultLitePullConsumer consumer = new DefaultLitePullConsumer("pullConsumer");
            consumer.setNamesrvAddr("127.0.0.1:9876");
            consumer.start();
            Collection<MessageQueue> messageQueues = consumer.fetchMessageQueues("TopicTest");
            ArrayList<MessageQueue> messageQueues1 = new ArrayList<>(messageQueues);
            consumer.assign(messageQueues1);
            consumer.seek(messageQueues1.get(0), 1);
            while(true){
                List<MessageExt> messages = consumer.poll();
                for (MessageExt msg : messages) {
                    System.out.println(msg);
                    System.out.println(new String(msg.getBody()));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
