package org.example.domain.shop.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.shop.Shop;
import org.example.domain.shop.ShopItem;

import java.util.List;

/**
 * @Author 刘文轩
 * @Date 2023/12/2 13:08
 * 用于收藏夹，购物车展示
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopWithItemVO {

    private Shop shop;
    private List<ShopItem> shopItems;

}
