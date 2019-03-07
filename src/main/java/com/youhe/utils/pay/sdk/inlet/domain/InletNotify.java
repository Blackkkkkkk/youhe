package com.youhe.utils.pay.sdk.inlet.domain;

import java.util.UUID;

import com.alibaba.fastjson.JSON;

/**
 * 子商户进件 异步通知
 */
public class InletNotify {
	private String customerInfoId;
	
	private String name;
	
	private String shortName;
	
	private Long auditResult;
	
	private String auditMsg;
	
	private String auditDateTime;
	
	private String customerCode;

	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

	public String getCustomerInfoId() {
		return customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Long getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(Long auditResult) {
		this.auditResult = auditResult;
	}

	public String getAuditMsg() {
		return auditMsg;
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}

	public String getAuditDateTime() {
		return auditDateTime;
	}

	public void setAuditDateTime(String auditDateTime) {
		this.auditDateTime = auditDateTime;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return JSON.toJSONString(this);
	}
	
}
