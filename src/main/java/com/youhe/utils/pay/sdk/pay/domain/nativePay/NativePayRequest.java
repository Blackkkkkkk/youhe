package com.youhe.utils.pay.sdk.pay.domain.nativePay;

import com.youhe.utils.pay.sdk.pay.domain.cashierPay.OrderInfo;
import com.youhe.utils.pay.sdk.pay.domain.split.SplitInfo;

import java.util.List;
import java.util.UUID;

/**
 * 描述 ：主扫支付请求参数封装
 */
public class NativePayRequest  {

    private String outTradeNo;
    private String customerCode;
    private String terminalNo;
    private String clientIp;
    private OrderInfo orderInfo;
    private long payAmount;
    private String payCurrency;
    private String channelType;
    private String notifyUrl;
    private String redirectUrl;
    private String attachData;
    private String transactionStartTime;
    private String transactionEndTime;
    private String payMethod;//支付方式
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

    private Boolean needSplit;
    private List<SplitInfo> splitInfoList;
    private String splitNotifyUrl;
    private String splitAttachData;
	
	private String rechargeMemCustCode;
	
	private Boolean noCreditCards;
	private String subCustomerCode; //子商户号：银联主扫需要

    public NativePayRequest() {    }

    public NativePayRequest(String outTradeNo, String customerCode, String terminalNo, String clientIp,
                            OrderInfo orderInfo, long payAmount, String payCurrency, String channelType,
                            String notifyUrl, String redirectUrl, String attachData, String transactionStartTime,
                            String transactionEndTime, String payMethod, String subCustomerCode) {
        this.outTradeNo = outTradeNo;
        this.customerCode = customerCode;
        this.terminalNo = terminalNo;
        this.clientIp = clientIp;
        this.orderInfo = orderInfo;
        this.payAmount = payAmount;
        this.payCurrency = payCurrency;
        this.channelType = channelType;
        this.notifyUrl = notifyUrl;
        this.redirectUrl = redirectUrl;
        this.attachData = attachData;
        this.transactionStartTime = transactionStartTime;
        this.transactionEndTime = transactionEndTime;
        this.payMethod = payMethod;
        this.subCustomerCode = subCustomerCode;
    }

    public String getSubCustomerCode() {
		return subCustomerCode;
	}

	public void setSubCustomerCode(String subCustomerCode) {
		this.subCustomerCode = subCustomerCode;
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

    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(long payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayCurrency() {
        return payCurrency;
    }

    public void setPayCurrency(String payCurrency) {
        this.payCurrency = payCurrency;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getAttachData() {
        return attachData;
    }

    public void setAttachData(String attachData) {
        this.attachData = attachData;
    }

    public String getTransactionStartTime() {
        return transactionStartTime;
    }

    public void setTransactionStartTime(String transactionStartTime) {
        this.transactionStartTime = transactionStartTime;
    }

    public String getTransactionEndTime() {
        return transactionEndTime;
    }

    public void setTransactionEndTime(String transactionEndTime) {
        this.transactionEndTime = transactionEndTime;
    }

    public String getPayMethod() { return payMethod; }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public Boolean getNeedSplit() {
        return needSplit;
    }
    public void setNeedSplit(Boolean needSplit) {
        this.needSplit = needSplit;
    }
    public List<SplitInfo> getSplitInfoList() {
        return splitInfoList;
    }
    public void setSplitInfoList(List<SplitInfo> splitInfoList) {
        this.splitInfoList = splitInfoList;
    }
    public String getSplitNotifyUrl() {
        return splitNotifyUrl;
    }
    public void setSplitNotifyUrl(String splitNotifyUrl) {
        this.splitNotifyUrl = splitNotifyUrl;
    }
    public String getSplitAttachData() {
        return splitAttachData;
    }
    public void setSplitAttachData(String splitAttachData) {
        this.splitAttachData = splitAttachData;
    }

	public String getRechargeMemCustCode() {
		return rechargeMemCustCode;
	}

	public void setRechargeMemCustCode(String rechargeMemCustCode) {
		this.rechargeMemCustCode = rechargeMemCustCode;
	}

	public Boolean getNoCreditCards() {
		return noCreditCards;
	}

	public void setNoCreditCards(Boolean noCreditCards) {
		this.noCreditCards = noCreditCards;
	}

}
