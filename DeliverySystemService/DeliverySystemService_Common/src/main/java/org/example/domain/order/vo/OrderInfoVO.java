package org.example.domain.order.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.order.OrderItem;
import org.example.domain.shop.Shop;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Author 刘文轩
 * @Date 2023/12/1 10:19
 * 用来展示的订单信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoVO {
    private String id;

    private String shopId;
    private Shop shop;

    private String userId;

    private String deliveryRiderId;

    private Double packingCharges;

    private Double deliveryCharge;

    private LocalDateTime expectedTime;

    private String location;

    private String deliveryService;

    private LocalDateTime orderTime;

    private Double totalCharge;

    private Integer status;

    private String paymentMethod;

    private String remark;

    private String tableware;

    private List<OrderItem> orderItems;

}
