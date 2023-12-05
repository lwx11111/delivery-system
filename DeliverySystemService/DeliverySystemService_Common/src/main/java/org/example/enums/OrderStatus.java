package org.example.enums;

/**
 * @Author 刘文轩
 * @Date 2023/12/4 16:33
 */

/**
 * 订单状态
 */
public enum OrderStatus {
    //待支付，待发货，待收货，订单结束
    WAIT_PAYMENT(1,"待支付"),
    WAIT_TAKING(2,"待接单"),
    WAIT_DELIVER(3,"待发货"),
    WAIT_RECEIVE(4,"待收货"),
    FINISH(5,"订单结束");

    private Integer value;
    private String name;

    OrderStatus(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
    public Integer getValue(){
        return value;
    }

    public String getName(){
        return name;
    }
}
