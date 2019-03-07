package com.youhe.entity.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayResult {

    private Integer payState;    //支付状态 1 成功
    private String outTradeNo;   //商户订单编号
    private String transactionNo; //
    private String customerCode;   //商户号
    private String attachData;// 原样复制统一支付请求中的attachData
    private String nonceStr;//32 个字符的随机字符串，用于保证签名不可预测
}
