package org.example.domain.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.Map;
/**
 * <p>
 *
 * </p>
 *
 * @author lwx20
 * @since 2023-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_shop_item")
@Schema(name="_OrderShopItem对象", description="")
public class OrderShopItem extends Model<OrderShopItem> {

    private static final long serialVersionUID=1L;

    @Excel(name = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Excel(name = "orderId")
    @TableField("order_id")
    private String orderId;

    @Excel(name = "shopItemId")
    @TableField("shop_item_id")
    private String shopItemId;

    /**
    * 数量
    */
    @Schema(description = "数量")
    @Excel(name = "数量")
    @TableField("amount")
    private Integer amount;

    @Schema(description = "总价")
    @Excel(name = "总价")
    @TableField("total")
    private Double total;

    @TableField(exist = false)
    private Map<String,String> params;


}
