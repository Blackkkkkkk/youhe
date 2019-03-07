package com.youhe.utils.pay.sdk.pay.domain.split;

import java.util.UUID;

import com.youhe.utils.pay.sdk.domain.Response;

/**
 * 分账响应
 *
 */
public class SplitResponse extends Response {
	 private String outTradeNo;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getNonceStr() { return nonceStr; }
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
}
