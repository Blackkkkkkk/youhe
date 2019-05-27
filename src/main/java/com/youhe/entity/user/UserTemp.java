package com.youhe.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youhe.entity.role.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@TableName("shop_order_detail")
@Getter
@Setter
@Accessors(chain = true)
public class UserTemp implements Serializable {

    private static final long serialVersionUID = 1L;
    private String oldpassword;





}