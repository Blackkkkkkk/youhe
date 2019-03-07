package com.youhe.utils.pay.sdk.pay.domain.splitRefund;

import java.util.UUID;

import com.youhe.utils.pay.sdk.domain.Response;

/**
 * 退款响应
 * @author jidonglin
 *
 */
public class RefundResponse extends Response {

	private String outRefundNo;
	private String outTradeNo;
	/**
	 * EFPS订单号
	 */
	private String transactionNo;
	/**
	 * 申请退款金额
	 */
	private Long refundAmount;
	/**
	 * 原订单支付金额
	 */
	private Long origAmount;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

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
	public Long getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}
	public Long getOrigAmount() {
		return origAmount;
	}
	public void setOrigAmount(Long origAmount) {
		this.origAmount = origAmount;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	
}
