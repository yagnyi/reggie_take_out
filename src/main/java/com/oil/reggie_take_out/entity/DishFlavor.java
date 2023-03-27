package com.oil.reggie_take_out.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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

    private Date createTime;

    private Date updateTime;

    private Long createUser;

    private Long updateUser;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}