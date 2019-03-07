package com.youhe.utils.pay.sdk.inlet.domain;

import java.util.UUID;

import com.youhe.utils.pay.sdk.domain.Response;

/**
 * Created by adm on 2018/1/23.
 */
public class InletMerchantResponse extends Response {

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
