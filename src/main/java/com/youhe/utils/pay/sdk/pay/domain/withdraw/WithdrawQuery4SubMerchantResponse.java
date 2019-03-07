package com.youhe.utils.pay.sdk.pay.domain.withdraw;

import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.youhe.utils.pay.sdk.domain.Response;

public class WithdrawQuery4SubMerchantResponse extends Response {

	private String customerCode;

	private String outTradeNo;

	private String transactionNo;

	private Long amount;

	private String payState;
	private String settCycle;
	private Long settCycleInterval;
	private Long procedureFee;
	private Long payTime;

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

	public Long getPayTime() {
		return payTime;
	}

	public void setPayTime(Long payTime) {
		this.payTime = payTime;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
