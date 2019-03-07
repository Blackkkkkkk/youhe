package com.youhe.utils.pay.sdk.member.domain.transferQuery;

import java.util.UUID;

import com.youhe.utils.pay.sdk.domain.Response;

public class TransferQueryResponse extends Response {
	private String outTradeNo;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
	private String srcCustomerCode;
	private String targetCustomerCode;
	private Long amount;
	private String transferResult;

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

	public String getSrcCustomerCode() {
		return srcCustomerCode;
	}

	public void setSrcCustomerCode(String srcCustomerCode) {
		this.srcCustomerCode = srcCustomerCode;
	}

	public String getTargetCustomerCode() {
		return targetCustomerCode;
	}

	public void setTargetCustomerCode(String targetCustomerCode) {
		this.targetCustomerCode = targetCustomerCode;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getTransferResult() {
		return transferResult;
	}

	public void setTransferResult(String transferResult) {
		this.transferResult = transferResult;
	}
	
}
