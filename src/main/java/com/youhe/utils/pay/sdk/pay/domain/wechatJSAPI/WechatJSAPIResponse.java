package com.youhe.utils.pay.sdk.pay.domain.wechatJSAPI;

import java.util.UUID;

import com.youhe.utils.pay.sdk.domain.Response;

/**
 * @Author : Author
 * @Date : 2018/1/29 14:48
 * @Description : 下单返回参数封装
 */
public class WechatJSAPIResponse extends Response {

    private String amount;

    private String wxJsapiParam;

    /**
     * 商户单号
     */
    private String outTradeNo;

	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");


    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getWxJsapiParam() {
        return wxJsapiParam;
    }
    public void setWxJsapiParam(String wxJsapiParam) {
        this.wxJsapiParam = wxJsapiParam;
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
