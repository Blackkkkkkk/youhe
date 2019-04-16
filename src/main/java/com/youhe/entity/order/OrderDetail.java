package com.youhe.entity.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youhe.entity.SysBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-16
 */
@TableName("shop_order_detail")
@Getter
@Setter
@Accessors(chain = true)
public class OrderDetail extends SysBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 大订单号
     */
    private String bOrderNum;

    /**
     * 商品ID
     */
    private Long commodityId;

    /**
     * 小订单号
     */
    private String sOrderNum;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 订单总金额：单位分
     */
    private Integer price;

    /**
     * 商品数量 =
     */
    private Integer num;

    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
