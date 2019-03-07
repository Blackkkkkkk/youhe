package com.youhe.utils.pay.sdk.pay.domain.wechatJSAPI;

import java.util.UUID;

/**
 * @Author : Author
 * @Date : 2018/1/29 11:27
 * @Description :
 */
public class WxJsOrderNotify {

    private String customerCode ;//商户在EFPS的编号
    private String outTradeNo ;//商户订单号
    private String transactionNo ;//EFPS订单号
    private Long amount ;//支付金额
    private String payState ;//支付结果
    private String payTime ;//支付完成时间
    private String settCycle ;//该支付交易所属的清算周期
    private Long settCycleInterval ;//清算周期长度
    private Long procedureFee ;//手续费
    private String attachData ;//附加数据
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

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }
}
