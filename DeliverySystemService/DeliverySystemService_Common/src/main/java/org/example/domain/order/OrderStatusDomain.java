package org.example.domain.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.Map;
/**
 * <p>
 * 点单状态流程表
 * </p>
 *
 * @author lwx20
 * @since 2024-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_status")
@Schema(name="点单状态流程表_OrderStatus对象", description="点单状态流程表")
public class OrderStatusDomain extends Model<OrderStatusDomain> {

    private static final long serialVersionUID=1L;

    @Excel(name = "id")
        @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
    * 订单号
    */
    @Schema(description = "订单号")
    @Excel(name = "订单号")
    @TableField("order_id")
    private String orderId;

    /**
    * 状态
    */
    @Schema(description = "状态")
    @Excel(name = "状态")
    @TableField("status")
    private Integer status;

    /**
    * 状态名
    */
    @Schema(description = "状态名")
    @Excel(name = "状态名")
    @TableField("name")
    private String name;

    /**
    * 状态切换时间
    */
    @Schema(description = "状态切换时间")
    @Excel(name = "状态切换时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("status_time")
        @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime statusTime;

    @TableField(exist = false)
    private Map<String,String> params;


}
