package com.youhe.utils.pay.sdk.member.domain.withdrawQuery;

import java.util.UUID;

import com.youhe.utils.pay.sdk.domain.Response;

public class WithdrawQueryResponse extends Response {
	private String outTradeNo;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
	private String memberCustomerCode;
	private String withdrawResult;
	private String endTime;
	private Long rAmount;
	private String currency;
	private Long vAmount;

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

	public String getWithdrawResult() {
		return withdrawResult;
	}

	public void setWithdrawResult(String withdrawResult) {
		this.withdrawResult = withdrawResult;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getrAmount() {
		return rAmount;
	}

	public void setrAmount(Long rAmount) {
		this.rAmount = rAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getvAmount() {
		return vAmount;
	}

	public void setvAmount(Long vAmount) {
		this.vAmount = vAmount;
	}

}
