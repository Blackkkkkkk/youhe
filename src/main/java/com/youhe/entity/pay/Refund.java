package com.youhe.entity.pay;


import com.youhe.common.Constant;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Refund {

    private String transactionNo = "";//易票联订单号
    private String outTradeNo; //商户订单号
    private String customerCode; // 商户号
    private Long amount; // 金额
    private final String notifyUrl = Constant.REFUND_NOTIFYURL;//退款异步通知结果
    private final String sourceChannel = "API";//渠道类型
    private String outRefundNo; //商户退款单号
    private Long refundAmount;// 退款金额
    private String remark = "";// 退款说明
    private Long userId;//门户的id


    //退款成功返回的参数

}
