package com.youhe.utils.pay.sdk.pay.domain.withdraw;

import java.util.UUID;

import com.youhe.utils.pay.sdk.domain.Response;

public class Withdraw4SubMerchantResponse extends Response {

    /**
     * 支付结果
     */
    private String payResult;
    /**
     * 支付失败原因
     */
    private String payError;
    /**
     * 交易单号
     */
    private String transactionNo;
    /**
     * 交易金额
     */
    private Long amount;
    /**
     * 手续费
     */
    private Long procedureFee;
    /**
     * 实际到账金额
     */
    private Long actualFee;

	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
    

	public String getPayResult() {
		return payResult;
	}
	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}
	public String getPayError() {
		return payError;
	}
	public void setPayError(String payError) {
		this.payError = payError;
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
	public Long getActualFee() {
		return actualFee;
	}
	public void setActualFee(Long actualFee) {
		this.actualFee = actualFee;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
}
