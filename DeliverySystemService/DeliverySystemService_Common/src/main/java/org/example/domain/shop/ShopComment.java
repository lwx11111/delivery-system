package org.example.domain.shop;

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
 *
 * </p>
 *
 * @author lwx20
 * @since 2023-10-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("shop_comment")
//@Schema(name="_ShopComment对象", description="")
public class ShopComment extends Model<ShopComment> {

    private static final long serialVersionUID=1L;

    @Excel(name = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    @Excel(name = "shopId")
    @TableField("shop_id")
    private Integer shopId;

    @Excel(name = "userId")
    @TableField("user_id")
    private Integer userId;

    /**
    * 内容
    */
    @Schema(name = "内容")
    @Excel(name = "内容")
    @TableField("content")
    private String content;

    /**
    * 发布时间
    */
    @Schema(description = "发布时间")
    @Excel(name = "发布时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("release_time")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseTime;

    /**
    * 评分
    */
    @Schema(name = "评分")
    @Excel(name = "评分")
    @TableField("mark")
    private Integer mark;

    /**
    * 商家回复ID
    */
    @Schema(name = "商家回复ID")
    @Excel(name = "商家回复ID")
    @TableField("recover_id")
    private Integer recoverId;

    @TableField(exist = false)
    private Map<String,String> params;


//    @Override
//    protected Serializable pkVal(){
//            return this.id;
//        }

}
