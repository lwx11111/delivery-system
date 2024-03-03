package org.example.dto;

import lombok.Data;

/**
 * @Author 刘文轩
 * @Date 2024/3/2 15:46
 */
@Data
public class OrderPayDto {

    private String orderId;

    private String paymentMethod;
}
