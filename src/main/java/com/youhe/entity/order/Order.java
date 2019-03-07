package com.youhe.entity.order;


import com.youhe.entity.SysBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Accessors(chain = true)
@Getter
@Setter
public class Order extends SysBaseEntity implements Serializable {


    private static final long serialVersionUID = 3160094850506230197L;
    private Integer id;//主键
    private String bigOrderCode;//订单编码
    private Integer status;//订单状态：0 未支付   1 支付成功  2 支付超时
    private Integer transportStatus;//0 未发货   1 已发货   2 已收货
    private Date orderEndTime;// 订单完成的时间
    private Integer totalPrice;//总价
}
