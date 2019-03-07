package com.youhe.utils.pay.sdk.member.domain.withdraw;

import java.util.UUID;

import com.youhe.utils.pay.sdk.domain.Response;

public class WithdrawResponse extends Response {
	private String outTradeNo;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

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
