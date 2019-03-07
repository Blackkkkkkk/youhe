package com.youhe.utils.pay.sdk.pay.domain.withdraw;

import com.alibaba.fastjson.JSON;

import com.youhe.utils.pay.sdk.domain.Response;

public class WithdrawToCardQueryResponse extends Response {
	
    private String customerCode;
    
    private String outTradeNo;
    
    private String transactionNo;
    
    private Long amount;
    
    private Long procedureFee;
    
    private String payState;

    private String payTime;
    
    private String nonceStr;

	public Long getProcedureFee() {
		return procedureFee;
	}



	public void setProcedureFee(Long procedureFee) {
		this.procedureFee = procedureFee;
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


	public String getNonceStr() {
		return nonceStr;
	}



	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}



	public String getPayTime() {
		return payTime;
	}



	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}



	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
    
    
	
}
