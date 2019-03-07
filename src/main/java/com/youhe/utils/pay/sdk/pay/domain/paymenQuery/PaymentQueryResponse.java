package com.youhe.utils.pay.sdk.pay.domain.paymenQuery;

import java.util.UUID;

import com.youhe.utils.pay.sdk.domain.Response;

/**
 * 支付结果查询响应
 *
 */
public class PaymentQueryResponse extends Response {
	private String customerCode;
	private String outTradeNo;
	private String transactionNo;
	private Long amount;
	private String payState;
	private String payTime;
	private String settCycle;
	private Long settCycleInterval;
	private Long procedureFee;
	private String attachData;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
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

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getPayState() {
		return payState;
	}

	public void setPayState(String payState) {
		this.payState = payState;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getSettCycle() {
		return settCycle;
	}

	public void setSettCycle(String settCycle) {
		this.settCycle = settCycle;
	}

	public Long getSettCycleInterval() {
		return settCycleInterval;
	}

	public void setSettCycleInterval(Long settCycleInterval) {
		this.settCycleInterval = settCycleInterval;
	}

	public Long getProcedureFee() {
		return procedureFee;
	}

	public void setProcedureFee(Long procedureFee) {
		this.procedureFee = procedureFee;
	}

	public String getAttachData() {
		return attachData;
	}

	public void setAttachData(String attachData) {
		this.attachData = attachData;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
}
