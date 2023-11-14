package org.example.domain.shop;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 店铺物品表
 * </p>
 *
 * @author lwx20
 * @since 2023-10-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("shop_item")
//@Schema(name="店铺物品表_ShopItem对象", description="店铺物品表")
public class ShopItem extends Model<ShopItem> {

    private static final long serialVersionUID=1L;

    @Excel(name = "id")
        @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    @Excel(name = "shopId")
    @TableField("shop_id")
    private Integer shopId;

    /**
    * 物品名
    */
    @Schema(name = "物品名")
    @Excel(name = "物品名")
    @TableField("name")
    private String name;

    /**
    * 物品图片
    */
    @Schema(name = "物品图片")
    @Excel(name = "物品图片")
    @TableField("picture")
    private String picture;

    /**
    * 物品介绍
    */
    @Schema(name = "物品介绍")
    @Excel(name = "物品介绍")
    @TableField("description")
    private String description;

    /**
     * 物品价格
     */
    @Schema(name = "物品价格")
    @Excel(name = "物品价格")
    @TableField("price")
    private double price;

    @TableField(exist = false)
    private Map<String,String> params;


//    @Override
//    protected Serializable pkVal(){
//            return this.id;
//        }

}
