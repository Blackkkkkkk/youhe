package com.youhe.utils.pay.sdk.pay.domain.microPay;

import java.util.List;
import java.util.UUID;

import com.youhe.utils.pay.sdk.pay.domain.cashierPay.OrderInfo;
import com.youhe.utils.pay.sdk.pay.domain.split.SplitInfo;

/**
 * 被扫请求参数
 */
public class MicroPayRequest {
	private String outTradeNo;
	private String customerCode;
	private String terminalNo;
	private String clientIp;
	private OrderInfo orderInfo;
	private Long payAmount;
	private String payCurrency;
	private String payMethod;
	private String channelType;
	private String attachData;
	private String transactionStartTime;
	private String transactionEndTime;
	private String authCode;
	private String scene;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

	private Boolean needSplit;
    private List<SplitInfo> splitInfoList;
    private String splitNotifyUrl;
    private String splitAttachData;
	
	private String rechargeMemCustCode;
	
	private Boolean noCreditCards;
	private String subCustomerCode;
	public MicroPayRequest() {
	}

	public MicroPayRequest(String outTradeNo, String customerCode, String terminalNo, String clientIp,
						  OrderInfo orderInfo, Long payAmount, String payCurrency,
						  String payMethod, String channelType, String attachData,
						  String transactionStartTime, String transactionEndTime, String authCode,
						  String scene, String subCustomerCode) {
		this.outTradeNo = outTradeNo;
		this.customerCode = customerCode;
		this.terminalNo = terminalNo;
		this.clientIp = clientIp;
		this.orderInfo = orderInfo;
		this.payAmount = payAmount;
		this.payCurrency = payCurrency;
		this.payMethod = payMethod;
		this.channelType = channelType;
		this.attachData = attachData;
		this.transactionStartTime = transactionStartTime;
		this.transactionEndTime = transactionEndTime;
		this.authCode = authCode;
		this.scene = scene;
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
	public Long getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}
	public String getPayCurrency() {
		return payCurrency;
	}
	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
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
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getScene() {
		return scene;
	}
	public void setScene(String scene) {
		this.scene = scene;
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
