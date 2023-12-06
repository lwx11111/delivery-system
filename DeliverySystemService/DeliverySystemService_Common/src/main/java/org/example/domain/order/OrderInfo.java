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
import org.example.domain.shop.Shop;

import java.util.List;
import java.util.Map;
/**
 * <p>
 *
 * </p>
 *
 * @author lwx20
 * @since 2023-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_info")
@Schema(name="_OrderInfo对象", description="")
public class OrderInfo extends Model<OrderInfo> {

    private static final long serialVersionUID = 1L;

    @Excel(name = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Excel(name = "shopId")
    @TableField("shop_id")
    private String shopId;

    @Schema(description = "用户ID")
    @Excel(name = "用户ID")
    @TableField("user_id")
    private String userId;

    /**
     * 配送骑手
     */
    @Schema(description = "配送骑手")
    @Excel(name = "配送骑手")
    @TableField("delivery_rider_id")
    private String deliveryRiderId;

    /**
    * 打包费
    */
    @Schema(description = "打包费")
    @Excel(name = "打包费")
    @TableField("packing_charges")
    private Double packingCharges;

    /**
    * 配送费
    */
    @Schema(description = "配送费")
    @Excel(name = "配送费")
    @TableField("delivery_charge")
    private Double deliveryCharge;

    /**
    * 期望时间
    */
    @Schema(description = "期望时间")
    @Excel(name = "期望时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("expected_time")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expectedTime;

    /**
    * 地址
    */
    @Schema(description = "地址")
    @Excel(name = "地址")
    @TableField("location")
    private String location;

    /**
    * 配送服务
    */
    @Schema(description = "配送服务")
    @Excel(name = "配送服务")
    @TableField("delivery_service")
    private String deliveryService;

    /**
    * 下单时间
    */
    @Schema(description = "下单时间")
    @Excel(name = "下单时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("order_time")
        @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;

    /**
    * 总金额
    */
    @Schema(description = "总金额")
    @Excel(name = "总金额")
    @TableField("total_charge")
    private Double totalCharge;

    /**
    * 状态
    */
    @Schema(description = "状态")
    @Excel(name = "状态")
    @TableField("status")
    private Integer status;

    @Schema(description = "支付方式")
    @Excel(name = "支付方式")
    @TableField("payment_method")
    private String paymentMethod;

    @Schema(description = "备注")
    @Excel(name = "备注")
    @TableField("remark")
    private String remark;

    @Schema(description = "餐具数量")
    @Excel(name = "餐具数量")
    @TableField("tableware")
    private String tableware;

    @TableField(exist = false)
    private Shop shop;

    @TableField(exist = false)
    private List<OrderItem> orderItems;

    @TableField(exist = false)
    private Map<String,String> params;

//    @Override
//    protected Serializable pkVal(){
//            return this.id;
//        }

}
