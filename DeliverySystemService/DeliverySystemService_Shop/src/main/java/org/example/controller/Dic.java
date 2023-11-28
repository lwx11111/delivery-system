package org.example.controller;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.KeySequence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author chenqian01
 * @since 2021-08-09
 */
@Data
public class Dic extends Model<Dic> {

    private static final long serialVersionUID = 1L;

    @Excel(name = "code")
    @TableId(value = "CODE", type = IdType.INPUT)

    private String code;

    @Excel(name = "name")
    @TableField("NAME")

    private String name;

    @Excel(name = "remark")
    @TableField("REMARK")

    private String remark;

    @Excel(name = "type")
    @TableField("TYPE")

    private String type;

    @Excel(name = "parentid")
    @TableField("PARENTID")

    private String parentid;


}
