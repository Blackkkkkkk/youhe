package com.youhe.utils.pay.sdk.domain;

import java.util.UUID;

public class NotifyContent {

	private String customerCode;
	private String outTradeNo;
	private String transactionNo;
	private String amount;
	private String payState;
	private String payTime;
	private String settCycle;
	private String settCycleInterval;
	private String procedureFee;
	private String attachData;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

	public NotifyContent() {
	}

	public NotifyContent(String customerCode, String outTradeNo, String transactionNo,
						 String amount, String payState, String payTime, String settCycle,
						 String settCycleInterval, String procedureFee, String attachData, String nonceStr) {
		this.customerCode = customerCode;
		this.outTradeNo = outTradeNo;
		this.transactionNo = transactionNo;
		this.amount = amount;
		this.payState = payState;
		this.payTime = payTime;
		this.settCycle = settCycle;
		this.settCycleInterval = settCycleInterval;
		this.procedureFee = procedureFee;
		this.attachData = attachData;
		this.nonceStr = nonceStr;
	}

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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
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

	public String getSettCycleInterval() {
		return settCycleInterval;
	}

	public void setSettCycleInterval(String settCycleInterval) {
		this.settCycleInterval = settCycleInterval;
	}

	public String getProcedureFee() {
		return procedureFee;
	}

	public void setProcedureFee(String procedureFee) {
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
