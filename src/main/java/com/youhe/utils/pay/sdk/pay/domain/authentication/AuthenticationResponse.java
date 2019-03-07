package com.youhe.utils.pay.sdk.pay.domain.authentication;

import java.util.UUID;

import com.youhe.utils.pay.sdk.domain.Response;

public class AuthenticationResponse  extends Response{
    private String returnCode;
    private String outTradeNo;
    private String consistState;
    private String returnMsg;
    private String customerCode;
    private String transactionNo;
    private Long procedureFee;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getConsistState() {
		return consistState;
	}
	public void setConsistState(String consistState) {
		this.consistState = consistState;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public Long getProcedureFee() {
		return procedureFee;
	}
	public void setProcedureFee(Long procedureFee) {
		this.procedureFee = procedureFee;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	@Override
	public String toString() {
		return "AuthenticationResponse [returnCode=" + returnCode + ", outTradeNo=" + outTradeNo + ", consistState="
				+ consistState + ", returnMsg=" + returnMsg + ", customerCode=" + customerCode + ", transactionNo="
				+ transactionNo + ", procedureFee=" + procedureFee + ", nonceStr=" + nonceStr + "]";
	}

	
	
}
