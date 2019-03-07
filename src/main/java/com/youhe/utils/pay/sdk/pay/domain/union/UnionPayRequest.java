package com.youhe.utils.pay.sdk.pay.domain.union;

import com.alibaba.fastjson.JSONObject;

import com.youhe.utils.pay.sdk.pay.domain.cashierPay.CashierRequest;

public class UnionPayRequest extends CashierRequest{
	/**
	 * 前台通知地址
	 */
	private String frontUrl;
	/**
	 * 交易失败前台跳转地址
	 */
	private String frontFailUrl;
	/**
	 * 发卡机构代码
	 */
	private String issInsCode;
	/**
	 * 支持的卡类型（debit：借记卡，credit：贷记卡）
	 */
	private String bankCardType;
	
	/**
	 * 取值：
	 * 网银支付：personal_bank
	 * 银联在线：union_online
	 * 不传默认为网银支付
	 */
	private String tradeType;
	
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getFrontUrl() {
		return frontUrl;
	}
	public void setFrontUrl(String frontUrl) {
		this.frontUrl = frontUrl;
	}
	public String getFrontFailUrl() {
		return frontFailUrl;
	}
	public void setFrontFailUrl(String frontFailUrl) {
		this.frontFailUrl = frontFailUrl;
	}
	public String getIssInsCode() {
		return issInsCode;
	}
	public void setIssInsCode(String issInsCode) {
		this.issInsCode = issInsCode;
	}
	public String getBankCardType() {
		return bankCardType;
	}
	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return JSONObject.toJSONString(UnionPayRequest.class);
	}
}
