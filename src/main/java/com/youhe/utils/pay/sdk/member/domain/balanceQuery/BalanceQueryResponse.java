package com.youhe.utils.pay.sdk.member.domain.balanceQuery;

import java.util.UUID;

import com.youhe.utils.pay.sdk.domain.Response;

public class BalanceQueryResponse extends Response {
	private String outTradeNo;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
	private String memberCustomerCode;
	private Long balance;

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

	public String getMemberCustomerCode() {
		return memberCustomerCode;
	}

	public void setMemberCustomerCode(String memberCustomerCode) {
		this.memberCustomerCode = memberCustomerCode;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

}
