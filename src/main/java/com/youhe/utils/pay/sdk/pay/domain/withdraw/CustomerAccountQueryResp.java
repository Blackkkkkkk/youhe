package com.youhe.utils.pay.sdk.pay.domain.withdraw;

import com.alibaba.fastjson.JSON;

import com.youhe.utils.pay.sdk.domain.Response;

public class CustomerAccountQueryResp extends Response {
	String customerCode;
	Long availableBalance;
	Long floatBalance;
	String nonceStr;
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public Long getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(Long availableBalance) {
		this.availableBalance = availableBalance;
	}
	public Long getFloatBalance() {
		return floatBalance;
	}
	public void setFloatBalance(Long floatBalance) {
		this.floatBalance = floatBalance;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	

}
