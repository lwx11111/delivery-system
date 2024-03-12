package org.example.vo;

import lombok.Data;

/**
 * @Author 刘文轩
 * @Date 2024/3/5 22:01
 */
@Data
public class EarningsDataVo {

    /**
     * 所有订单总价 商家收益 = total - deliveryChargeTotal
     */
    private Double total;

    private Integer num;

    /**
     * 配送费总计
     */
    private Double deliveryChargeTotal;
}
