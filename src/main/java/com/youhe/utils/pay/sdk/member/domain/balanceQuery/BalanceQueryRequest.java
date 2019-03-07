package com.youhe.utils.pay.sdk.member.domain.balanceQuery;

import java.util.UUID;

public class BalanceQueryRequest {
	private String customerCode;
	private String memberCustomerCode;
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

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

}
