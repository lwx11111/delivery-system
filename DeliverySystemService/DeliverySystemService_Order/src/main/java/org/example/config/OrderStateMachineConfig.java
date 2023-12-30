package org.example.config;

/**
 * @Author 刘文轩
 * @Date 2023/12/4 16:34
 */

import org.example.Order;
import org.example.domain.order.OrderInfo;
import org.example.enums.OrderStatus;
import org.example.enums.OrderStatusChangeEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * 订单状态机配置
 */
@Configuration
@EnableStateMachine(name = "orderStateMachine")
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderStatus, OrderStatusChangeEvent> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStatus, OrderStatusChangeEvent> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Bean
    public StateMachineListener<OrderStatus, OrderStatusChangeEvent> listener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<OrderStatus, OrderStatusChangeEvent> from, State<OrderStatus, OrderStatusChangeEvent> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }

    /**
     * 配置状态
     * @param states
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderStatusChangeEvent> states) throws Exception {
        states
                .withStates()
                .initial(OrderStatus.WAIT_PAYMENT)
                .states(EnumSet.allOf(OrderStatus.class));
    }

    /**
     * 配置状态转换事件关系
     * @param transitions
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderStatusChangeEvent> transitions) throws Exception {
        transitions
                .withExternal().source(OrderStatus.WAIT_PAYMENT).target(OrderStatus.WAIT_TAKING)
                .event(OrderStatusChangeEvent.PAYED)
                .and()
                .withExternal().source(OrderStatus.WAIT_TAKING).target(OrderStatus.WAIT_DELIVER)
                .event(OrderStatusChangeEvent.TAKING)
                .and()
                .withExternal().source(OrderStatus.WAIT_DELIVER).target(OrderStatus.WAIT_RECEIVE)
                .event(OrderStatusChangeEvent.DELIVERY)
                .and()
                .withExternal().source(OrderStatus.WAIT_RECEIVE).target(OrderStatus.FINISH)
                .event(OrderStatusChangeEvent.RECEIVED)
                .and()
                .withExternal()
                // 消费者取消
                .source(OrderStatus.WAIT_PAYMENT)
                .target(OrderStatus.CONSUMER_CANCEL)
                .event(OrderStatusChangeEvent.CANCEL)
                .and().withExternal()
                .source(OrderStatus.WAIT_TAKING)
                .target(OrderStatus.CONSUMER_CANCEL)
                .event(OrderStatusChangeEvent.CANCEL)
                .and().withExternal()
                .source(OrderStatus.WAIT_DELIVER)
                .target(OrderStatus.CONSUMER_CANCEL)
                .event(OrderStatusChangeEvent.CANCEL)
                .and().withExternal()
                .source(OrderStatus.WAIT_RECEIVE)
                .target(OrderStatus.CONSUMER_CANCEL)
                .event(OrderStatusChangeEvent.CANCEL)
                // 商家取消
                .and().withExternal()
                .source(OrderStatus.WAIT_TAKING)
                .target(OrderStatus.SHOP_CANCEL)
                .event(OrderStatusChangeEvent.UNTAKING)
                // 消费者退款
                .and().withExternal()
                .source(OrderStatus.FINISH)
                .target(OrderStatus.REFUND)
                .event(OrderStatusChangeEvent.REFUND)
                // 评论
                .and().withExternal()
                .source(OrderStatus.FINISH)
                .target(OrderStatus.COMMENT)
                .event(OrderStatusChangeEvent.COMMENT);
    }

    /**
     * 持久化配置
     * 在实际使用中，可以配合Redis等进行持久化操作
     * @return
     */
    @Bean
    public DefaultStateMachinePersister persister(){
        return new DefaultStateMachinePersister<>(new StateMachinePersist<Object, Object, OrderInfo>() {

            @Override
            public void write(StateMachineContext<Object, Object> context, OrderInfo order) throws Exception {
                //此处并没有进行持久化操作
            }

            @Override
            public StateMachineContext<Object, Object> read(OrderInfo order) throws Exception {
                //此处直接获取Order中的状态，其实并没有进行持久化读取操作，通过这一步获取状态
                return new DefaultStateMachineContext<>(OrderStatus.getByKey(order.getStatus()), null, null, null);
            }
        });
    }
}
