package com.oil.reggie_take_out.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName address_book
 */
@TableName(value ="address_book")
@Data
public class AddressBook implements Serializable {
    private Long id;

    private Long userId;

    private String consignee;

    private Integer sex;

    private String phone;

    private String provinceCode;

    private String provinceName;

    private String cityCode;

    private String cityName;

    private String districtCode;

    private String districtName;

    private String detail;

    private String label;

    private Integer isDefault;

    private Date createTime;

    private Date updateTime;

    private Long createUser;

    private Long updateUser;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}