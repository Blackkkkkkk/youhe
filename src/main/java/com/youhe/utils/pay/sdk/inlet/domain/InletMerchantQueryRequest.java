package com.youhe.utils.pay.sdk.inlet.domain;

import java.util.UUID;

public class InletMerchantQueryRequest {
	private String customerInfoId;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

	public String getCustomerInfoId() {
		return customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

}
