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
import java.time.LocalDateTime;

/**
 * <p>
 * 商品订单表
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-16
 */
@TableName("shop_order")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Order extends SysBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 大订单号
     */
    private String bOrderNum;

    /**
     * 订单总金额：单位分
     */
    private Integer totalPrice;

    /**
     * 订单状态：0-已取消；30-待支付；60-已支付待发货；90-已发货
     */
    private Integer status;

    /**
     * 完成时间
     */
    private LocalDateTime endTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
