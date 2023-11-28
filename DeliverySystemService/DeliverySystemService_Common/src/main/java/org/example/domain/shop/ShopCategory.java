package org.example.domain.shop;

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
 * 商铺分中间表
 * </p>
 *
 * @author lwx20
 * @since 2023-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("shop_category")
@Schema(name="商铺分中间表_ShopCategory对象", description="商铺分中间表")
public class ShopCategory extends Model<ShopCategory> {

    private static final long serialVersionUID=1L;

    @Excel(name = "id")
        @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Excel(name = "shopId")
    @TableField("shop_id")
    private String shopId;

    @Excel(name = "categoryId")
    @TableField("category_id")
    private String categoryId;

    @TableField(exist = false)
    private Map<String,String> params;


}
