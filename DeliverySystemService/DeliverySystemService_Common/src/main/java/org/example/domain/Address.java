package org.example.domain;

import java.math.BigDecimal;
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
 * @since 2024-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("address")
@Schema(name="_Address对象", description="")
public class Address extends Model<Address> {

    private static final long serialVersionUID=1L;

    @Excel(name = "id")
        @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
    * 经度
    */
    @Schema(description = "经度")
    @Excel(name = "经度")
    @TableField("longitude")
    private BigDecimal longitude;

    /**
    * 纬度
    */
    @Schema(description = "纬度")
    @Excel(name = "纬度")
    @TableField("latitude")
    private BigDecimal latitude;

    /**
    * 省名
    */
    @Schema(description = "省名")
    @Excel(name = "省名")
    @TableField("province_name")
    private String provinceName;

    /**
    * 省ID
    */
    @Schema(description = "省ID")
    @Excel(name = "省ID")
    @TableField("province_id")
    private String provinceId;

    /**
    * 市名
    */
    @Schema(description = "市名")
    @Excel(name = "市名")
    @TableField("city_name")
    private String cityName;

    /**
    * 市ID
    */
    @Schema(description = "市ID")
    @Excel(name = "市ID")
    @TableField("city_id")
    private String cityId;

    /**
    * 县名
    */
    @Schema(description = "县名")
    @Excel(name = "县名")
    @TableField("county_name")
    private String countyName;

    /**
    * 县ID
    */
    @Schema(description = "县ID")
    @Excel(name = "县ID")
    @TableField("county_id")
    private String countyId;

    /**
    * 详细地址
    */
    @Schema(description = "详细地址")
    @Excel(name = "详细地址")
    @TableField("detail_address")
    private String detailAddress;

    /**
    * 联系人
    */
    @Schema(description = "联系人")
    @Excel(name = "联系人")
    @TableField("contacts")
    private String contacts;

    /**
    * 联系电话
    */
    @Schema(description = "联系电话")
    @Excel(name = "联系电话")
    @TableField("phone")
    private String phone;

    @Schema(description = "用户ID")
    @Excel(name = "用户ID")
    @TableField("account_id")
    private String accountId;

    @Schema(description = "门牌号")
    @Excel(name = "门牌号")
    @TableField("house_number")
    private String houseNumber;

    @TableField(exist = false)
    private Map<String,String> params;


}
