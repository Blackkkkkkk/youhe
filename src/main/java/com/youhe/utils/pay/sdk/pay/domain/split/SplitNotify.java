package com.youhe.utils.pay.sdk.pay.domain.split;

import java.util.List;
import java.util.UUID;

/**
 * @Author : Author
 * @Date : 2018/1/29 11:27
 * @Description :
 */
public class SplitNotify {

    private String customerCode ;//商户在EFPS的编号
    private String outTradeNo ;//商户订单号
    private String transactionNo ;//EFPS订单号
    private Long amount ;//分账金额
    private Long realAmount ;//实际分账金额
    private String splitState ;//分账结果
    private String splitTime ;//分账完成时间
    private String settCycle ;//该支付交易所属的清算周期
    private Long settCycleInterval ;//清算周期长度
    private Long procedureFee ;//手续费
    private String attachData ;//附加数据
    private List<com.youhe.utils.pay.sdk.pay.domain.split.SplitResultInfo> splitResultInfoList ;//该笔分账对应的具体分账信息
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

    public String getCustomerCode() {  return customerCode; }

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

    public List<com.youhe.utils.pay.sdk.pay.domain.split.SplitResultInfo> getSplitResultInfoList() {
        return splitResultInfoList;
    }

    public void setSplitResultInfoList(List<com.youhe.utils.pay.sdk.pay.domain.split.SplitResultInfo> splitResultInfoList) {
        this.splitResultInfoList = splitResultInfoList;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }
}
