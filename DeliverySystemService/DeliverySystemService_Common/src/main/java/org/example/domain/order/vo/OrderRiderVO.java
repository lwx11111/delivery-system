package org.example.domain.order.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.order.OrderInfo;

import java.time.LocalDateTime;

/**
 * @Author 刘文轩
 * @Date 2023/12/6 23:11
 * 骑手端展示的订单信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRiderVO {
    private String id;
    private String shopName;
    private String shopLocation;
    private LocalDateTime expectedTime;
    private String location;
    private Double deliveryCharge;
    private String remark;
    private Integer status;

    public OrderRiderVO(OrderInfo orderInfo) {
        this.id = orderInfo.getId();
        this.expectedTime = orderInfo.getExpectedTime();
        this.location = orderInfo.getLocation();
        this.deliveryCharge = orderInfo.getDeliveryCharge();
        this.remark = orderInfo.getRemark();
        this.status = orderInfo.getStatus();
    }
}
