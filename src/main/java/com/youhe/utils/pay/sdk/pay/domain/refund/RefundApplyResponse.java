package com.youhe.utils.pay.sdk.pay.domain.refund;

import java.util.UUID;

import com.youhe.utils.pay.sdk.domain.Response;

/**
 * 退款申请值对象
 *
 */
public class RefundApplyResponse extends Response {
 
	private String refundApplyNo;
	private String outRefundNo;
	private String outTradeNo;
	private String transactionNo;
	private String refundAmount;
	private String origAmount;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

	public String getRefundApplyNo() {
		return refundApplyNo;
	}

	public void setRefundApplyNo(String refundApplyNo) {
		this.refundApplyNo = refundApplyNo;
	}

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
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

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getOrigAmount() {
		return origAmount;
	}

	public void setOrigAmount(String origAmount) {
		this.origAmount = origAmount;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
}
