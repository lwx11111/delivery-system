package org.example.params;

import lombok.Data;

/**
 * @Author 刘文轩
 * @Date 2024/3/15 4:59
 */
@Data
public class GetExpectedTimeParams {

    @NotB
    private String userAddressId;

    private String shopAddressId;
}
