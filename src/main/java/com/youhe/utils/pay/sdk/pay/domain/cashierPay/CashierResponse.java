package com.youhe.utils.pay.sdk.pay.domain.cashierPay;

import java.util.UUID;

import com.youhe.utils.pay.sdk.domain.Response;

/**
 * @Author : Author
 * @Date : 2018/1/29 14:48
 * @Description : 下单返回参数封装
 */
public class CashierResponse extends Response {

    /**
     * 收银台URL
     */
    private String casherUrl;
    /**
     * 商户单号
     */
    private String outTradeNo;

	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

    public String getCasherUrl() {
        return casherUrl;
    }
    public void setCasherUrl(String casherUrl) {
        this.casherUrl = casherUrl;
    }
    public String getOutTradeNo() {
        return outTradeNo;
    }
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
    public String getNonceStr() {
        return nonceStr;
    }
    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }
}
