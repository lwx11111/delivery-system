package org.example.enums;

/**
 * @Author 刘文轩
 * @Date 2023/12/4 16:33
 * 订单状态机
 */
public enum OrderStatus {
    DELETE(0,"逻辑删除"),
    WAIT_PAYMENT(1,"待支付"),
    WAIT_TAKING(2,"待接单"),
    WAIT_DELIVER(3,"待发货"),
    WAIT_RECEIVE(4,"待收货"),
    FINISH(5,"订单结束"),
    COMMENT(51,"订单评论"),
    CONSUMER_CANCEL(6,"消费者取消"),
    SHOP_CANCEL(7,"商家取消"),
    REFUND(8,"退款"),
    SYSTEM_CANCEL(9,"系统取消");

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

    /**
     * 根据value获得对应Enum对象
     * @param key
     * @return
     */
    public static OrderStatus getByKey(Integer key) {
        for (OrderStatus e : values()) {
            if (e.getValue().equals(key)) {
                return e;
            }
        }
        throw new RuntimeException("enum not exists.");
    }
}
