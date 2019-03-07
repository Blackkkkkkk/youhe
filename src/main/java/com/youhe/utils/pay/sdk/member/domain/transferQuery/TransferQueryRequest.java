package com.youhe.utils.pay.sdk.member.domain.transferQuery;

import java.util.UUID;

public class TransferQueryRequest {
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
