package com.youhe.utils.pay.sdk.pay.domain.paymenQuery;

import java.util.UUID;

/**
 * 支付结果查询
 */
public class PaymentQueryRequest {
	
	private String outTradeNo;
	private String transactionNo;
	private String customerCode;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
	
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
