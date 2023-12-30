package org.example.domain.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Author 刘文轩
 * @Date 2023/12/29 19:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("rider_order")
public class RiderOrder {

    @TableId("account_id")
    private String accountId;

    @TableField("status")
    private Integer status;

    @TableField("num")
    private Integer num;

    @TableField(exist = false)
    private Map<String,String> params;
}
