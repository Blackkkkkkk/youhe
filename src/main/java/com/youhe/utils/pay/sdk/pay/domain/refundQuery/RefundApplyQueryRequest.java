package com.youhe.utils.pay.sdk.pay.domain.refundQuery;

import java.util.UUID;

public class RefundApplyQueryRequest {

    private String customerCode;//商户编号
    private String outRefundNo;//商户退款单号
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

    public RefundApplyQueryRequest() {    }

    public RefundApplyQueryRequest(String customerCode, String outRefundNo) {
        this.customerCode = customerCode;
        this.outRefundNo = outRefundNo;
    }

    public String getCustomerCode() {  return customerCode;   }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }
}