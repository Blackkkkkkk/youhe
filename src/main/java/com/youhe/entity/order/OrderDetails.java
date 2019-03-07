package com.youhe.entity.order;

import com.youhe.entity.SysBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
public class OrderDetails extends SysBaseEntity implements Serializable {
    private static final long serialVersionUID = -788735124416960661L;

    private Integer id;//主键
    private String bigOrderCode; //大订单编码
    private String orderCode; //订单编码
    private String name;              //商品名称
    private Integer pirce;                //价格
    private Integer num;                 //商品数量
    private String remark;           //商品备注

}
