package com.youhe.utils.pay.sdk.pay.domain.WechatH5;

import com.youhe.utils.pay.sdk.domain.Response;

/**
 * @Author : Author
 * @Date : 2018/1/29 14:48
 * @Description : 下单返回参数封装
 */
public class WechatH5Response  extends Response {

    /**
     * 支付跳转链接
     */
    private String mwebURL;

    private String outTradeNo;

    private String transactionNo;

    public String getMwebURL() { return mwebURL; }
    public void setMwebURL(String mwebURL) {
        this.mwebURL = mwebURL;
    }
    public String getOutTradeNo() {
        return outTradeNo;
    }
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
    public String getTransactionNo() {
        return transactionNo;
    }
    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }
}
