package com.youhe.utils.pay.sdk.pay.domain.wechatJSAPI;

import java.util.List;
import java.util.UUID;

import com.youhe.utils.pay.sdk.pay.domain.split.SplitInfo;

/**
 * @Author : Author
 * @Date : 2018/1/29 11:27
 * @Description :
 */
public class WechatJSAPIRequest {

	private String outTradeNo;
	private String customerCode;
	private String terminalNo;
	private String clientIp;
	private com.youhe.utils.pay.sdk.pay.domain.wechatJSAPI.WxJsOrderInfo orderInfo;
	private long payAmount;
	private String payCurrency;
	private String channelType;
	private String notifyUrl;
	private String attachData;
	private String transactionStartTime;
	private String transactionEndTime;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
	private String appId;
	private String openId;

	private Boolean needSplit;
	private List<SplitInfo> splitInfoList;
	private String splitNotifyUrl;
	private String splitAttachData;

	private String rechargeMemCustCode;

	private Boolean noCreditCards;
	public WechatJSAPIRequest() {
	}

	public WechatJSAPIRequest(String outTradeNo, String customerCode, String terminalNo, String clientIp,
							  com.youhe.utils.pay.sdk.pay.domain.wechatJSAPI.WxJsOrderInfo orderInfo, long payAmount, String payCurrency, String channelType, String notifyUrl,
							  String attachData, String transactionStartTime, String transactionEndTime) {
		this.outTradeNo = outTradeNo;
		this.customerCode = customerCode;
		this.terminalNo = terminalNo;
		this.clientIp = clientIp;
		this.orderInfo = orderInfo;
		this.payAmount = payAmount;
		this.payCurrency = payCurrency;
		this.channelType = channelType;
		this.notifyUrl = notifyUrl;
		this.attachData = attachData;
		this.transactionStartTime = transactionStartTime;
		this.transactionEndTime = transactionEndTime;
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

	public com.youhe.utils.pay.sdk.pay.domain.wechatJSAPI.WxJsOrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(com.youhe.utils.pay.sdk.pay.domain.wechatJSAPI.WxJsOrderInfo orderInfo) {
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

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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
