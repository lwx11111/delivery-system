package org.example.params;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author 刘文轩
 * @Date 2024/2/29 11:35
 */
@Data
public class UpdateSumScoreParams {

    /**
     * 店铺评分
     */
//    @NotNull(value = "店铺评分不能为空")
    private Integer score;

    /**
     * 店铺评分
     */
//    @NotNull(value = "店铺ID不能为空")
    private String shopId;


}
