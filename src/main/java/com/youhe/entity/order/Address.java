package com.youhe.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youhe.entity.SysBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 商品收货地址表
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-16
 */

@Getter
@Setter
@Accessors(chain = true)
public class Address extends SysBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 收货地址
     */
    private String deliveryAddr;

    private String createTime;


    //收件人
    private String addressee;

    //收件人手机
    private String phone;

}
