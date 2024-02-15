package org.example.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dou_area")
@Schema(name="_DouArea对象", description="")
public class SysArea extends Model<SysArea> {

    private static final long serialVersionUID=1L;

    @Excel(name = "areaId")
    @TableId(value = "area_id", type = IdType.AUTO)
    private Integer areaId;

    @Excel(name = "parentId")
    @TableField("parent_id")
    private Integer parentId;

    @Excel(name = "name")
    @TableField("name")
    private String name;

}
