package org.example.domain.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 刘文轩
 * @Date 2023/11/28 17:45
 * 用于店铺内物品展示
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopItemVO {

    String categoryName;
    List<ShopItem> items;
}
