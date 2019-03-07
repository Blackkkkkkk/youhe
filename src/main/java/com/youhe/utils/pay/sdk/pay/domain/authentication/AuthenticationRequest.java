package com.youhe.utils.pay.sdk.pay.domain.authentication;

import java.util.UUID;

import com.youhe.utils.pay.sdk.pay.domain.cashierPay.OrderInfo;


public class AuthenticationRequest {
    private String outTradeNo;
    private String customerCode;
    private String telephoneNo;
    private String certId;
    private String certName;
    private String cvn;
    private String cardNo;
    private String expiredDate;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
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
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	public String getCertId() {
		return certId;
	}
	public void setCertId(String certId) {
		this.certId = certId;
	}
	public String getCertName() {
		return certName;
	}
	public void setCertName(String certName) {
		this.certName = certName;
	}
	public String getCvn() {
		return cvn;
	}
	public void setCvn(String cvn) {
		this.cvn = cvn;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	
    public AuthenticationRequest(String outTradeNo, String customerCode, String telephoneNo, String certId,
            String certName,  String cvn, String cardNo,
            String expiredDate) {
    	this.outTradeNo = outTradeNo;
    	this.customerCode = customerCode;
    	this.telephoneNo = telephoneNo;
    	this.certId = certId;
    	this.certName = certName;
    	if (null != cvn && expiredDate != null){
        	this.cvn = cvn;
        	this.expiredDate = expiredDate;
    	}

    	this.cardNo = cardNo;

    }
	
	@Override
	public String toString() {
		return "AuthenticationRequest [outTradeNo=" + outTradeNo + ", customerCode=" + customerCode + ", telephoneNo="
				+ telephoneNo + ", certId=" + certId + ", certName=" + certName + ", cvn=" + cvn + ", cardNo=" + cardNo
				+ ", expiredDate=" + expiredDate + ", nonceStr=" + nonceStr + "]";
	}
	
	
}
