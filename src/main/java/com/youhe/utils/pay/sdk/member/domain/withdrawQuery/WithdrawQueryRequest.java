package com.youhe.utils.pay.sdk.member.domain.withdrawQuery;

import java.util.UUID;

public class WithdrawQueryRequest {
	private String customerCode;
	private String memberCustomerCode;
	private String outTradeNo;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getMemberCustomerCode() {
		return memberCustomerCode;
	}

	public void setMemberCustomerCode(String memberCustomerCode) {
		this.memberCustomerCode = memberCustomerCode;
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
