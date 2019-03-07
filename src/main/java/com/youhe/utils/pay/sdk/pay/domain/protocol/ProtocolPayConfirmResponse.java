package com.youhe.utils.pay.sdk.pay.domain.protocol;

import com.youhe.utils.pay.sdk.domain.Response;

/**
 * 协议支付绑卡返回
 * @author yjw
 *
 */
public class ProtocolPayConfirmResponse extends Response{

    /**
     * 客户编码
     */
    private String customerCode;
	
    /**
     * 会员编号
     */
    private String memberId;
    
    private String outTradeNo;
	private String transactionNo;
	private Long amount;
	private Long procedureFee;
	private String payState;
	private String payTime;
	private String settCycle;
	private Long settCycleInterval;
    
    
	public String getCustomerCode() {
		return customerCode;
	}


	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}


	public String getMemberId() {
		return memberId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
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

	@Override
	public String toString() {
		return "ProtocolPayConfirmResponse [customerCode=" + customerCode + ", memberId=" + memberId + ", outTradeNo="
				+ outTradeNo + ", transactionNo=" + transactionNo + ", amount=" + amount + ", procedureFee="
				+ procedureFee + ", payState=" + payState + ", payTime=" + payTime + ", settCycle=" + settCycle
				+ ", settCycleInterval=" + settCycleInterval + "]";
	}
	
}
