package org.example.dto;

import lombok.Data;
import org.example.domain.Address;

/**
 * @Author 刘文轩
 * @Date 2024/3/14 21:51
 * 解决feign只能一个@RequestBody的问题
 */
@Data
public class DoubleAddressDto {

    private Address departure;
    private Address arrival;
}
