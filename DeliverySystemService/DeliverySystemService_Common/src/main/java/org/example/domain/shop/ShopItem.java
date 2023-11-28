package org.example.domain.shop;

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
 * 店铺物品表
 * </p>
 *
 * @author lwx20
 * @since 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("shop_item")
@Schema(name="店铺物品表_ShopItem对象", description="店铺物品表")
public class ShopItem extends Model<ShopItem> {

    private static final long serialVersionUID=1L;

    @Excel(name = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Excel(name = "shopId")
    @TableField("shop_id")
    private String shopId;

    /**
    * 物品名
    */
    @Schema(description = "物品名")
    @Excel(name = "物品名")
    @TableField("name")
    private String name;

    /**
    * 物品图片
    */
    @Schema(description = "物品图片")
    @Excel(name = "物品图片")
    @TableField("picture")
    private String picture;

    /**
    * 物品介绍
    */
    @Schema(description = "物品介绍")
    @Excel(name = "物品介绍")
    @TableField("description")
    private String description;

    /**
    * 分类id
    */
    @Schema(description = "分类id")
    @Excel(name = "分类id")
    @TableField("item_category_id")
    private String itemCategoryId;

    /**
    * 价格
    */
    @Schema(description = "价格")
    @Excel(name = "价格")
    @TableField("price")
    private BigDecimal price;

    /**
    * 分类名
    */
    @Schema(description = "分类名")
    @Excel(name = "分类名")
    @TableField("category_name")
    private String categoryName;

    @TableField(exist = false)
    private Map<String,String> params;


}
