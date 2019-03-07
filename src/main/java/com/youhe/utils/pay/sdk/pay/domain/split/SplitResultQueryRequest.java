package com.youhe.utils.pay.sdk.pay.domain.split;

import java.util.UUID;

/**
 * 分账结果查询
 *
 */
public class SplitResultQueryRequest {
	
	private String outTradeNo;
	private String transactionNo;
	private String customerCode;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

	public SplitResultQueryRequest() { }

	public SplitResultQueryRequest(String outTradeNo, String transactionNo, String customerCode) {
		this.outTradeNo = outTradeNo;
		this.transactionNo = transactionNo;
		this.customerCode = customerCode;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
}
