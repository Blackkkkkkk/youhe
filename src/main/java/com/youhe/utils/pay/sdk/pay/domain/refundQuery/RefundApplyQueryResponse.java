package com.youhe.utils.pay.sdk.pay.domain.refundQuery;

import java.util.UUID;

import com.youhe.utils.pay.sdk.domain.Response;

/**
 * 退款查询返回对象
 *
 */
public class RefundApplyQueryResponse extends Response {

	private String customerCode; //商户在EFPS的编号
	private String outRefundNo; //商户退款单号
	private String transactionNo; //EFPS订单号
	private String refundAmount; //申请退款金额
	private String auditState; //退款审核状态
	private String auditMsg; //退款审核意见
	private String refundState; //退款执行结果
	private String refundTime; //退款成功时间
	private String procedureFee; //退款手续费
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

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

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getAuditMsg() {
		return auditMsg;
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	public String getProcedureFee() {
		return procedureFee;
	}

	public void setProcedureFee(String procedureFee) {
		this.procedureFee = procedureFee;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
}
