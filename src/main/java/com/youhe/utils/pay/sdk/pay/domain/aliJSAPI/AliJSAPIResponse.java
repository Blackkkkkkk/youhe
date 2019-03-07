package com.youhe.utils.pay.sdk.pay.domain.aliJSAPI;


import java.util.UUID;

import com.youhe.utils.pay.sdk.domain.Response;

/**
 * 支付宝服务窗支付响应参数
 *
 * @date 2018年3月14日 上午9:40:19
 */
public class AliJSAPIResponse extends Response{
	private String alipayTradeNo;
	private String outTradeNo;
	private Long amount;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

	public String getAlipayTradeNo() {
		return alipayTradeNo;
	}
	public void setAlipayTradeNo(String alipayTradeNo) {
		this.alipayTradeNo = alipayTradeNo;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	
}
