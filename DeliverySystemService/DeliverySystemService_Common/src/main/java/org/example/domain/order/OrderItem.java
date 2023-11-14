package org.example.domain.order;

import lombok.Data;
import org.example.domain.shop.ShopItem;

import java.util.List;

/**
 * @Author 刘文轩
 * @Date 2023/11/2 11:32
 */
@Data
public class OrderItem {
    private ShopItem shopItem;

    private Integer amount;
}
