package com.youhe.utils.pay.sdk.pay.domain.microPay;

import java.util.UUID;

import com.youhe.utils.pay.sdk.domain.Response;

/**
 * 被扫响应参数
 */
public class MicroPayResponse extends Response {
	private String outTradeNo;
	private String transactionNo;
	private Long amount;
	private Long procedureFee;
	private String payState;
	private String payTime;
	private String settCycle;
	private Long settCycleInterval;
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
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getProcedureFee() {
		return procedureFee;
	}
	public void setProcedureFee(Long procedureFee) {
		this.procedureFee = procedureFee;
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
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
}
