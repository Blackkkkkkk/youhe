package com.youhe.utils.pay.sdk.pay.domain.splitRefund;

import java.util.List;
import java.util.UUID;

import com.youhe.utils.pay.sdk.pay.domain.split.SplitInfo;

/**
 * 退款请求
 *
 */
public class RefundRequest {
	private String customerCode;
	/**
	 * 商户退款单号
	 */
	private String outRefundNo;
	/**
	 * 原支付交易的商户订单号
	 */
	private String outTradeNo;
	private String transactionNo;
	private Long refundAmount;
	private Long amount;
	private String remark;
	private String notifyUrl;
	private List<SplitInfo> splitInfoList;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

	public RefundRequest(String customerCode, String outRefundNo, String outTradeNo, String transactionNo,
			Long refundAmount, Long amount, String remark, String notifyUrl, List<SplitInfo> splitInfoList) {
		this.customerCode = customerCode;
		this.outRefundNo = outRefundNo;
		this.outTradeNo = outTradeNo;
		this.transactionNo = transactionNo;
		this.refundAmount = refundAmount;
		this.amount = amount;
		this.remark = remark;
		this.notifyUrl = notifyUrl;
		this.splitInfoList = splitInfoList;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public List<SplitInfo> getSplitInfoList() {
		return splitInfoList;
	}

	public void setSplitInfoList(List<SplitInfo> splitInfoList) {
		this.splitInfoList = splitInfoList;
	}
}
