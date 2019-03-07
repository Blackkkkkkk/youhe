package com.youhe.utils.pay.sdk.pay.domain.refund;

import java.util.UUID;

public class RefundApplyRequest {

    private String transactionNo;//原交易单号
    private String outTradeNo;//原支付交易的商户订单号
    private String customerCode;//商户编号
    private long amount;//原订单支付金额
    private String notifyUrl;//退款结果通知
    private String sourceChannel;//渠道类型
    private String outRefundNo;//商户退款单号
    private long refundAmount;//申请退款金额
    private String remark;//退款申请说明
    private Long userId;//门户的用户ID，对API可空
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

    public RefundApplyRequest() {    }

    public RefundApplyRequest(String transactionNo, String outTradeNo, String customerCode, long amount,
                              String notifyUrl, String sourceChannel, String outRefundNo, long refundAmount,
                              String remark, Long userId) {
        this.transactionNo = transactionNo;
        this.outTradeNo = outTradeNo;
        this.customerCode = customerCode;
        this.amount = amount;
        this.notifyUrl = notifyUrl;
        this.sourceChannel = sourceChannel;
        this.outRefundNo = outRefundNo;
        this.refundAmount = refundAmount;
        this.remark = remark;
        this.userId = userId;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getSourceChannel() {
        return sourceChannel;
    }

    public void setSourceChannel(String sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public long getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(long refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }
}