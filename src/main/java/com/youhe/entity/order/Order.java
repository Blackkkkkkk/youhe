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
     * 商品id  逗号隔开
     */
    private String shopId;

    /**
     * 商品价格 逗号隔开
     */
    private String pirce;

    /**
     * 商品数量  逗号隔开
     */
    private String num;

    /**
     * 大订单号
     */
    private String bOrderNum;

    /**
     * 订单总金额：单位分
     */
    private Integer totalPrice;

    /**
     * // 订单状态   0 未支付   1 已支付  2 运输中  3完成
     */
    private Integer status;

    /**
     * 收货地址
     */
    private String deliveryAddr;

    /**
     * 完成时间
     */
    private String endTime;

    /**
     * 创建时间
     */
    private String createTime;


    //查询参数
    private String userName;


    //收件人
    private String addressee;

    //收件人手机
    private String phone;


    //判断是查看还是编辑
    private String actionType;
}
