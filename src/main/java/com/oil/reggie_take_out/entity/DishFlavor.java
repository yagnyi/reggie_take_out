package com.oil.reggie_take_out.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * @TableName dish_flavor
 */
@TableName(value ="dish_flavor")
@Data
public class DishFlavor implements Serializable {
    private Long id;

    private Long dishId;

    private String name;

    private String value;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}