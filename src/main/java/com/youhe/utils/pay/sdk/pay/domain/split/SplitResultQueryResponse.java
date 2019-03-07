package com.youhe.utils.pay.sdk.pay.domain.split;

import com.alibaba.fastjson.JSONObject;
import com.youhe.utils.pay.sdk.domain.Response;

import java.util.List;
import java.util.UUID;

/**
 * 分账结果查询响应参数
 */
public class SplitResultQueryResponse extends Response {

	private String customerCode;
	private String outTradeNo;
	private String transactionNo;
	private Long amount;
	private Long realAmount;
	private String splitState;
	private String splitTime;
	private String settCycle;
	private Long settCycleInterval;
	private Long procedureFee;
	private String attachData;
	private List<JSONObject> splitResultInfoList;
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
	public Long getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(Long realAmount) {
		this.realAmount = realAmount;
	}
	public String getSplitState() {
		return splitState;
	}
	public void setSplitState(String splitState) {
		this.splitState = splitState;
	}
	public String getSplitTime() {
		return splitTime;
	}
	public void setSplitTime(String splitTime) {
		this.splitTime = splitTime;
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
	public List<JSONObject> getSplitResultInfoList() {
		return splitResultInfoList;
	}
	public void setSplitResultInfoList(List<JSONObject> splitResultInfoList) {
		this.splitResultInfoList = splitResultInfoList;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
}
