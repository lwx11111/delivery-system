package org.example.qo;

import lombok.Data;
import org.example.domain.Address;
import org.example.domain.shop.Shop;

/**
 * @Author 刘文轩
 * @Date 2024/3/14 21:20
 */
@Data
public class listShopsQo {

    private Shop shop;

    private Address address;
}
