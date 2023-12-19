package org.example.domain.shop.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.Cart;
import org.example.domain.order.OrderItem;
import org.example.domain.shop.ShopItem;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

/**
 * @Author 刘文轩
 * @Date 2023/12/17 22:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartVO {

    /**
     * 购物车id
     */
    private String shopId;
    private String shopName;
    private List<CartQo> carts;
}
