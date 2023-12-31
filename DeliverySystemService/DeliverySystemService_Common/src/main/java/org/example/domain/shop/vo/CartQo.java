package org.example.domain.shop.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author 刘文轩
 * @Date 2023/12/18 16:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartQo {
    /**
     * 购物车id
     */
    private String id;
    private String shopItemId;
    private String shopId;
    private String picture;
    private Integer amount;
    private String shopItemName;
    private Double price;
    private String shopName;
}
