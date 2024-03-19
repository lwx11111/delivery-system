package org.example.domain.shop;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Time;

import com.baomidou.mybatisplus.annotation.TableField;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.Map;
/**
 * <p>
 * 店铺信息
 * </p>
 *
 * @author lwx20
 * @since 2023-10-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("shop")
@Schema(name="店铺信息_Shop对象", description="店铺信息")
public class Shop extends Model<Shop> {

    private static final long serialVersionUID=1L;

    @Excel(name = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Excel(name = "userId")
    @TableField("user_id")
    private String userId;

    /**
    * 店铺名
    */
    @Schema(name = "店铺名")
    @Excel(name = "店铺名")
    @TableField("name")
    private String name;

    /**
    * 省
    */
    @Schema(name = "省")
    @Excel(name = "省")
    @TableField("province")
    private String province;

    /**
    * 县
    */
    @Schema(name = "县")
    @Excel(name = "县")
    @TableField("county")
    private String county;

    /**
    * 具体地址
    */
    @Schema(name = "具体地址")
    @Excel(name = "具体地址")
    @TableField("location")
    private String location;

    /**
    * 店铺评分
    */
    @Schema(name = "店铺评分")
    @Excel(name = "店铺评分")
    @TableField("score")
    private Double score = 4.0;

    /**
    * 销量
    */
    @Schema(name = "销量")
    @Excel(name = "销量")
    @TableField("sales_volume")
    private Integer salesVolume = 0;

    /**
    * 起送价格
    */
    @Schema(name = "起送价格")
    @Excel(name = "起送价格")
    @TableField("min_price")
    private Double minPrice;

    /**
    * 配送费
    */
    @Schema(name = "配送费")
    @Excel(name = "配送费")
    @TableField("delivery_charge")
    private Double deliveryCharge;

    /**
    * 安全档案图片
    */
    @Schema(name = "安全档案图片")
    @Excel(name = "安全档案图片")
    @TableField("safety_file")
    private String safetyFile;

    /**
    * 开门时间
    */
    @Schema(name = "开门时间")
    @Excel(name = "开门时间")
    @TableField("open_time")
    private Time openTime;

    /**
    * 关门时间
    */
    @Schema(name = "关门时间")
    @Excel(name = "关门时间")
    @TableField("close_time")
    private Time closeTime;

    /**
    * 状态
    */
    @Schema(name = "状态")
    @Excel(name = "状态")
    @TableField("status")
    private Integer status = 1;

    /**
    * 店铺描述
    */
    @Schema(name = "店铺描述")
    @Excel(name = "店铺描述")
    @TableField("description")
    private String description;


    @Schema(name = "店铺图片路径")
    @Excel(name = "店铺图片路径")
    @TableField("picture")
    private String picture;

    @Schema(name = "总分1-5 整数")
    @Excel(name = "总分1-5 整数")
    @TableField("sum_score")
    Integer sumScore;

    @Schema(name = "打分总人数")
    @Excel(name = "打分总人数")
    @TableField("sum_people")
    Integer sumPeople;

    @TableField(exist = false)
    private String distanceKm;

    @TableField(exist = false)
    private String duration;


    @TableField(exist = false)
    private Map<String,String> params;

    @TableField(exist = false)
    private String[] categoryIds;



}
